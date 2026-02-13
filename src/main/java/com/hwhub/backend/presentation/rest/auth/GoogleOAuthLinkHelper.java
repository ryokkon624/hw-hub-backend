package com.hwhub.backend.presentation.rest.auth;

import com.hwhub.backend.application.service.oauth.GoogleOAuthService;
import com.hwhub.backend.config.GoogleOAuthProperties;
import com.hwhub.backend.domain.enums.OAuthFlow;
import com.hwhub.backend.security.oauth.OAuthStateSigner;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOAuthLinkHelper {

  public static final String STATE_COOKIE_NAME = "hwhub_oauth_state";

  private final GoogleOAuthProperties props;
  private final GoogleOAuthService googleOAuthService;
  private final OAuthStateSigner stateSigner;

  public String generateStateForLink(Long userId) {
    // subject=userId を payload に設定
    return stateSigner.generate(
        OAuthFlow.LINK.getCode(),
        userId.toString(),
        props.getOauthStateSecret(),
        props.getStateTtlSeconds());
  }

  public String generateStateForLogin() {
    // ログイン時にはuserIdが未確定のため空文字を設定
    return stateSigner.generate(
        OAuthFlow.LOGIN.getCode(), "", props.getOauthStateSecret(), props.getStateTtlSeconds());
  }

  public boolean verifyState(String state) {
    return stateSigner.verify(state, props.getOauthStateSecret());
  }

  /** state の cookie 一致 + 署名/TTL 検証 */
  public boolean isValidState(String state, String stateCookie) {
    if (state == null || stateCookie == null) return false;
    if (!state.equals(stateCookie)) return false;
    return verifyState(state);
  }

  public Long extractUserIdFromState(String state) {
    String subject = stateSigner.extractSubject(state);
    if (subject == null || subject.isBlank()) {
      throw new IllegalArgumentException("Invalid state subject");
    }
    try {
      return Long.parseLong(subject);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid state subject: not a number", e);
    }
  }

  public String buildAuthorizationUrl(String state) {
    return googleOAuthService.buildAuthorizationUrl(state);
  }

  public void setStateCookie(HttpServletResponse response, String state) {
    Cookie c = new Cookie(STATE_COOKIE_NAME, state);
    c.setHttpOnly(true);
    c.setSecure(props.isStateCookieSecure());
    c.setPath("/");
    c.setMaxAge((int) Duration.ofSeconds(props.getStateTtlSeconds()).toSeconds());
    c.setAttribute("SameSite", "Lax");
    response.addCookie(c);
  }

  public void clearStateCookie(HttpServletResponse response) {
    Cookie c = new Cookie(STATE_COOKIE_NAME, "");
    c.setHttpOnly(true);
    c.setSecure(props.isStateCookieSecure());
    c.setPath("/");
    c.setMaxAge(0);
    c.setAttribute("SameSite", "Lax");
    response.addCookie(c);
  }

  /** Google連携 成功時：設定画面へ戻す（token付き） */
  public ResponseEntity<Void> redirectToLinkSuccess(String notice, String token) {
    String base = props.getFrontBaseUrl();
    String path = props.getGoogleLinkSuccessRedirectPath();
    String url = base + path + "?notice=" + urlEncode(notice) + "&token=" + urlEncode(token);

    return ResponseEntity.status(302).header(HttpHeaders.LOCATION, url).build();
  }

  /** Google連携 失敗時：設定画面へ戻す（reason付き） */
  public ResponseEntity<Void> redirectToLinkFailure(String reason) {
    String base = props.getFrontBaseUrl();
    String path = props.getGoogleLinkFailureRedirectPath();
    String url = base + path + "?notice=googleLinkFailed" + "&reason=" + urlEncode(reason);

    return ResponseEntity.status(302).header(HttpHeaders.LOCATION, url).build();
  }

  /** ログイン成功時：/loginへ戻す（token付き） */
  public ResponseEntity<Void> redirectToLoginSuccess(String notice, String token) {
    String base = props.getFrontBaseUrl();
    String path = props.getSuccessRedirectPath();
    String url = base + path + "?notice=" + urlEncode(notice) + "&token=" + urlEncode(token);
    return ResponseEntity.status(302).header(HttpHeaders.LOCATION, url).build();
  }

  /** ログイン失敗時：/loginへ戻す（reason付き） */
  public ResponseEntity<Void> redirectToLoginFailure(String reason) {
    String base = props.getFrontBaseUrl();
    String path = props.getFailureRedirectPath();
    String url = base + path + "?notice=googleLoginFailed&reason=" + urlEncode(reason);
    return ResponseEntity.status(302).header(HttpHeaders.LOCATION, url).build();
  }

  private String urlEncode(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }
}
