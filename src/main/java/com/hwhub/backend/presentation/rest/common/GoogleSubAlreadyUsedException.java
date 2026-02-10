package com.hwhub.backend.presentation.rest.common;

public class GoogleSubAlreadyUsedException extends RuntimeException {
  public GoogleSubAlreadyUsedException() {
    super("This Google account is already used by another user.");
  }
}
