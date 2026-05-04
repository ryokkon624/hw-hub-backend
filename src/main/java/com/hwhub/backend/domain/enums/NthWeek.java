package com.hwhub.backend.domain.enums;

public enum NthWeek implements CodeEnum {
  FIRST_WEEK("1"),
  SECOND_WEEK("2"),
  THIRD_WEEK("3"),
  FOURTH_WEEK("4"),
  LAST_WEEK("5");

  private final String code;

  NthWeek(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static NthWeek fromCode(String code) {
    for (NthWeek v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid NthWeek code: " + code);
  }
}
