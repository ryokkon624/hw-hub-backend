package com.hwhub.backend.domain.model.notification;

public record NotificationId(long value) {
  public NotificationId {
    if (value <= 0) throw new IllegalArgumentException("notificationId must be positive");
  }
}
