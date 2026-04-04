package com.hwhub.backend.presentation.rest.common;

/** ユーザー登録や更新時、指定されたメールアドレスが既に他のユーザーに使用されている場合に投げられる例外。 */
public class EmailAlreadyUsedException extends RuntimeException {
  public EmailAlreadyUsedException(String email) {
    super("Email already used: " + email);
  }
}
