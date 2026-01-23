package com.hwhub.backend.presentation.rest.common;

public class PasswordResetTokenExpiredException extends RuntimeException {
  public PasswordResetTokenExpiredException() {
    super("Password reset token is expired.");
  }
}
