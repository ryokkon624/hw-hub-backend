package com.hwhub.backend.presentation.rest.common;

public class EmailAlreadyUsedException extends RuntimeException {
  public EmailAlreadyUsedException(String email) {
    super("Email already used: " + email);
  }
}
