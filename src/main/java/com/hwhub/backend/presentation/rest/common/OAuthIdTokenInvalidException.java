package com.hwhub.backend.presentation.rest.common;

/** OAuth の ID トークンが不正、または検証に失敗した場合に投げられる例外。 */
public class OAuthIdTokenInvalidException extends RuntimeException {
  public OAuthIdTokenInvalidException() {
    super("OAuth id token invalid");
  }
}
