package com.hwhub.backend.domain.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class HouseworkTaskAssignmentHistModel {

  private final Long houseworkTaskAssignmentHistoryId;
  private final Long houseworkTaskId;
  private final Long householdId;
  private final Long fromAssigneeUserId;
  private final Long toAssigneeUserId;
  private final Long operatedUserId;
  private final String assignReasonType;
  private final String note;
  private final LocalDateTime changedAt;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param houseworkTaskAssignmentHistoryId 家事タスク担当変更履歴ID
   * @param houseworkTaskId 家事タスクID
   * @param householdId 世帯ID
   * @param fromAssigneeUserId 変更前担当者ユーザID
   * @param toAssigneeUserId 変更後担当者ユーザID
   * @param operatedUserId 操作と行ったユーザID
   * @param assignReasonType 変更理由種別
   * @param note 備考・メモ
   * @param changedAt 変更日時
   */
  private HouseworkTaskAssignmentHistModel(
      Long houseworkTaskAssignmentHistoryId,
      Long houseworkTaskId,
      Long householdId,
      Long fromAssigneeUserId,
      Long toAssigneeUserId,
      Long operatedUserId,
      String assignReasonType,
      String note,
      LocalDateTime changedAt) {
    this.houseworkTaskAssignmentHistoryId = houseworkTaskAssignmentHistoryId;
    this.houseworkTaskId = houseworkTaskId;
    this.householdId = householdId;
    this.fromAssigneeUserId = fromAssigneeUserId;
    this.toAssigneeUserId = toAssigneeUserId;
    this.operatedUserId = operatedUserId;
    this.assignReasonType = assignReasonType;
    this.note = note;
    this.changedAt = changedAt;
  }

  /**
   * 新規追加時のファクトリメソッド。
   *
   * @param houseworkTaskId 家事タスクID
   * @param householdId 世帯ID
   * @param fromAssigneeUserId 変更前担当者ユーザID
   * @param toAssigneeUserId 変更後担当者ユーザID
   * @param operatedUserId 操作と行ったユーザID
   * @param assignReasonType 変更理由種別
   * @param note 備考・メモ
   * @param changedAt 変更日時
   * @return 家事タスク担当変更履歴IDがnullのインスタンスを返す。
   */
  public static HouseworkTaskAssignmentHistModel create(
      Long houseworkTaskId,
      Long householdId,
      Long fromAssigneeUserId,
      Long toAssigneeUserId,
      Long operatedUserId,
      String assignReasonType,
      String note,
      LocalDateTime changedAt) {
    return new HouseworkTaskAssignmentHistModel(
        null,
        houseworkTaskId,
        householdId,
        fromAssigneeUserId,
        toAssigneeUserId,
        operatedUserId,
        assignReasonType,
        note,
        changedAt);
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param houseworkTaskAssignmentHistoryId 家事タスク担当変更履歴ID
   * @param houseworkTaskId 家事タスクID
   * @param householdId 世帯ID
   * @param fromAssigneeUserId 変更前担当者ユーザID
   * @param toAssigneeUserId 変更後担当者ユーザID
   * @param operatedUserId 操作と行ったユーザID
   * @param assignReasonType 変更理由種別
   * @param note 備考・メモ
   * @param changedAt 変更日時
   * @return インスタンスを返す。
   */
  public static HouseworkTaskAssignmentHistModel reconstruct(
      Long houseworkTaskAssignmentHistoryId,
      Long houseworkTaskId,
      Long householdId,
      Long fromAssigneeUserId,
      Long toAssigneeUserId,
      Long operatedUserId,
      String assignReasonType,
      String note,
      LocalDateTime changedAt) {
    return new HouseworkTaskAssignmentHistModel(
        houseworkTaskAssignmentHistoryId,
        houseworkTaskId,
        householdId,
        fromAssigneeUserId,
        toAssigneeUserId,
        operatedUserId,
        assignReasonType,
        note,
        changedAt);
  }
}
