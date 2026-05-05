package com.hwhub.backend.domain.enums;

public enum AnnouncementSeverity implements CodeEnum {
  INFO("INFO"),
  WARNING("WARN"),
  ERROR("ERROR");

  private final String code;

  AnnouncementSeverity(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static AnnouncementSeverity fromCode(String code) {
    for (AnnouncementSeverity v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid AnnouncementSeverity code: " + code);
  }
}
