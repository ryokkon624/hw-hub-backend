package com.hwhub.backend.domain.model.notification;

import com.hwhub.backend.domain.enums.NotificationGroup;
import com.hwhub.backend.domain.enums.NotificationType;

public class NotificationGroupResolver {

  private NotificationGroupResolver() {}

  public static NotificationGroup resolve(NotificationType type) {
    return switch (type) {
      // 世帯系
      case INVITATION_ACCEPTED,
          INVITATION_DECLINED,
          HAVE_BEEN_REMOVED,
          LEFT_THE_HOUSEHOLD,
          ASSIGNED_TO_THE_OWNER ->
          NotificationGroup.HOUSEHOLD;

      // タスク割当系
      case TASK_ASSIGNED, YOUR_TASK_WAS_TAKEN, BE_DUMPED_TASK -> NotificationGroup.TASK_ASSIGNMENT;
    };
  }
}
