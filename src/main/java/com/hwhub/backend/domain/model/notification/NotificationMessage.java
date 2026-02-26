package com.hwhub.backend.domain.model.notification;

import java.util.Map;

public record NotificationMessage(String titleKey, String bodyKey, Map<String, Object> params) {
  public NotificationMessage {
    if (bodyKey == null || bodyKey.isBlank()) {
      throw new IllegalArgumentException("bodyKey is required");
    }
    // titleKey は任意
    if (params != null && params.isEmpty()) {}
  }

  public static NotificationMessage ofBody(String bodyKey) {
    return new NotificationMessage(null, bodyKey, null);
  }

  public NotificationMessage withParams(Map<String, Object> params) {
    return new NotificationMessage(this.titleKey, this.bodyKey, params);
  }
}
