package com.hwhub.backend.presentation.rest.auth

import com.hwhub.backend.application.service.AuthService
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.enums.AuthProvider
import com.hwhub.backend.presentation.rest.auth.dto.LoginRequest
import com.hwhub.backend.presentation.rest.auth.dto.LoginResponse
import com.hwhub.backend.presentation.rest.auth.dto.LoginUserDto
import com.hwhub.backend.presentation.rest.auth.dto.RegisterRequest
import com.hwhub.backend.presentation.rest.auth.dto.RegisterResponse
import com.hwhub.backend.presentation.rest.auth.dto.VerifyEmailRequest
import com.hwhub.backend.presentation.rest.auth.dto.ResendVerificationRequest
import org.springframework.http.HttpStatus
import spock.lang.Specification

class AuthControllerSpec extends Specification {

    AuthService authService = Mock()
    AuthController controller = new AuthController(authService)

    def "login は AuthService.login を呼び出し、200 OK と LoginResponse を返す"() {
        given:
        def request = new LoginRequest()
        request.setEmail("user@example.com")
        request.setPassword("secret")

        // AuthService が返す UserModel（ログイン済みユーザ想定）
        def user = UserModel.reconstruct(
                1L,
                "user@example.com",
                "hashed",
                null,
                AuthProvider.LOCAL.code,
                null,
                "Taro",
                "ja",
                true,
                "profile-key",
                null, // emailVerifiedAt
                true,
                null,
                null
        )

        when:
        def response = controller.login(request)

        then:
        1 * authService.login(request) >> new AuthService.LoginInfo("jwt-token-123", user)

        and: "HTTP ステータスは 200"
        response.statusCode == HttpStatus.OK

        and: "ボディの token と user 情報が設定されている"
        LoginResponse body = response.body
        body.accessToken == "jwt-token-123"

        LoginUserDto dto = body.user
        dto.getUserId() == 1L
        dto.getEmail() == "user@example.com"
        dto.getDisplayName() == "Taro"
        dto.getLocale() == "ja"
    }

    def "register は RegisterRequest から UserModel を組み立てて AuthService.register を呼ぶ"() {
        given:
        def request = new RegisterRequest(
                "new@example.com",
                "plain-pass",
                "Hanako",
                "ja",
                "token-xxxxxxx"
        )

        // register 後に返ってくる（永続化された）UserModel の想定
        def inserted = UserModel.reconstruct(
                10L,
                "new@example.com",
                "hashed-pass",
                null,
                AuthProvider.LOCAL.code,
                null,
                "Hanako",
                "ja",
                true,
                null,
                null, // verification
                true,
                null,
                null
        )

        when:
        def response = controller.register(request)

        then:
        1 * authService.register(_ as UserModel) >> { UserModel arg ->
            // Controller が組み立てた UserModel が正しいかチェック
            assert arg.userId == null
            assert arg.email == "new@example.com"
            assert arg.password == "plain-pass"
            assert arg.displayName == "Hanako"
            assert arg.locale == "ja"
            // 戻り値として RegisterInfo を返す (verificationRequired=false)
            new AuthService.RegisterInfo(false, "reg-token-456", inserted, null)
        }

        and:
        response.statusCode == HttpStatus.OK

        RegisterResponse body = response.body
        body.accessToken == "reg-token-456"

        LoginUserDto dto = body.user
        dto.getUserId() == 10L
        dto.getEmail() == "new@example.com"
        dto.getDisplayName() == "Hanako"
        dto.getLocale() == "ja"
    }

    def "registerは認証が必要な場合、認証情報を返す"() {
        given:
        def request = new RegisterRequest("verify@example.com", "pw", "Verify", "ja", null)
        def user = UserModel.reconstruct(100L, "verify@example.com", "hash", null, AuthProvider.LOCAL.code, null, "Verify", "ja", true, null, null, true, null, null)

        when:
        def response = controller.register(request)

        then:
        1 * authService.register(_) >> new AuthService.RegisterInfo(true, null, user, java.time.LocalDateTime.now().plusDays(1))

        and:
        response.statusCode == HttpStatus.OK
        response.body.emailVerificationRequired == true
        response.body.accessToken == null
        response.body.verificationExpiresAt != null
    }

    def "verifyEmailはサービスを呼び出し、204 No Contentを返す"() {
        given:
        def req = new VerifyEmailRequest("token-123")

        when:
        def response = controller.verifyEmail(req)

        then:
        1 * authService.verifyEmail("token-123")
        response.statusCode == HttpStatus.NO_CONTENT
    }

    def "resendVerificationはサービスを呼び出し、204 No Contentを返す"() {
        given:
        def req = new ResendVerificationRequest("resend@example.com")

        when:
        def response = controller.resendVerification(req)

        then:
        1 * authService.resendVerification("resend@example.com")
        response.statusCode == HttpStatus.NO_CONTENT
    }
}
