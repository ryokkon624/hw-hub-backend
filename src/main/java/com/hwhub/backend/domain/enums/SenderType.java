package com.hwhub.backend.domain.enums;

public enum SenderType implements CodeEnum {
  USER("USER"),
  AI("AI"),
  STAFF("STAFF");

  private final String code;

  SenderType(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static SenderType fromCode(String code) {
    for (SenderType v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid SenderType code: " + code);
  }
}
