package com.hwhub.backend.infrastructure.oauth.google;

import com.hwhub.backend.config.GoogleOAuthProperties;
import com.hwhub.backend.domain.oauth.google.GoogleOAuthClient;
import com.hwhub.backend.domain.oauth.google.GoogleTokenResponse;
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo;
import com.hwhub.backend.presentation.rest.common.OAuthIdTokenInvalidException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class RestClientGoogleOAuthClient implements GoogleOAuthClient {

  private final GoogleOAuthProperties props;
  private final RestClient restClient = RestClient.create();

  @Override
  public String buildAuthorizationUrl(String state) {
    String base = "https://accounts.google.com/o/oauth2/v2/auth";

    // 最小スコープ
    String scope = "openid email profile";

    return base
        + "?client_id="
        + url(props.getClientId())
        + "&redirect_uri="
        + url(props.getRedirectUri())
        + "&response_type=code"
        + "&scope="
        + url(scope)
        + "&state="
        + url(state)
        + "&prompt=select_account";
  }

  @Override
  public GoogleTokenResponse exchangeCodeForToken(String code) {
    Map<String, String> form =
        Map.of(
            "code",
            code,
            "client_id",
            props.getClientId(),
            "client_secret",
            props.getClientSecret(),
            "redirect_uri",
            props.getRedirectUri(),
            "grant_type",
            "authorization_code");

    return restClient
        .post()
        .uri("https://oauth2.googleapis.com/token")
        .contentType(Objects.requireNonNull(MediaType.APPLICATION_FORM_URLENCODED))
        .body(Objects.requireNonNull(toFormBody(form)))
        .retrieve()
        .body(GoogleTokenResponse.class);
  }

  @Override
  public GoogleUserInfo fetchUserInfo(String accessToken) {
    return restClient
        .get()
        .uri("https://www.googleapis.com/oauth2/v3/userinfo")
        .header("Authorization", "Bearer " + accessToken)
        .retrieve()
        .body(GoogleUserInfo.class);
  }

  @Override
  public GoogleUserInfo verifyIdToken(String idToken) {
    try {
      return restClient
          .get()
          .uri("https://oauth2.googleapis.com/tokeninfo?id_token=" + url(idToken))
          .retrieve()
          .body(GoogleUserInfo.class);
    } catch (RestClientException e) {
      throw new OAuthIdTokenInvalidException();
    }
  }

  private String toFormBody(Map<String, String> form) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (var e : form.entrySet()) {
      if (!first) sb.append("&");
      first = false;
      sb.append(url(e.getKey())).append("=").append(url(e.getValue()));
    }
    return sb.toString();
  }

  private String url(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }
}
