package com.hwhub.backend.application.service.notification;

import com.hwhub.backend.domain.enums.NotificationGroup;
import com.hwhub.backend.domain.enums.NotificationType;
import com.hwhub.backend.domain.model.notification.NotificationGroupResolver;
import com.hwhub.backend.domain.repository.UserNotificationSettingRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationPermissionService {

  private final UserRepository userRepository;
  private final UserNotificationSettingRepository userNotificationSettingRepository;

  /**
   * 通知を受け取れるかどうかを返す。
   *
   * <p>差分運用のため、m_notification_settingsにレコードがない場合はデフォルトONであると判断する。
   *
   * <ul>
   *   <li>m_user.notification_enabled=false => false
   *   <li>m_user.notification_enabled=true かつ m_notification_settingsにレコードがない => true
   *   <li>m_user.notification_enabled=true かつ m_notification_settingsにレコードがある => その値
   * </ul>
   *
   * @param userId ユーザーID
   * @param type 通知タイプ
   * @return 通知を受け取れるかどうか
   */
  public boolean canReceive(Long userId, NotificationType type) {

    if (!userRepository.isNotificationEnabled(userId)) {
      return false;
    }

    NotificationGroup group = NotificationGroupResolver.resolve(type);

    Optional<Boolean> groupEnabled = userNotificationSettingRepository.findEnabled(userId, group);
    return groupEnabled.orElse(true);
  }
}
