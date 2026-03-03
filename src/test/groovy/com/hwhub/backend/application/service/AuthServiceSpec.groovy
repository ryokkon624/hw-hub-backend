package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.enums.AuthProvider
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.presentation.rest.auth.dto.LoginRequest
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedException
import com.hwhub.backend.security.JwtProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import com.hwhub.backend.config.EmailVerificationProperties
import com.hwhub.backend.domain.notification.VerificationMailSender
import com.hwhub.backend.domain.repository.UserEmailVerificationRepository
import spock.lang.Specification

class AuthServiceSpec extends Specification{

    UserRepository userRepository = Mock()
    PasswordEncoder passwordEncoder = Mock()
    JwtProvider jwtProvider = Mock()
    UserIconService userIconService = Mock()

    EmailVerificationProperties emailVerificationProperties = new EmailVerificationProperties(false, false, 60, 60, 5, "http://localhost", "/verify")
    
    UserEmailVerificationRepository userEmailVerificationRepository = Mock()
    VerificationMailSender verificationMailSender = Mock()

    AuthService service

    def setup() {
        service = new AuthService(
                userRepository,
                passwordEncoder,
                jwtProvider,
                userIconService,
                emailVerificationProperties,
                userEmailVerificationRepository,
                verificationMailSender
        )
    }

    def "loginは正しい認証情報かつActiveなユーザのときJWTとユーザ情報を返す"() {
        given: "ログインリクエストとユーザ"
        def request = new LoginRequest()
        request.setEmail("test@example.com")
        request.setPassword("raw-password")

        def user = UserModel.reconstruct(
                10L,
                "test@example.com",
                "hashed-password",
                null,
                AuthProvider.LOCAL.code,
                null,
                "テストユーザ",
                "ja",
                true,
                "icon/key/001",
                null, 
                true // Active
        )

        // Default is enabled=false


        when:
        def result = service.login(request)

        then: "依存コンポーネントの呼び出しと戻り値を同時に定義・検証する"
        1 * userRepository.findByEmail("test@example.com") >> Optional.of(user)
        1 * passwordEncoder.matches("raw-password", "hashed-password") >> true
        1 * userIconService.getIconUrl("icon/key/001") >> "https://cdn/icon.png"
        1 * jwtProvider.generateToken(10L, "テストユーザ") >> "jwt-token"

        and: "結果オブジェクトも検証する"
        result.token() == "jwt-token"
        result.user().userId == 10L
        result.user().iconUrl == "https://cdn/icon.png"
    }



    // Fix: Using correct exception class
    def "loginは認証有効かつ未認証の場合EmailNotVerifiedExceptionを投げる"() {
        given:
        def request = new LoginRequest()
        request.setEmail("unverified@example.com")
        request.setPassword("password")

        def user = Mock(UserModel)
        user.getPasswordHash() >> "hash"
        user.isActive() >> true
        user.getEmailVerifiedAt() >> null // Not verified

        // Re-init with enabled=true
        def props = new EmailVerificationProperties(true, false, 60, 60, 5, "http://localhost", "/verify")
        service = new AuthService(userRepository, passwordEncoder, jwtProvider, userIconService, props, userEmailVerificationRepository, verificationMailSender)

        when:
        service.login(request)

        then:
        1 * userRepository.findByEmail("unverified@example.com") >> Optional.of(user)
        1 * passwordEncoder.matches("password", "hash") >> true
        thrown(com.hwhub.backend.presentation.rest.common.EmailNotVerifiedException)
    }

    def "loginはInactiveなユーザのときBadCredentialsException"() {
        given:
        def request = new LoginRequest()
        request.setEmail("inactive@example.com")
        request.setPassword("any-password")

        def user = UserModel.reconstruct(
                11L,
                "inactive@example.com",
                "hashed-password",
                null,
                AuthProvider.LOCAL.code,
                null,
                "退会済みユーザ",
                "ja",
                true,
                null,
                null,
                false // Inactive
        )

        when:
        service.login(request)

        then:
        1 * userRepository.findByEmail("inactive@example.com") >> Optional.of(user)
        // パスワードチェックを通過させる (true)
        1 * passwordEncoder.matches("any-password", "hashed-password") >> true
        
        // 活性チェックで例外が出る
        def ex = thrown(BadCredentialsException)
        ex.message == "Account is deactivated"
    }

