package com.hwhub.backend.domain.enums;

public enum Category implements CodeEnum {
  PET("PET"),
  CLEANING("CLEAN"),
  GARBAGE("GARBAGE"),
  GARDEN("GARDEN"),
  KITCHEN("KITCHEN"),
  OTHER("OTHER");

  private final String code;

  Category(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static Category fromCode(String code) {
    for (Category v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid Category code: " + code);
  }
}
