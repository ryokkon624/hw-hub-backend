package com.hwhub.backend.domain.enums;

public enum NotificationType implements CodeEnum {
  REMOVED_FROM_HOUSEHOLD("MemRemoved"),
  TASKS_ASSIGNED("TskAssign"),
  INVITATION_ACCEPTED("InvAccept"),
  INVITATION_DECLINED("InvDecline"),
  OWNER_CHANGED("OwrAssign");

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
