package com.hwhub.backend.presentation.rest.auth

import com.hwhub.backend.application.service.AuthService
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.presentation.rest.auth.dto.LoginRequest
import com.hwhub.backend.presentation.rest.auth.dto.LoginResponse
import com.hwhub.backend.presentation.rest.auth.dto.LoginUserDto
import com.hwhub.backend.presentation.rest.auth.dto.RegisterRequest
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
                "Taro",
                "ja",
                "profile-key",
                true
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
                "Hanako",
                "ja",
                null,
                true
        )

        when:
        def response = controller.register(request)

        then:
        1 * authService.register(_ as UserModel) >> { UserModel arg ->
            // Controller が組み立てた UserModel が正しいかチェック
            assert arg.userId == null          // create なので null のはず
            assert arg.email == "new@example.com"
            assert arg.password == "plain-pass"
            assert arg.displayName == "Hanako"
            assert arg.locale == "ja"
            // 戻り値として LoginInfo を返す
            new AuthService.LoginInfo("reg-token-456", inserted)
        }

        and:
        response.statusCode == HttpStatus.OK

        LoginResponse body = response.body
        body.accessToken == "reg-token-456"

        LoginUserDto dto = body.user
        dto.getUserId() == 10L
        dto.getEmail() == "new@example.com"
        dto.getDisplayName() == "Hanako"
        dto.getLocale() == "ja"
    }
}
