package com.hwhub.backend.domain.enums;

public enum Weekday implements CodeEnum {
  SUNDAY("0"),
  MONDAY("1"),
  TUESDAY("2"),
  WEDNESDAY("3"),
  THURSDAY("4"),
  FRIDAY("5"),
  SATURDAY("6");

  private final String code;

  Weekday(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static Weekday fromCode(String code) {
    for (Weekday v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid Weekday code: " + code);
  }
}
