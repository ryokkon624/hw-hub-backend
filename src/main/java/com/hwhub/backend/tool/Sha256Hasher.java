package com.hwhub.backend.tool;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Sha256Hasher {

  private Sha256Hasher() {}

  public static byte[] sha256(String value) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      return md.digest(value.getBytes(StandardCharsets.UTF_8)); // 32 bytes
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 not available", e);
    }
  }
}
