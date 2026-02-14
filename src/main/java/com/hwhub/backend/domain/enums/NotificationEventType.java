package com.hwhub.backend.domain.enums;

public enum NotificationEventType implements CodeEnum {
  TASK_ASSIGNED("TskAssign");

  private final String code;

  NotificationEventType(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static NotificationEventType fromCode(String code) {
    for (NotificationEventType v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid NotificationEventType code: " + code);
  }
}
