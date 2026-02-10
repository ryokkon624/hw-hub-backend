package com.hwhub.backend.presentation.rest.common;

public class OAuthEmailAlreadyRegisteredException extends RuntimeException {
  public OAuthEmailAlreadyRegisteredException() {
    super(
        "This email is already registered. Please login with email/password and link Google account from settings.");
  }
}
