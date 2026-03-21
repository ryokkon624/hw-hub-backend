package com.hwhub.backend.domain.enums;

public enum NotificationLinkType implements CodeEnum {
  NONE("None"),
  MY_TASKS("MyTasks"),
  HOUSEHOLD("Household"),
  INVITATION("Invite"),
  SETTINGS("Settings"),
  INQUIRY_DETAIL("Inquiry");

  private final String code;

  NotificationLinkType(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static NotificationLinkType fromCode(String code) {
    for (NotificationLinkType v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid NotificationLinkType code: " + code);
  }
}
