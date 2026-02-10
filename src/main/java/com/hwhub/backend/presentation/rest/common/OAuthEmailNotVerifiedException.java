package com.hwhub.backend.presentation.rest.common;

public class OAuthEmailNotVerifiedException extends RuntimeException {
  public OAuthEmailNotVerifiedException() {
    super("OAuth email not verified");
  }
}
