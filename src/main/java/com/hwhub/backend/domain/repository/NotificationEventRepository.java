package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.notification.NotificationEventId;
import com.hwhub.backend.domain.model.notification.NotificationEventModel;

public interface NotificationEventRepository {

  /** イベント新規登録 */
  NotificationEventId insert(NotificationEventModel model, Long loginUserId, String program);
}
