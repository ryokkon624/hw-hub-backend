package com.hwhub.backend.presentation.rest.common;

/** パスワードリセットの回数制限（1日の送信上限等）に達した場合に投げられる例外。 */
public class PasswordResetLimitExceededException extends RuntimeException {
  public PasswordResetLimitExceededException() {
    super("Password reset limit exceeded.");
  }
}
