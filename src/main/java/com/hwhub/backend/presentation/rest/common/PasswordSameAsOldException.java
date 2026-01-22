package com.hwhub.backend.presentation.rest.common;

public class PasswordSameAsOldException extends RuntimeException {
  public PasswordSameAsOldException() {
    super("Password is same as old");
  }
}
