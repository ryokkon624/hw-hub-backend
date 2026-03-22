package com.hwhub.backend.domain.enums;

public enum Permission implements CodeEnum {
  USER_LIST_VIEW("10"),
  ROLE_MANAGEMENT("11"),
  INQUIRY_REPLY("20");

  private final String code;

  Permission(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static Permission fromCode(String code) {
    for (Permission v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid Permission code: " + code);
  }
}
