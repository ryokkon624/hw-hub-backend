package com.hwhub.backend.presentation.rest.common;

/** OAuth の state パラメータが一致しない（CSRFの可能性等）場合に投げられる例外。 */
public class OAuthStateMismatchException extends RuntimeException {
  public OAuthStateMismatchException() {
    super("OAuth state mismatch");
  }
}
