package com.hwhub.backend.presentation.rest.common;

public class InvalidRefreshTokenException extends RuntimeException {
  public InvalidRefreshTokenException() {
    super("Invalid or expired refresh token");
  }
}
