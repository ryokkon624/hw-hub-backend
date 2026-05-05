package com.hwhub.backend.domain.enums;

public enum AnnouncementScope implements CodeEnum {
  ALL("ALL"),
  HOME("HOME"),
  HOUSEWORK_ASSIGN("HW_ASSIGN"),
  MY_TASKS("HW_TASK"),
  HOUSEWORK_SETTINGS("HW_CONF"),
  SHOPPING("SHOPPING"),
  ACCOUNT_SETTINGS("CONF_ACCT"),
  HOUSEHOLD_SETTINGS("CONF_HH"),
  APP_SETTINGS("CONF_APP"),
  NOTIFICATION("NOTIFY"),
  INQUIRY("INQUIRY"),
  ADMIN("ADMIN");

  private final String code;

  AnnouncementScope(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static AnnouncementScope fromCode(String code) {
    for (AnnouncementScope v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid AnnouncementScope code: " + code);
  }
}
