package com.hwhub.backend.application.service.oauth;

import com.hwhub.backend.domain.oauth.google.GoogleOAuthClient;
import com.hwhub.backend.domain.oauth.google.GoogleTokenResponse;
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Google OAuth のアプリケーション層サービス。
 *
 * <p>外部HTTP通信は domain ポート（GoogleOAuthClient）に委譲する。
 */
@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

  private final GoogleOAuthClient client;

  /**
   * Google 認可エンドポイント用の URL を生成する
   *
   * @param state
   * @return
   */
  public String buildAuthorizationUrl(String state) {
    return client.buildAuthorizationUrl(state);
  }

  public GoogleTokenResponse exchangeCodeForToken(String code) {
    return client.exchangeCodeForToken(code);
  }

  public GoogleUserInfo fetchUserInfo(String accessToken) {
    return client.fetchUserInfo(accessToken);
  }

  public GoogleUserInfo verifyIdToken(String idToken) {
    return client.verifyIdToken(idToken);
  }
}
