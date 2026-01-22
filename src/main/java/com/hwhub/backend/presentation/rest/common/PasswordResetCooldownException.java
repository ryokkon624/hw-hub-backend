package com.hwhub.backend.presentation.rest.common;

public class PasswordResetCooldownException extends RuntimeException {
  public PasswordResetCooldownException() {
    super("Password reset is in cooldown period.");
  }
}
