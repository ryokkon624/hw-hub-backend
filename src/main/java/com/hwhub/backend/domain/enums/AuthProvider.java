package com.hwhub.backend.domain.enums;

public enum AuthProvider implements CodeEnum {
  LOCAL("LOCAL"),
  GOOGLE("GOOGLE");

  private final String code;

  AuthProvider(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static AuthProvider fromCode(String code) {
    for (AuthProvider v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid AuthProvider code: " + code);
  }
}
