package com.hwhub.backend.domain.enums;

public enum NotificationGroup implements CodeEnum {
  HOUSEHOLD("100"),
  TASK_ASSIGNMENT("200");

  private final String code;

  NotificationGroup(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static NotificationGroup fromCode(String code) {
    for (NotificationGroup v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid NotificationGroup code: " + code);
  }
}
