package com.hwhub.backend.presentation.rest.common;

/** 設定しようとしたパスワードが、システムのパスワードポリシー（長さ、文字種等）に違反している場合に投げられる例外。 */
public class PasswordPolicyViolationException extends RuntimeException {
  public PasswordPolicyViolationException() {
    super("Password policy violation");
  }
}
