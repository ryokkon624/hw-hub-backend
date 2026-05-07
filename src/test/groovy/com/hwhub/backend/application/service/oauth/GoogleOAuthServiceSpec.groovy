package com.hwhub.backend.application.service.oauth

import com.hwhub.backend.domain.oauth.google.GoogleOAuthClient
import com.hwhub.backend.domain.oauth.google.GoogleTokenResponse
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo
import spock.lang.Specification

class GoogleOAuthServiceSpec extends Specification {

    GoogleOAuthClient client = Mock()
    GoogleOAuthService service = new GoogleOAuthService(client)

    def "buildAuthorizationUrlはクライアントに委譲する"() {
        given:
        String state = "test-state"
        String expectedUrl = "https://accounts.google.com/o/oauth2/v2/auth?..."

        when:
        def result = service.buildAuthorizationUrl(state)

        then:
        1 * client.buildAuthorizationUrl(state) >> expectedUrl
        result == expectedUrl
    }

    def "exchangeCodeForTokenはクライアントに委譲する"() {
        given:
        String code = "auth-code"
        def expectedResponse = new GoogleTokenResponse(accessToken: "access-token", idToken: "id-token", expiresIn: 3600, tokenType: "Bearer")

        when:
        def result = service.exchangeCodeForToken(code)

        then:
        1 * client.exchangeCodeForToken(code) >> expectedResponse
        result == expectedResponse
    }

    def "fetchUserInfoはクライアントに委譲する"() {
        given:
        String accessToken = "access-token"
        def expectedUser = new GoogleUserInfo(sub: "sub-123", name: "User Name", email: "user@example.com")

        when:
        def result = service.fetchUserInfo(accessToken)

        then:
        1 * client.fetchUserInfo(accessToken) >> expectedUser
        result == expectedUser
    }

    def "verifyIdTokenはクライアントに委譲する"() {
        given:
        String idToken = "id-token-from-flutter"
        def expectedUser = new GoogleUserInfo(sub: "sub-456", email: "mobile@example.com", emailVerified: true)

        when:
        def result = service.verifyIdToken(idToken)

        then:
        1 * client.verifyIdToken(idToken) >> expectedUser
        result == expectedUser
    }
}
