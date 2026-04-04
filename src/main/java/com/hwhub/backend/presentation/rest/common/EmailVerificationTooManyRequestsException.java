package com.hwhub.backend.presentation.rest.common;

/** 認証メールの送信回数（試行回数）が上限に達した場合に投げられる例外。 */
public class EmailVerificationTooManyRequestsException extends RuntimeException {
  public EmailVerificationTooManyRequestsException() {
    super("Too many email verification requests");
  }
}
