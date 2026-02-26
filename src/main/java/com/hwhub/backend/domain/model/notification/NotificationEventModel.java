package com.hwhub.backend.domain.model.notification;

import com.hwhub.backend.domain.enums.EventStatus;
import com.hwhub.backend.domain.enums.NotificationType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class NotificationEventModel {

  private final NotificationEventId notificationEventId;
  private final Long householdId;
  private final NotificationType notificationType;
  private final Long actorUserId;
  private final Long targetUserId;
  private final Long entityId;
  private final LocalDate aggregationDate;
  private final LocalDateTime occurredAt;
  private final EventStatus eventStatus;
  private final String processingKey;
  private final LocalDate processingStartedAt;
  private final LocalDate processedAt;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param notificationEventId 通知イベントID
   * @param householdId 世帯ID
   * @param notificationType 通知種別
   * @param actorUserId 行為者ユーザーID
   * @param targetUserId 対象ユーザーID
   * @param entityId エンティティID
   * @param aggregationDate 集計日
   * @param occurredAt 発生日時
   * @param eventStatus イベントステータス
   * @param processingKey 処理キー
   * @param processingStartedAt 処理開始日時
   * @param processedAt 処理完了日時
   */
  private NotificationEventModel(
      NotificationEventId notificationEventId,
      Long householdId,
      NotificationType notificationType,
      Long actorUserId,
      long targetUserId,
      Long entityId,
      LocalDate aggregationDate,
      LocalDateTime occurredAt,
      EventStatus eventStatus,
      String processingKey,
      LocalDate processingStartedAt,
      LocalDate processedAt) {
    this.notificationEventId = notificationEventId;
    this.householdId = householdId;
    this.notificationType = notificationType;
    this.actorUserId = actorUserId;
    this.targetUserId = targetUserId;
    this.entityId = entityId;
    this.aggregationDate = aggregationDate;
    this.occurredAt = occurredAt;
    this.eventStatus = eventStatus;
    this.processingKey = processingKey;
    this.processingStartedAt = processingStartedAt;
    this.processedAt = processedAt;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param notificationEventId 通知イベントID
   * @param householdId 世帯ID
   * @param notificationType 通知種別
   * @param actorUserId 行為者ユーザーID
   * @param targetUserId 対象ユーザーID
   * @param entityId エンティティID
   * @param aggregationDate 集計日
   * @param occurredAt 発生日時
   * @param eventStatus イベントステータス
   * @param processingKey 処理キー
   * @param processingStartedAt 処理開始日時
   * @param processedAt 処理完了日時
   * @return インスタンスを返す。
   */
  public static NotificationEventModel reconstruct(
      Long notificationEventId,
      Long householdId,
      String notificationType,
      Long actorUserId,
      Long targetUserId,
      Long entityId,
      LocalDate aggregationDate,
      LocalDateTime occurredAt,
      String eventStatus,
      String processingKey,
      LocalDate processingStartedAt,
      LocalDate processedAt) {
    return new NotificationEventModel(
        new NotificationEventId(notificationEventId),
        householdId,
        NotificationType.fromCode(notificationType),
        actorUserId,
        targetUserId,
        entityId,
        aggregationDate,
        occurredAt,
        EventStatus.fromCode(eventStatus),
        processingKey,
        processingStartedAt,
        processedAt);
  }

  /**
   * 新規に未処理の通知イベントを作成する。
   *
   * @param householdId 世帯ID
   * @param notificationType 通知種別
   * @param actorUserId 行為者ユーザーID
   * @param targetUserId 対象ユーザーID
   * @param entityId エンティティID
   * @param aggregationDate 集計日
   * @param occurredAt 発生日時
   * @return インスタンスを返す。
   */
  public static NotificationEventModel newUnprocessed(
      Long householdId,
      NotificationType notificationType,
      Long actorUserId,
      Long targetUserId,
      Long entityId,
      LocalDate aggregationDate,
      LocalDateTime occurredAt) {
    return new NotificationEventModel(
        null,
        householdId,
        notificationType,
        actorUserId,
        targetUserId,
        entityId,
        aggregationDate,
        occurredAt,
        EventStatus.PENDING,
        null,
        null,
        null);
  }
}
