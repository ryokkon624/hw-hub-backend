package com.hwhub.backend.presentation.rest.common;

/** OAuth で取得したメールアドレスが既に別の方法で登録されている場合に投げられる例外。 */
public class OAuthEmailAlreadyRegisteredException extends RuntimeException {
  public OAuthEmailAlreadyRegisteredException() {
    super(
        "This email is already registered. Please login with email/password and link Google account from settings.");
  }
}
