package com.hwhub.backend.presentation.rest.auth;

import com.hwhub.backend.application.service.UserService;
import com.hwhub.backend.application.service.oauth.GoogleOAuthService;
import com.hwhub.backend.application.service.oauth.GoogleOAuthUserLoginOrCreateService;
import com.hwhub.backend.domain.enums.OAuthFlow;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo;
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedForLocalAccountException;
import com.hwhub.backend.security.JwtProvider;
import com.hwhub.backend.security.oauth.OAuthStateSigner;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/google")
public class GoogleOAuthController {

  private final GoogleOAuthService googleOAuthService;
  private final GoogleOAuthUserLoginOrCreateService loginOrCreateService;
  private final UserService userService;
  private final JwtProvider jwtProvider;
  private final OAuthStateSigner stateSigner;
  private final GoogleOAuthLinkHelper linkHelper;

  /** OAuth開始：Googleにリダイレクト - state を生成して Cookie に保存 - 302でGoogleへ */
  @GetMapping("/start")
  public ResponseEntity<Void> start(HttpServletResponse response) {
    String state = linkHelper.generateStateForLogin();
    linkHelper.setStateCookie(response, state);
    String url = googleOAuthService.buildAuthorizationUrl(state);
    return ResponseEntity.status(302).header(HttpHeaders.LOCATION, url).build();
  }

  /** コールバック：code + state 受領 - state 検証（Cookieと一致 & 署名OK & TTL内） - code -> token - userinfo 取得 - */
  @GetMapping("/callback")
  public ResponseEntity<Void> callback(
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "state", required = false) String state,
      @CookieValue(value = GoogleOAuthLinkHelper.STATE_COOKIE_NAME, required = false)
          String stateCookie,
      @RequestParam(value = "error", required = false) String error,
      HttpServletResponse response) {

    linkHelper.clearStateCookie(response);

    if (error != null) return linkHelper.redirectToLoginFailure("googleCanceled");
    if (code == null) return linkHelper.redirectToLoginFailure("missingCode");
    if (!linkHelper.isValidState(state, stateCookie)) {
      return linkHelper.redirectToLoginFailure("stateInvalid");
    }

    String purpose = stateSigner.extractPurpose(state);

    if (OAuthFlow.LINK.getCode().equals(purpose)) {
      Long userId = Long.parseLong(stateSigner.extractSubject(state));
      userService.linkGoogleAccount(userId, code);

      UserModel user = userService.getProfile(userId);
      String jwt = jwtProvider.generateToken(userId, user.getDisplayName());

      return linkHelper.redirectToLinkSuccess("googleLinked", jwt);
    }

    var token = googleOAuthService.exchangeCodeForToken(code);
    GoogleUserInfo info = googleOAuthService.fetchUserInfo(token.getAccessToken());

    try {
      var user = loginOrCreateService.loginOrCreate(info);
      String jwt = jwtProvider.generateToken(user.getUserId(), user.getDisplayName());
      return linkHelper.redirectToLoginSuccess("googleLoginSuccess", jwt);
    } catch (EmailAlreadyUsedForLocalAccountException ex) {
      return linkHelper.redirectToLoginFailure("emailAlreadyUsed");
    }
  }
}
