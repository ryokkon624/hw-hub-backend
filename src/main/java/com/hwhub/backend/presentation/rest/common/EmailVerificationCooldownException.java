package com.hwhub.backend.presentation.rest.common;

/** 認証メールの再送間隔が短すぎる場合に投げられる例外。 */
public class EmailVerificationCooldownException extends RuntimeException {
  public EmailVerificationCooldownException() {
    super("Email verification resend is in cooldown period");
  }
}
