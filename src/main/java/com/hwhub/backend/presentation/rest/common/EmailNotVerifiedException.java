package com.hwhub.backend.presentation.rest.common;

public class EmailNotVerifiedException extends RuntimeException {
  public EmailNotVerifiedException() {
    super("Email is not verified");
  }
}
