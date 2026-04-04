package com.hwhub.backend.presentation.rest.common;

/** パスワード変更時、現在のパスワードが正しくない場合に投げられる例外。 */
public class CurrentPasswordInvalidException extends RuntimeException {
  public CurrentPasswordInvalidException() {
    super("Current password is invalid");
  }
}
