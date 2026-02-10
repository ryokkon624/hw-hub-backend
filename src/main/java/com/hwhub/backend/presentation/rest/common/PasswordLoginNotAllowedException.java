package com.hwhub.backend.presentation.rest.common;

public class PasswordLoginNotAllowedException extends RuntimeException {
  public PasswordLoginNotAllowedException() {
    super("Password login is not allowed for this account.");
  }
}
