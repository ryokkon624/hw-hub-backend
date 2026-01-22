package com.hwhub.backend.presentation.rest.common;

public class PasswordResetLimitExceededException extends RuntimeException {
  public PasswordResetLimitExceededException() {
    super("Password reset limit exceeded.");
  }
}
