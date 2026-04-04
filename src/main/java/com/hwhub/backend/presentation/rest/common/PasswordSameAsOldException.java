package com.hwhub.backend.presentation.rest.common;

/** 新しいパスワードが現在のパスワードと同じ場合に投げられる例外。 */
public class PasswordSameAsOldException extends RuntimeException {
  public PasswordSameAsOldException() {
    super("Password is same as old");
  }
}
