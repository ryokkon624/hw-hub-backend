package com.hwhub.backend.presentation.rest.common;

public class PasswordPolicyViolationException extends RuntimeException {
  public PasswordPolicyViolationException() {
    super("Password policy violation");
  }
}
