package com.hwhub.backend.presentation.rest.common;

public class EmailVerificationTooManyRequestsException extends RuntimeException {
  public EmailVerificationTooManyRequestsException() {
    super("Too many email verification requests");
  }
}
