package com.hwhub.backend.presentation.rest.common;

/** パスワードリセットが無効なユーザー（OAuth専用アカウント等）に対してパスワードリセットを試みた場合に投げられる例外。 */
public class PasswordResetDisabledException extends RuntimeException {
  public PasswordResetDisabledException() {
    super("Password reset is currently disabled.");
  }
}
