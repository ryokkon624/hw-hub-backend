package com.hwhub.backend.presentation.rest.common;

public class PasswordResetTokenInvalidException extends RuntimeException {
  public PasswordResetTokenInvalidException() {
    super("Password reset token is invalid.");
  }
}
