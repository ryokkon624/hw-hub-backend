package com.hwhub.backend.application.service.notification;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.notification.NotificationModel;
import com.hwhub.backend.domain.repository.NotificationRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationQueryService {

  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;

  /**
   * 通知一覧取得
   *
   * @param targetUserId 対象ユーザー
   * @param limit 件数
   * @param markRead 一覧表示と同時に既読化するか
   */
  @Transactional
  public List<NotificationModel> getNotifications(Long targetUserId, int limit, boolean markRead) {
    if (!userRepository.isNotificationEnabled(targetUserId)) {
      return List.of();
    }

    List<NotificationModel> list =
        notificationRepository.findLatestByTargetUser(targetUserId, limit);

    if (markRead && !list.isEmpty()) {
      notificationRepository.markAllAsRead(
          targetUserId, LocalDateTime.now(), targetUserId, ProgramType.ONL_NTF_QRY.getCode());
    }

    return list;
  }

  @Transactional(readOnly = true)
  public long getUnreadCount(Long userId) {
    if (!userRepository.isNotificationEnabled(userId)) {
      return 0;
    }
    return notificationRepository.countUnreadByTargetUser(userId);
  }
}
