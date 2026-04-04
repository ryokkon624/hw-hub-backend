package com.hwhub.backend.presentation.rest.common;

/** メール認証が完了していないユーザーがログインを試みた場合に投げられる例外。 */
public class EmailNotVerifiedException extends RuntimeException {
  public EmailNotVerifiedException() {
    super("Email is not verified");
  }
}
