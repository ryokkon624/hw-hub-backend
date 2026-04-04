package com.hwhub.backend.presentation.rest.common;

/** パスワードログインが許可されていない（OAuth専用ユーザー等）場合に投げられる例外。 */
public class PasswordLoginNotAllowedException extends RuntimeException {
  public PasswordLoginNotAllowedException() {
    super("Password login is not allowed for this account.");
  }
}
