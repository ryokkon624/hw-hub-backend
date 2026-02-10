package com.hwhub.backend.domain.oauth.google;

/**
 * Google OAuth / OpenID Connect との外部通信を抽象化する Port（インターフェース）。
 *
 * <p>本インターフェースは Domain/Application 層から Google 認証基盤を利用するための 抽象ポートとして定義される。
 *
 * <p>実装は Infrastructure 層に配置し、 HTTP 通信や JSON マッピングなどの技術的関心事はこのポートの外側に閉じ込める。
 *
 * <p>責務：
 *
 * <ul>
 *   <li>Google 認可エンドポイント（Authorization Endpoint）URL の生成
 *   <li>Authorization Code を Access Token / ID Token に交換
 *   <li>Google UserInfo Endpoint からユーザー情報を取得
 * </ul>
 *
 * <p>非責務：
 *
 * <ul>
 *   <li>hwhubのユーザー登録・更新（m_user 操作）
 *   <li>JWT発行
 *   <li>認証セッション管理
 * </ul>
 *
 * <p>これらは Application Service（例：GoogleOAuthUserLoginOrCreateService） が担う。
 */
public interface GoogleOAuthClient {

  /**
   * Google 認可エンドポイント（Authorization Endpoint）へリダイレクトするための URL を生成する。
   *
   * <p>生成される URL には以下の OAuth2 / OIDC パラメータが含まれる：
   *
   * <ul>
   *   <li>client_id
   *   <li>redirect_uri
   *   <li>response_type=code
   *   <li>scope（openid email profile 等）
   *   <li>state（CSRF / Flow 識別用）
   * </ul>
   *
   * <p>state は CSRF 防止およびログイン / 連携フロー識別に利用されるため、 呼び出し側で署名付きトークンを生成して渡すこと。
   *
   * @param state OAuth2 state パラメータ（署名済み）
   * @return Google 認可画面へ遷移するための完全な URL
   */
  String buildAuthorizationUrl(String state);

  /**
   * Authorization Code を Google Token Endpoint に送信し、 Access Token / ID Token / Refresh Token
   * 等を取得する。
   *
   * <p>通信仕様：
   *
   * <ul>
   *   <li>POST https://oauth2.googleapis.com/token
   *   <li>Content-Type: application/x-www-form-urlencoded
   * </ul>
   *
   * <p>主なリクエストパラメータ：
   *
   * <ul>
   *   <li>code
   *   <li>client_id
   *   <li>client_secret
   *   <li>redirect_uri
   *   <li>grant_type=authorization_code
   * </ul>
   *
   * @param code Google からコールバックで返却される Authorization Code
   * @return トークンレスポンス（AccessToken / IdToken 等を含む）
   */
  GoogleTokenResponse exchangeCodeForToken(String code);

  /**
   * Google UserInfo Endpoint を呼び出し、ユーザー情報を取得する。
   *
   * <p>通信仕様：
   *
   * <ul>
   *   <li>GET https://www.googleapis.com/oauth2/v3/userinfo
   *   <li>Authorization: Bearer {accessToken}
   * </ul>
   *
   * <p>主に取得される情報：
   *
   * <ul>
   *   <li>sub（Google 内部ユーザーID）
   *   <li>email
   *   <li>email_verified
   *   <li>name
   *   <li>picture
   * </ul>
   *
   * <p>sub は Google アカウントを一意に識別する不変IDであり、 自アプリでは auth_provider_id として利用する。
   *
   * @param accessToken Token Endpoint で取得した Access Token
   * @return Google ユーザー情報
   */
  GoogleUserInfo fetchUserInfo(String accessToken);
}
