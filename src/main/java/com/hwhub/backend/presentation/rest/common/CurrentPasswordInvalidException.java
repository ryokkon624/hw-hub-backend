package com.hwhub.backend.presentation.rest.common;

public class CurrentPasswordInvalidException extends RuntimeException {
  public CurrentPasswordInvalidException() {
    super("Current password is invalid");
  }
}
