package com.hwhub.backend.presentation.rest.common;

public class PasswordResetDisabledException extends RuntimeException {
  public PasswordResetDisabledException() {
    super("Password reset is currently disabled.");
  }
}