    def "loginはGoogle連携ユーザの場合PasswordLoginNotAllowedExceptionを投げる"() {
        given:
        def request = new LoginRequest()
        request.setEmail("google@example.com")
        request.setPassword("any")
        
        def user = Mock(UserModel) {
            getPasswordHash() >> "hash"
            isActive() >> true
            getAuthProvider() >> "GOOGLE"
        }

        when:
        service.login(request)

        then:
        1 * userRepository.findByEmail("google@example.com") >> Optional.of(user)
        1 * passwordEncoder.matches("any", "hash") >> true
        thrown(com.hwhub.backend.presentation.rest.common.PasswordLoginNotAllowedException)
    }

    def "loginはemailが存在しないときIllegalArgumentException"() {
        given:
        def request = new LoginRequest()
        request.setEmail("notfound@example.com")
        request.setPassword("password")

        when:
        service.login(request)

        then:
        1 * userRepository.findByEmail("notfound@example.com") >> Optional.empty()
        thrown(IllegalArgumentException)
    }

    def "loginはパスワードが不一致のときBadCredentialsException"() {
        given:
        def request = new LoginRequest()
        request.setEmail("test@example.com")
        request.setPassword("wrong-password")

        def user = UserModel.reconstruct(
                10L,
                "test@example.com",
                "hashed-password",
                null,
                AuthProvider.LOCAL.code,
                null,
                "テストユーザ",
                "ja",
                true,
                null,
                null,
                true
        )

        when:
        service.login(request)

        then:
        1 * userRepository.findByEmail("test@example.com") >> Optional.of(user)
        1 * passwordEncoder.matches("wrong-password", "hashed-password") >> false
        thrown(BadCredentialsException)
    }

    def "registerはemail未使用のとき新規登録してJWTを返す"() {
        given:
        def model = UserModel.create(
                "new@example.com",
                "raw-password",
                "新規ユーザ",
                "ja"
        )

        def inserted = UserModel.reconstruct(
                99L,
                "new@example.com",
                "hashed-password",
                null,
                AuthProvider.LOCAL.code,
                null,
                "新規ユーザ",
                "ja",
                true,
                 "icon/key/999",
                 null,
                 true
         )
        // Default is enabled=false


        when:
        def result = service.register(model)

        then:
        1 * userRepository.findByEmail("new@example.com") >> Optional.empty()
        1 * passwordEncoder.encode("raw-password") >> "hashed-password"
        1 * userRepository.insert(_, 1L, ProgramType.ONL_AUTH.getCode()) >> inserted
        1 * userIconService.getIconUrl("icon/key/999") >> "https://cdn/new.png"
        1 * jwtProvider.generateToken(99L, "新規ユーザ") >> "new-token"

        and:
        result.token() == "new-token"
        result.user().userId == 99L
    }

    def "registerは既存ActiveユーザがいるときEmailAlreadyUsedException"() {
        given:
        def model = UserModel.create(
                "dup@example.com",
                "password",
                "重複ユーザ",
                "ja"
        )
        def existingUser = UserModel.reconstruct(
                20L, "dup@example.com", "hash", null, AuthProvider.LOCAL.code, null, "Exist", "en", true, null, null, true
        )

        when:
        service.register(model)

        then:
        1 * userRepository.findByEmail("dup@example.com") >> Optional.of(existingUser)
        // Default is enabled=false
        thrown(EmailAlreadyUsedException)
    }

    def "registerはメール認証有効時にVerificationRequiredを返す"() {
        given:
        def model = UserModel.create("verify@example.com", "pw", "VerifyMe", "ja")
        def inserted = UserModel.reconstruct(100L, "verify@example.com", "hash", null, AuthProvider.LOCAL.code, null, "VerifyMe", "ja", true, null, null, true)

        // Re-init with enabled=true, sendMail=true
        def props = new EmailVerificationProperties(true, true, 30, 60, 5, "http://front", "/verify")
        service = new AuthService(userRepository, passwordEncoder, jwtProvider, userIconService, props, userEmailVerificationRepository, verificationMailSender)

        when:
        def result = service.register(model)

        then:
        1 * userRepository.findByEmail("verify@example.com") >> Optional.empty()
        1 * userRepository.insert(_, _, _) >> inserted
        
        // Mock resend policy checks
        1 * userEmailVerificationRepository.findLatestRequestedAt(_) >> Optional.empty()
        1 * userEmailVerificationRepository.countRequestedSince(_, _) >> 0
        
        1 * userEmailVerificationRepository.insert(_, _, _)
        1 * verificationMailSender.sendVerificationMail("verify@example.com", "VerifyMe", { it.contains("token=") }, "ja")

        and:
        result.emailVerificationRequired()
        result.token() == null
        result.verificationExpiresAt() != null
    }

