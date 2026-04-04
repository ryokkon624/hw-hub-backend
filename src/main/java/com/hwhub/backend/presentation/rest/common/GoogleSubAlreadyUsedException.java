package com.hwhub.backend.presentation.rest.common;

/** 連携しようとした Google アカウントが、既に別のユーザーに使用されている場合に投げられる例外。 */
public class GoogleSubAlreadyUsedException extends RuntimeException {
  public GoogleSubAlreadyUsedException() {
    super("This Google account is already used by another user.");
  }
}
