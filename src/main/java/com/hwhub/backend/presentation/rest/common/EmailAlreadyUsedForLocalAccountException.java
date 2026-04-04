package com.hwhub.backend.presentation.rest.common;

/** OAuthログイン時、取得したメールアドレスで既にローカルアカウント（パスワード認証）が作成されている場合に投げられる例外。 */
public class EmailAlreadyUsedForLocalAccountException extends RuntimeException {
  public EmailAlreadyUsedForLocalAccountException(String message) {
    super(message);
  }
}
