package com.hwhub.backend.presentation.rest.common;

/** パスワードリセットメールの再送間隔が短すぎる場合に投げられる例外。 */
public class PasswordResetCooldownException extends RuntimeException {
  public PasswordResetCooldownException() {
    super("Password reset is in cooldown period.");
  }
}
