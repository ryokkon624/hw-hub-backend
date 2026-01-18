package com.hwhub.backend.presentation.rest.common;

public class EmailVerificationCooldownException extends RuntimeException {
  public EmailVerificationCooldownException() {
    super("Email verification resend is in cooldown period");
  }
}
