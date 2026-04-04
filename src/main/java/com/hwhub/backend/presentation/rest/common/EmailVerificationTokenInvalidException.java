package com.hwhub.backend.presentation.rest.common;

/** メール認証トークンが不正（存在しない、形式が違う等）な場合に投げられる例外。 */
public class EmailVerificationTokenInvalidException extends RuntimeException {
  public EmailVerificationTokenInvalidException() {
    super("Email verification token is invalid or expired");
  }
}
