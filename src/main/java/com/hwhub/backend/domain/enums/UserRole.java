package com.hwhub.backend.domain.enums;

public enum UserRole implements CodeEnum {
  ADMIN("ADMIN"),
  SUPPORT("SPPRT");

  private final String code;

  UserRole(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static UserRole fromCode(String code) {
    for (UserRole v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid UserRole code: " + code);
  }
}
