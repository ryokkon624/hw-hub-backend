package com.hwhub.backend.presentation.rest.common;

/** OAuth プロバイダ側でメールアドレスが未認証状態の場合に投げられる例外。 */
public class OAuthEmailNotVerifiedException extends RuntimeException {
  public OAuthEmailNotVerifiedException() {
    super("OAuth email not verified");
  }
}
