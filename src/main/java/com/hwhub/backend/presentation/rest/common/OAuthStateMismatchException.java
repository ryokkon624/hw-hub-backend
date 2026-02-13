package com.hwhub.backend.presentation.rest.common;

public class OAuthStateMismatchException extends RuntimeException {
  public OAuthStateMismatchException() {
    super("OAuth state mismatch");
  }
}
