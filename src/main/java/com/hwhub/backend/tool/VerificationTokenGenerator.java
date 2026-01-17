package com.hwhub.backend.tool;

import java.security.SecureRandom;
import java.util.Base64;

public final class VerificationTokenGenerator {
  private static final SecureRandom RANDOM = new SecureRandom();

  public static String generateToken() {
    byte[] bytes = new byte[32];
    RANDOM.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }
}
