package com.hwhub.backend.presentation.rest.common;

/** 既に認証済みのメールアドレスに対して、再度認証完了のアクションを取ろうとした場合に投げられる例外。 */
public class EmailAlreadyVerifiedException extends RuntimeException {
  public EmailAlreadyVerifiedException() {
    super("Email is already verified");
  }
}
