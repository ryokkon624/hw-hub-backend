package com.hwhub.backend.presentation.rest.common;

/** 既に Google アカウントと連携済みのユーザーが、再度連携を試みた場合に投げられる例外。 */
public class GoogleAccountAlreadyLinkedException extends RuntimeException {
  public GoogleAccountAlreadyLinkedException() {
    super("Google account is already linked.");
  }
}
