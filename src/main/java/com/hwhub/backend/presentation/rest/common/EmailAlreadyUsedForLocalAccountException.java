package com.hwhub.backend.presentation.rest.common;

public class EmailAlreadyUsedForLocalAccountException extends RuntimeException {
  public EmailAlreadyUsedForLocalAccountException(String message) {
    super(message);
  }
}