    def "registerは既存Inactiveユーザがいるとき再活性化してJWTを返す"() {
        given:
        def model = UserModel.create(
                "inactive@example.com",
                "new-raw-pass",
                "復帰ユーザ",
                "en"
        )
        // 既存の退会済みユーザ
        def existingUser = Mock(UserModel) {
            isActive() >> false
            getUserId() >> 50L
            getProfileImageKey() >> "old/icon"
            getDisplayName() >> "復帰ユーザ"
        }

        when:
        def result = service.register(model)

        then:
        1 * userRepository.findByEmail("inactive@example.com") >> Optional.of(existingUser)
        
        // 再活性化処理
        1 * passwordEncoder.encode("new-raw-pass") >> "new-hashed-pass"
        1 * existingUser.setPasswordHash("new-hashed-pass")
        1 * existingUser.changeProfile("復帰ユーザ", "en")
        1 * existingUser.activate()
        
        1 * userRepository.updateForReactivation(existingUser, 50L, ProgramType.ONL_AUTH.getCode())
        1 * userIconService.getIconUrl("old/icon") >> "https://cdn/restored.png"
        1 * jwtProvider.generateToken(50L, "復帰ユーザ") >> "restored-token"

        and:
        result.token() == "restored-token"
        
        // This test case assumes enabled=false implicitly


        and:
        result.token() == "restored-token"
    }

    // --- New Verification Tests ---

    def "verifyEmailはトークンが有効な場合ユーザを認証済みにする"() {
        given:
        def token = "valid-token"
        // Mock a user verification model found in DB
        def uvModel = Mock(com.hwhub.backend.domain.model.UserEmailVerificationModel)
        uvModel.getUserEmailVerificationId() >> 123L
        uvModel.getUserId() >> 10L

        when:
        service.verifyEmail(token)

        then:
        1 * userEmailVerificationRepository.findUsableByTokenHash(_, _) >> Optional.of(uvModel)
        1 * userEmailVerificationRepository.markUsed(123L, _, _, _)
        1 * userRepository.markEmailVerified(10L, _, _, _)
    }

    def "verifyEmailはトークンが無効な場合例外を投げる"() {
        when:
        service.verifyEmail("invalid")

        then:
        1 * userEmailVerificationRepository.findUsableByTokenHash(_, _) >> Optional.empty()
        thrown(com.hwhub.backend.presentation.rest.common.EmailVerificationTokenInvalidException)
    }

    def "resendVerificationはユーザが存在し未認証の場合メールを送信する"() {
        given:
        def email = "resend@example.com"
        def user = Mock(UserModel)
        user.getEmailVerifiedAt() >> null // Not verified
        user.getUserId() >> 5L
        user.getEmail() >> email
        user.getDisplayName() >> "Resender"
        user.getLocale() >> "ja"

        // Re-init with custom props
        def props = new EmailVerificationProperties(true, true, 30, 60, 5, "http://front", "/verify")
        service = new AuthService(userRepository, passwordEncoder, jwtProvider, userIconService, props, userEmailVerificationRepository, verificationMailSender)

        when:
        service.resendVerification(email)

        then:
        1 * userRepository.findByEmail(email) >> Optional.of(user)
        1 * userEmailVerificationRepository.findLatestRequestedAt(5L) >> Optional.empty()
        1 * userEmailVerificationRepository.countRequestedSince(5L, _) >> 0

        1 * userEmailVerificationRepository.insert(_, _, _)
        1 * verificationMailSender.sendVerificationMail(email, "Resender", _, "ja")
    }

    def "resendVerificationはリクエスト頻度が高すぎる場合例外を投げる"() {
        given:
        def email = "fast@example.com"
        def user = Mock(UserModel)
        user.getEmailVerifiedAt() >> null
        user.getUserId() >> 6L

        def props = new EmailVerificationProperties(true, true, 30, 60, 5, "http://front", "/verify")
        service = new AuthService(userRepository, passwordEncoder, jwtProvider, userIconService, props, userEmailVerificationRepository, verificationMailSender)

        when:
        service.resendVerification(email)

        then:
        1 * userRepository.findByEmail(email) >> Optional.of(user)
        1 * userEmailVerificationRepository.findLatestRequestedAt(6L) >> Optional.of(java.time.LocalDateTime.now()) // Just now
        
        thrown(com.hwhub.backend.presentation.rest.common.EmailVerificationCooldownException)
    }
}
