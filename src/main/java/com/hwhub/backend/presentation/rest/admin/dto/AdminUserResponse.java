package com.hwhub.backend.presentation.rest.admin.dto;

import com.hwhub.backend.domain.model.UserModel;
import java.time.LocalDateTime;

public record AdminUserResponse(
    long userId,
    String email,
    String authProvider,
    String displayName,
    String locale,
    boolean notificationEnabled,
    boolean isActive,
    String iconUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {

  public static AdminUserResponse from(UserModel model) {
    return new AdminUserResponse(
        model.getUserId(),
        model.getEmail(),
        model.getAuthProvider(),
        model.getDisplayName(),
        model.getLocale(),
        model.isNotificationEnabled(),
        model.isActive(),
        model.getIconUrl(),
        model.getCreatedAt(),
        model.getUpdatedAt());
  }
}
