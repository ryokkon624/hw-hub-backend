package com.hwhub.backend.domain.enums;

public enum RecurrenceType implements CodeEnum {
  WEEKLY("1"),
  MONTHLY("2"),
  NTH_WEEKDAY("3");

  private final String code;

  RecurrenceType(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static RecurrenceType fromCode(String code) {
    for (RecurrenceType v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid RecurrenceType code: " + code);
  }
}
