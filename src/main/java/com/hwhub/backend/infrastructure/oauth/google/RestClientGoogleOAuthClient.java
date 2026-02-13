package com.hwhub.backend.infrastructure.oauth.google;

import com.hwhub.backend.config.GoogleOAuthProperties;
import com.hwhub.backend.domain.oauth.google.GoogleOAuthClient;
import com.hwhub.backend.domain.oauth.google.GoogleTokenResponse;
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(toFormBody(form))
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
