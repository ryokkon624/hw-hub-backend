package com.hwhub.backend.presentation.rest.auth

import com.hwhub.backend.application.service.UserService
import com.hwhub.backend.application.service.oauth.GoogleOAuthService
import com.hwhub.backend.application.service.oauth.GoogleOAuthUserLoginOrCreateService
import com.hwhub.backend.domain.enums.OAuthFlow
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.oauth.google.GoogleTokenResponse
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo
import com.hwhub.backend.domain.enums.AuthProvider
import com.hwhub.backend.presentation.rest.auth.dto.GoogleMobileLoginRequest
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedForLocalAccountException
import com.hwhub.backend.presentation.rest.common.OAuthEmailNotVerifiedException
import com.hwhub.backend.presentation.rest.common.OAuthIdTokenInvalidException
import com.hwhub.backend.security.JwtProvider
import com.hwhub.backend.security.oauth.OAuthStateSigner
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class GoogleOAuthControllerSpec extends Specification {

    GoogleOAuthService googleOAuthService = Mock()
    GoogleOAuthUserLoginOrCreateService loginOrCreateService = Mock()
    UserService userService = Mock()
    JwtProvider jwtProvider = Mock()
    OAuthStateSigner stateSigner = Mock()
    GoogleOAuthLinkHelper linkHelper = Mock()

    GoogleOAuthController controller = new GoogleOAuthController(
            googleOAuthService,
            loginOrCreateService,
            userService,
            jwtProvider,
            stateSigner,
            linkHelper
    )

    def "startはステート生成、クッキー設定、リダイレクトURL返却を行う"() {
        given:
        def response = new MockHttpServletResponse()
        def state = "generated-state"
        def url = "https://accounts.google.com/..."

        when:
        def result = controller.start(response)

        then:
        1 * linkHelper.generateStateForLogin() >> state
        1 * linkHelper.setStateCookie(response, state)
        1 * googleOAuthService.buildAuthorizationUrl(state) >> url

        result.statusCode == HttpStatus.FOUND
        result.headers.getFirst(HttpHeaders.LOCATION) == url
    }

    def "callbackはエラーパラメータが存在する場合、失敗URLへリダイレクトする"() {
        given:
        def response = new MockHttpServletResponse()
        def failureResponse = ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "failure-url").build()

        when:
        def result = controller.callback(null, null, null, "access_denied", response)

        then:
        1 * linkHelper.clearStateCookie(response)
        1 * linkHelper.redirectToLoginFailure("googleCanceled") >> failureResponse
        result == failureResponse
    }

    def "callbackはcodeが欠如している場合、失敗URLへリダイレクトする"() {
        given:
        def response = new MockHttpServletResponse()
        def failureResponse = ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "failure-url").build()

        when:
        def result = controller.callback(null, "state", "cookie", null, response)

        then:
        1 * linkHelper.clearStateCookie(response)
        1 * linkHelper.redirectToLoginFailure("missingCode") >> failureResponse
        result == failureResponse
    }

    def "callbackはstateが無効な場合、失敗URLへリダイレクトする"() {
        given:
        def response = new MockHttpServletResponse()
        def failureResponse = ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "failure-url").build()

        when:
        def result = controller.callback("code", "state", "cookie", null, response)

        then:
        1 * linkHelper.clearStateCookie(response)
        1 * linkHelper.isValidState("state", "cookie") >> false
        1 * linkHelper.redirectToLoginFailure("stateInvalid") >> failureResponse
        result == failureResponse
    }

    def "callbackはLINKフローを正常に処理する"() {
        given:
        def code = "code"
        def state = "state"
        def cookie = "cookie"
        def response = new MockHttpServletResponse()
        def userId = 123L
        def user = Mock(UserModel)
        def jwt = "jwt-token"
        def successResponse = ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "success-url").build()

        when:
        def result = controller.callback(code, state, cookie, null, response)

        then:
        1 * linkHelper.clearStateCookie(response)
        1 * linkHelper.isValidState(state, cookie) >> true
        1 * stateSigner.extractPurpose(state) >> OAuthFlow.LINK.code
        1 * stateSigner.extractSubject(state) >> "123"
        1 * userService.linkGoogleAccount(userId, code)
        1 * userService.getProfile(userId) >> user
        user.displayName >> "User Name"
        1 * jwtProvider.generateToken(userId, "User Name") >> jwt
        1 * linkHelper.redirectToLinkSuccess("googleLinked", jwt) >> successResponse

        result == successResponse
    }

    def "callbackはLOGINフローを正常に処理する(成功)"() {
        given:
        def code = "code"
        def state = "state"
        def cookie = "cookie"
        def response = new MockHttpServletResponse()
        def tokenResponse = new GoogleTokenResponse(accessToken: "access", idToken: "id", expiresIn: 3600, tokenType: "Bearer")
        def userInfo = new GoogleUserInfo(sub: "sub", email: "email")
        def user = Mock(UserModel)
        def jwt = "jwt-token"
        def successResponse = ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "success-url").build()

        when:
        def result = controller.callback(code, state, cookie, null, response)

        then:
        1 * linkHelper.clearStateCookie(response)
        1 * linkHelper.isValidState(state, cookie) >> true
        1 * stateSigner.extractPurpose(state) >> OAuthFlow.LOGIN.code
        
        1 * googleOAuthService.exchangeCodeForToken(code) >> tokenResponse
        1 * googleOAuthService.fetchUserInfo("access") >> userInfo
        1 * loginOrCreateService.loginOrCreate(userInfo) >> user
        user.userId >> 123L
        user.displayName >> "User Name"
        
        1 * jwtProvider.generateToken(123L, "User Name") >> jwt
        1 * linkHelper.redirectToLoginSuccess("googleLoginSuccess", jwt) >> successResponse

        result == successResponse
    }

    def "callbackはLOGINフローを正常に処理する(メール重複エラー)"() {
        given:
        def code = "code"
        def state = "state"
        def cookie = "cookie"
        def response = new MockHttpServletResponse()
        def tokenResponse = new GoogleTokenResponse(accessToken: "access", idToken: "id", expiresIn: 3600, tokenType: "Bearer")
        def userInfo = new GoogleUserInfo(sub: "sub", email: "email")
        def failureResponse = ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "failure-url").build()

        when:
        def result = controller.callback(code, state, cookie, null, response)

        then:
        1 * linkHelper.clearStateCookie(response)
        1 * linkHelper.isValidState(state, cookie) >> true
        1 * stateSigner.extractPurpose(state) >> OAuthFlow.LOGIN.code
        
        1 * googleOAuthService.exchangeCodeForToken(code) >> tokenResponse
        1 * googleOAuthService.fetchUserInfo("access") >> userInfo
        1 * loginOrCreateService.loginOrCreate(userInfo) >> { throw new EmailAlreadyUsedForLocalAccountException("used") }
        
        1 * linkHelper.redirectToLoginFailure("emailAlreadyUsed") >> failureResponse

        result == failureResponse
    }

    // ── モバイル用エンドポイント ──────────────────────────────────────────

    def "mobileLoginは有効なidTokenのとき200とトークンペアを返す"() {
        given:
        def userInfo = new GoogleUserInfo(sub: "sub-mobile", email: "mobile@example.com", emailVerified: true)
        def user = UserModel.reconstruct(
                77L, "mobile@example.com", "hash", null,
                AuthProvider.GOOGLE.code, "sub-mobile",
                "Mobile User", "ja",
                com.hwhub.backend.domain.enums.ThemeMode.SYSTEM,
                true, null, null, true, null, null
        )
        def req = new GoogleMobileLoginRequest("valid-id-token")

        when:
        def result = controller.mobileLogin(req)

        then:
        1 * googleOAuthService.verifyIdToken("valid-id-token") >> userInfo
        1 * loginOrCreateService.loginOrCreate(userInfo) >> user
        1 * jwtProvider.generateToken(77L, "Mobile User") >> "access-token"
        1 * jwtProvider.generateRefreshToken(77L) >> "refresh-token"

        result.statusCode == HttpStatus.OK
        result.body.accessToken == "access-token"
        result.body.refreshToken == "refresh-token"
    }

    def "mobileLoginはemailVerified=falseのときOAuthEmailNotVerifiedExceptionを投げる"() {
        given:
        def userInfo = new GoogleUserInfo(sub: "sub-unverified", email: "unverified@example.com", emailVerified: false)
        def req = new GoogleMobileLoginRequest("unverified-id-token")

        when:
        controller.mobileLogin(req)

        then:
        1 * googleOAuthService.verifyIdToken("unverified-id-token") >> userInfo
        thrown(OAuthEmailNotVerifiedException)
    }

    def "mobileLoginは無効なidTokenのときOAuthIdTokenInvalidExceptionが伝播する"() {
        given:
        def req = new GoogleMobileLoginRequest("bad-id-token")

        when:
        controller.mobileLogin(req)

        then:
        1 * googleOAuthService.verifyIdToken("bad-id-token") >> { throw new OAuthIdTokenInvalidException() }
        thrown(OAuthIdTokenInvalidException)
    }

    def "mobileLoginはemailが既にローカルアカウントに使用済みのときEmailAlreadyUsedForLocalAccountExceptionが伝播する"() {
        given:
        def userInfo = new GoogleUserInfo(sub: "sub-dup", email: "dup@example.com", emailVerified: true)
        def req = new GoogleMobileLoginRequest("dup-id-token")

        when:
        controller.mobileLogin(req)

        then:
        1 * googleOAuthService.verifyIdToken("dup-id-token") >> userInfo
        1 * loginOrCreateService.loginOrCreate(userInfo) >> { throw new EmailAlreadyUsedForLocalAccountException("used") }
        thrown(EmailAlreadyUsedForLocalAccountException)
    }
}
