package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.notification.NotificationId;
import com.hwhub.backend.domain.model.notification.NotificationModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository {

  /** 通知を新規登録 */
  NotificationId insert(NotificationModel model, String program);

  /** ID指定で取得 */
  Optional<NotificationModel> findById(NotificationId notificationId);

  /**
   * ユーザーの最新通知一覧を取得
   *
   * @param targetUserId 対象ユーザー
   * @param limit 件数
   */
  List<NotificationModel> findLatestByTargetUser(Long targetUserId, int limit);

  /** ユーザーの未読通知一覧 */
  List<NotificationModel> findUnreadByTargetUser(Long targetUserId, int limit);

  /** 一覧表示時の既読化（指定ユーザーの未読をまとめて既読） */
  void markAllAsRead(Long targetUserId, LocalDateTime readAt, Long updateUserId, String program);

  /** occurredAt以前を既読化（ページング既読化用・将来拡張） */
  void markAsReadUpTo(
      Long targetUserId,
      LocalDateTime occurredAt,
      LocalDateTime readAt,
      Long updateUserId,
      String program);

  /** 未読件数 */
  long countUnreadByTargetUser(Long targetUserId);

  /** 一括登録 */
  int bulkInsert(List<NotificationModel> list, String program);
}
