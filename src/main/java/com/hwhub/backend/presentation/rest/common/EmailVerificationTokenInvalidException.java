package com.hwhub.backend.presentation.rest.common;

public class EmailVerificationTokenInvalidException extends RuntimeException {
  public EmailVerificationTokenInvalidException() {
    super("Email verification token is invalid or expired");
  }
}
