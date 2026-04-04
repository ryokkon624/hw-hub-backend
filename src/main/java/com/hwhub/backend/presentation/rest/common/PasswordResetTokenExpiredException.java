package com.hwhub.backend.presentation.rest.common;

/** パスワードリセットトークンの有効期限が切れている場合に投げられる例外。 */
public class PasswordResetTokenExpiredException extends RuntimeException {
  public PasswordResetTokenExpiredException() {
    super("Password reset token is expired.");
  }
}
