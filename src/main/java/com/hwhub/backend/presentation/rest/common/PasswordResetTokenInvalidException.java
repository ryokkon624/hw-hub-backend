package com.hwhub.backend.presentation.rest.common;

/** パスワードリセットトークンが不正（存在しない、一致しない等）な場合に投げられる例外。 */
public class PasswordResetTokenInvalidException extends RuntimeException {
  public PasswordResetTokenInvalidException() {
    super("Password reset token is invalid.");
  }
}
