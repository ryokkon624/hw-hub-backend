package com.hwhub.backend.infrastructure.oauth.google

import com.hwhub.backend.config.GoogleOAuthProperties
import com.hwhub.backend.domain.oauth.google.GoogleTokenResponse
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo
import com.hwhub.backend.presentation.rest.common.OAuthIdTokenInvalidException
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestClient
import spock.lang.Specification

class RestClientGoogleOAuthClientSpec extends Specification {

    GoogleOAuthProperties props = new GoogleOAuthProperties()
    RestClientGoogleOAuthClient client
    MockRestServiceServer server

    def setup() {
        props.clientId = "test-client-id"
        props.clientSecret = "test-client-secret"
        props.redirectUri = "http://localhost:8080/callback"

        // RestClientGoogleOAuthClient は内部で RestClient.create() を使用しているため、
        // ReflectionTestUtils で restClient フィールドを差し替える
        def builder = RestClient.builder()
        server = MockRestServiceServer.bindTo(builder).build()

        client = new RestClientGoogleOAuthClient(props)
        ReflectionTestUtils.setField(client, "restClient", builder.build())
    }

    def "buildAuthorizationUrlは正しいURLを生成する"() {
        given:
        String state = "test-state"

        when:
        String url = client.buildAuthorizationUrl(state)

        then:
        url.startsWith("https://accounts.google.com/o/oauth2/v2/auth")
        url.contains("client_id=test-client-id")
        url.contains("redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fcallback")
        url.contains("response_type=code")
        url.contains("scope=openid+email+profile")
        url.contains("state=test-state")
        url.contains("prompt=select_account")
    }

    def "exchangeCodeForTokenは正しいリクエストを送信しレスポンスを返す"() {
        given:
        String code = "auth-code"
        String responseJson = """
            {
                "access_token": "access-token-123",
                "id_token": "id-token-456",
                "expires_in": 3600,
                "token_type": "Bearer"
            }
        """

        server.expect(MockRestRequestMatchers.requestTo("https://oauth2.googleapis.com/token"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andRespond(MockRestResponseCreators.withSuccess(responseJson, MediaType.APPLICATION_JSON))

        when:
        GoogleTokenResponse response = client.exchangeCodeForToken(code)

        then:
        response.accessToken == "access-token-123"
        response.idToken == "id-token-456"
        response.expiresIn == 3600
        response.tokenType == "Bearer"
    }

    def "fetchUserInfoは正しいリクエストを送信しレスポンスを返す"() {
        given:
        String accessToken = "access-token-123"
        String responseJson = """
            {
                "sub": "sub-123",
                "name": "Test User",
                "email": "test@example.com",
                "picture": "http://example.com/pic",
                "email_verified": true,
                "locale": "en",
                "given_name": "Test",
                "family_name": "User"
            }
        """

        server.expect(MockRestRequestMatchers.requestTo("https://www.googleapis.com/oauth2/v3/userinfo"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andExpect(MockRestRequestMatchers.header("Authorization", "Bearer access-token-123"))
                .andRespond(MockRestResponseCreators.withSuccess(responseJson, MediaType.APPLICATION_JSON))

        when:
        GoogleUserInfo userInfo = client.fetchUserInfo(accessToken)

        then:
        userInfo.sub == "sub-123"
        userInfo.name == "Test User"
        userInfo.email == "test@example.com"
        userInfo.picture == "http://example.com/pic"
        userInfo.emailVerified == true
    }

    def "verifyIdTokenは正しいリクエストを送信しGoogleUserInfoを返す"() {
        given:
        String idToken = "dummy.id.token"
        String responseJson = """
            {
                "sub": "sub-456",
                "name": "Mobile User",
                "email": "mobile@example.com",
                "picture": "http://example.com/pic2",
                "email_verified": true,
                "locale": "ja",
                "given_name": "Mobile",
                "family_name": "User",
                "aud": "test-client-id",
                "iss": "https://accounts.google.com"
            }
        """

        server.expect(MockRestRequestMatchers.requestTo("https://oauth2.googleapis.com/tokeninfo?id_token=dummy.id.token"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(responseJson, MediaType.APPLICATION_JSON))

        when:
        GoogleUserInfo userInfo = client.verifyIdToken(idToken)

        then:
        userInfo.sub == "sub-456"
        userInfo.email == "mobile@example.com"
        userInfo.emailVerified == true
    }

    def "verifyIdTokenはTokeninfo APIがエラーを返した場合OAuthIdTokenInvalidExceptionをスローする"() {
        given:
        String idToken = "invalid.id.token"

        server.expect(MockRestRequestMatchers.requestTo("https://oauth2.googleapis.com/tokeninfo?id_token=invalid.id.token"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withBadRequest())

        when:
        client.verifyIdToken(idToken)

        then:
        thrown(OAuthIdTokenInvalidException)
    }

    def "verifyIdTokenは別クライアント宛て(aud不一致)のidTokenを拒否する"() {
        given:
        String idToken = "attacker.id.token"
        String responseJson = """
            {
                "sub": "sub-789",
                "email": "victim@example.com",
                "email_verified": true,
                "aud": "attacker-client-id",
                "iss": "https://accounts.google.com"
            }
        """

        server.expect(MockRestRequestMatchers.requestTo("https://oauth2.googleapis.com/tokeninfo?id_token=attacker.id.token"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(responseJson, MediaType.APPLICATION_JSON))

        when:
        client.verifyIdToken(idToken)

        then:
        thrown(OAuthIdTokenInvalidException)
    }

    def "verifyIdTokenはissが不正なidTokenを拒否する"() {
        given:
        String idToken = "bad-iss.id.token"
        String responseJson = """
            {
                "sub": "sub-789",
                "email": "victim@example.com",
                "email_verified": true,
                "aud": "test-client-id",
                "iss": "https://evil.example.com"
            }
        """

        server.expect(MockRestRequestMatchers.requestTo("https://oauth2.googleapis.com/tokeninfo?id_token=bad-iss.id.token"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(responseJson, MediaType.APPLICATION_JSON))

        when:
        client.verifyIdToken(idToken)

        then:
        thrown(OAuthIdTokenInvalidException)
    }

    def "verifyIdTokenはaudが欠落したidTokenを拒否する"() {
        given:
        String idToken = "no-aud.id.token"
        String responseJson = """
            {
                "sub": "sub-789",
                "email": "victim@example.com",
                "email_verified": true,
                "iss": "https://accounts.google.com"
            }
        """

        server.expect(MockRestRequestMatchers.requestTo("https://oauth2.googleapis.com/tokeninfo?id_token=no-aud.id.token"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(responseJson, MediaType.APPLICATION_JSON))

        when:
        client.verifyIdToken(idToken)

        then:
        thrown(OAuthIdTokenInvalidException)
    }
}
