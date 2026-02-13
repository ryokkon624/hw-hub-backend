package com.hwhub.backend.presentation.rest.common;

public class GoogleAccountAlreadyLinkedException extends RuntimeException {
  public GoogleAccountAlreadyLinkedException() {
    super("Google account is already linked.");
  }
}
