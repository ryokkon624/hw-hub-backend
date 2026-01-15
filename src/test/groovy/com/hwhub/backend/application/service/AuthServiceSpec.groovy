package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.presentation.rest.auth.dto.LoginRequest
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedException
import com.hwhub.backend.security.JwtProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthServiceSpec extends Specification{

    UserRepository userRepository = Mock()
    PasswordEncoder passwordEncoder = Mock()
    JwtProvider jwtProvider = Mock()
    UserIconService userIconService = Mock()

    AuthService service = new AuthService(
            userRepository,
            passwordEncoder,
            jwtProvider,
            userIconService
    )

    def "loginは正しい認証情報かつActiveなユーザのときJWTとユーザ情報を返す"() {
        given: "ログインリクエストとユーザ"
        def request = new LoginRequest()
        request.setEmail("test@example.com")
        request.setPassword("raw-password")

        def user = UserModel.reconstruct(
                10L,
                "test@example.com",
                "hashed-password",
                "テストユーザ",
                "ja",
                "icon/key/001",
                true // Active
        )

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

    def "loginはInactiveなユーザのときBadCredentialsException"() {
        given:
        def request = new LoginRequest()
        request.setEmail("inactive@example.com")
        request.setPassword("any-password")

        def user = UserModel.reconstruct(
                11L,
                "inactive@example.com",
                "hashed-password",
                "退会済みユーザ",
                "ja",
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
                "テストユーザ",
                "ja",
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
                "新規ユーザ",
                "ja",
                "icon/key/999",
                true
        )

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
                20L, "dup@example.com", "hash", "Exist", "en", null, true
        )

        when:
        service.register(model)

        then:
        1 * userRepository.findByEmail("dup@example.com") >> Optional.of(existingUser)
        thrown(EmailAlreadyUsedException)
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
    }
}
