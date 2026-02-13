package com.hwhub.backend.presentation.rest.auth

import com.hwhub.backend.application.service.oauth.GoogleOAuthService
import com.hwhub.backend.config.GoogleOAuthProperties
import com.hwhub.backend.domain.enums.OAuthFlow
import com.hwhub.backend.security.oauth.OAuthStateSigner
import org.springframework.http.HttpHeaders
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class GoogleOAuthLinkHelperSpec extends Specification {

    GoogleOAuthProperties props = new GoogleOAuthProperties()
    GoogleOAuthService googleOAuthService = Mock()
    OAuthStateSigner stateSigner = Mock()

    GoogleOAuthLinkHelper helper

    def setup() {
        props.oauthStateSecret = "secret"
        props.stateTtlSeconds = 3600
        props.frontBaseUrl = "http://localhost:3000"
        props.googleLinkSuccessRedirectPath = "/settings"
        props.googleLinkFailureRedirectPath = "/settings"
        props.successRedirectPath = "/login"
        props.failureRedirectPath = "/login"
        
        helper = new GoogleOAuthLinkHelper(props, googleOAuthService, stateSigner)
    }

    def "generateStateForLinkは正しいパラメータでstateSignerを呼び出す"() {
        when:
        helper.generateStateForLink(123L)

        then:
        1 * stateSigner.generate(OAuthFlow.LINK.code, "123", "secret", 3600)
    }

    def "generateStateForLoginは正しいパラメータでstateSignerを呼び出す"() {
        when:
        helper.generateStateForLogin()

        then:
        1 * stateSigner.generate(OAuthFlow.LOGIN.code, "", "secret", 3600)
    }

    def "verifyStateはstateSignerに委譲する"() {
        when:
        helper.verifyState("state")

        then:
        1 * stateSigner.verify("state", "secret")
    }

    def "isValidStateは等価性と署名をチェックする"() {
        given:
        stateSigner.verify("valid", "secret") >> true

        expect:
        helper.isValidState("valid", "valid") == true
        helper.isValidState("valid", "invalid") == false
        helper.isValidState(null, "valid") == false
        helper.isValidState("valid", null) == false
    }

    def "extractUserIdFromStateは有効なサブジェクトをパースする"() {
        given:
        stateSigner.extractSubject("state") >> "456"

        when:
        def result = helper.extractUserIdFromState("state")

        then:
        result == 456L
    }

    def "extractUserIdFromStateは無効なサブジェクトの場合例外を投げる"() {
        given:
        stateSigner.extractSubject("state") >> "not-a-number"

        when:
        helper.extractUserIdFromState("state")

        then:
        thrown(IllegalArgumentException)
    }
    
    def "extractUserIdFromStateはサブジェクトがnullの場合例外を投げる"() {
            given:
            stateSigner.extractSubject("state") >> null
    
            when:
            helper.extractUserIdFromState("state")
    
            then:
            thrown(IllegalArgumentException)
    }

    def "setStateCookieは正しいクッキーを追加する"() {
        given:
        def response = new MockHttpServletResponse()

        when:
        helper.setStateCookie(response, "test-state")

        then:
        def cookie = response.getCookie("hwhub_oauth_state")
        cookie != null
        cookie.value == "test-state"
        cookie.maxAge == 3600
        cookie.path == "/"
        cookie.secure == false // default in props
        cookie.httpOnly == true
    }

    def "clearStateCookieはクッキーをクリアする"() {
        given:
        def response = new MockHttpServletResponse()

        when:
        helper.clearStateCookie(response)

        then:
        def cookie = response.getCookie("hwhub_oauth_state")
        cookie != null
        cookie.value == ""
        cookie.maxAge == 0
    }

    def "redirectToLinkSuccessは正しいURLを構築する"() {
        when:
        def result = helper.redirectToLinkSuccess("linked", "token123")

        then:
        result.statusCodeValue == 302
        result.headers[HttpHeaders.LOCATION][0] == "http://localhost:3000/settings?notice=linked&token=token123"
    }

    def "redirectToLinkFailureは正しいURLを構築する"() {
        when:
        def result = helper.redirectToLinkFailure("reason")

        then:
        result.statusCodeValue == 302
        result.headers[HttpHeaders.LOCATION][0] == "http://localhost:3000/settings?notice=googleLinkFailed&reason=reason"
    }
    
    def "redirectToLoginSuccessは正しいURLを構築する"() {
        when:
        def result = helper.redirectToLoginSuccess("welcome", "tokenABC")

        then:
        result.statusCodeValue == 302
        result.headers[HttpHeaders.LOCATION][0] == "http://localhost:3000/login?notice=welcome&token=tokenABC"
    }
    
    def "redirectToLoginFailureは正しいURLを構築する"() {
        when:
        def result = helper.redirectToLoginFailure("error")

        then:
        result.statusCodeValue == 302
        result.headers[HttpHeaders.LOCATION][0] == "http://localhost:3000/login?notice=googleLoginFailed&reason=error"
    }
}
