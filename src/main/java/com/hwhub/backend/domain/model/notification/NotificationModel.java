package com.hwhub.backend.domain.model.notification;

import com.hwhub.backend.domain.enums.NotificationLinkType;
import com.hwhub.backend.domain.enums.NotificationType;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;

@Getter
public class NotificationModel {
  private final NotificationId notificationId;
  private final Long householdId;
  private final NotificationType notificationType;
  private final Long actorUserId;
  private final long targetUserId;
  private boolean isRead;
  private LocalDateTime readAt;
  private final NotificationMessage message;
  private final NotificationLink link;
  private final LocalDateTime occurredAt;
  private final String aggregatedKey;
  private final int aggregatedCount;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param notificationId
   * @param householdId
   * @param notificationType
   * @param actorUserId
   * @param targetUserId
   * @param isRead
   * @param readAt
   * @param message
   * @param link
   * @param occurredAt
   * @param aggregatedKey
   * @param aggregatedCount
   */
  private NotificationModel(
      NotificationId notificationId,
      Long householdId,
      NotificationType notificationType,
      Long actorUserId,
      long targetUserId,
      boolean isRead,
      LocalDateTime readAt,
      NotificationMessage message,
      NotificationLink link,
      LocalDateTime occurredAt,
      String aggregatedKey,
      int aggregatedCount) {
    this.notificationId = notificationId;
    this.householdId = householdId;
    this.notificationType = Objects.requireNonNull(notificationType);
    this.actorUserId = actorUserId;
    if (targetUserId <= 0) throw new IllegalArgumentException("targetUserId must be positive");
    this.targetUserId = targetUserId;
    this.isRead = isRead;
    this.readAt = readAt;
    this.message = Objects.requireNonNull(message);
    this.link = Objects.requireNonNull(link);
    this.occurredAt = Objects.requireNonNull(occurredAt);
    this.aggregatedKey = aggregatedKey;
    if (aggregatedCount <= 0) throw new IllegalArgumentException("aggregatedCount must be >= 1");
    this.aggregatedCount = aggregatedCount;
    // 整合性チェック
    if (this.isRead && this.readAt == null) {
      throw new IllegalArgumentException("readAt is required when isRead = true");
    }
    if (!this.isRead && this.readAt != null) {
      throw new IllegalArgumentException("readAt must be null when isRead = false");
    }
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param notificationId 通知ID
   * @param householdId 世帯ID
   * @param notificationType 通知種別コード
   * @param actorUserId アクターユーザーID
   * @param targetUserId ターゲットユーザーID
   * @param isRead 既読フラグ
   * @param readAt 既読日時
   * @param titleKey タイトルキー
   * @param bodyKey ボディキー
   * @param paramsJson パラメータJSON
   * @param linkType 遷移先種別コード
   * @param linkId 遷移先ID
   * @param occurredAt 通知発生日時
   * @param aggregatedKey 集約キー
   * @param aggregatedCount 集約数
   * @return インスタンスを返す。
   */
  public static NotificationModel reconstruct(
      Long notificationId,
      Long householdId,
      String notificationType,
      Long actorUserId,
      long targetUserId,
      boolean isRead,
      LocalDateTime readAt,
      String titleKey,
      String bodyKey,
      Map<String, Object> params,
      String linkType,
      Long linkId,
      LocalDateTime occurredAt,
      String aggregatedKey,
      int aggregatedCount) {
    return new NotificationModel(
        new NotificationId(notificationId),
        householdId,
        NotificationType.fromCode(notificationType),
        actorUserId,
        targetUserId,
        isRead,
        readAt,
        new NotificationMessage(titleKey, bodyKey, params),
        new NotificationLink(NotificationLinkType.fromCode(linkType), linkId),
        occurredAt,
        aggregatedKey,
        aggregatedCount);
  }

  /**
   * 新規通知（未読）
   *
   * @param householdId 世帯ID
   * @param type 通知種別
   * @param actorUserId アクターユーザーID
   * @param targetUserId ターゲットユーザーID
   * @param message 通知メッセージ
   * @param link 遷移先
   * @param occurredAt 通知発生日時
   * @return
   */
  public static NotificationModel newUnread(
      Long householdId,
      NotificationType type,
      Long actorUserId,
      long targetUserId,
      NotificationMessage message,
      NotificationLink link,
      LocalDateTime occurredAt) {
    return new NotificationModel(
        null,
        householdId,
        type,
        actorUserId,
        targetUserId,
        false,
        null,
        message,
        link,
        occurredAt,
        null,
        1);
  }

  /**
   * 既読化（一覧開いたら既読運用のため）
   *
   * @param readAt 既読日時
   */
  public void markRead(LocalDateTime readAt) {
    Objects.requireNonNull(readAt);
    this.isRead = true;
    this.readAt = readAt;
  }
}
