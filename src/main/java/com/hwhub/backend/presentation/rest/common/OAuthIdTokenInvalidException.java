package com.hwhub.backend.presentation.rest.common;

public class OAuthIdTokenInvalidException extends RuntimeException {
  public OAuthIdTokenInvalidException() {
    super("OAuth id token invalid");
  }
}
