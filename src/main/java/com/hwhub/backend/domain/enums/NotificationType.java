package com.hwhub.backend.domain.enums;

public enum NotificationType implements CodeEnum {
  INVITATION_ACCEPTED("0101"),
  INVITATION_DECLINED("0102"),
  HAVE_BEEN_REMOVED("0201"),
  LEFT_THE_HOUSEHOLD("0202"),
  ASSIGNED_TO_THE_OWNER("0203"),
  TASK_ASSIGNED("0301"),
  BE_DUMPED_TASK("0302"),
  YOUR_TASK_WAS_TAKEN("0303"),
  YOUR_INQUIRY_HAS_BEEN_REPLIED("0401");

  private final String code;

  NotificationType(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static NotificationType fromCode(String code) {
    for (NotificationType v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid NotificationType code: " + code);
  }
}
