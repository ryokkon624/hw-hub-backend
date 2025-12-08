package com.hwhub.backend.domain.model;

import com.hwhub.backend.domain.enums.TaskStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

/** 家事タスクModel。オンラインでは新規追加することはあり得ないため新規追加用のファクトリメソッドはは持たない。 */
@Getter
public class HouseworkTaskModel {

  private final Long houseworkTaskId;
  private final Long householdId;
  private final Long houseworkId;
  private final String name;
  private final String description;
  private final String category;
  private final LocalDate targetDate;
  private Long assigneeUserId;
  private String status;
  private String assignReasonType;
  private LocalDate doneAt;
  private String skippedReason;
  private List<HouseworkTaskAssignmentHistModel> histories;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param houseworkTaskId 家事タスクID
   * @param householdId 世帯ID
   * @param houseworkId 家事ID
   * @param name 家事名
   * @param description 説明
   * @param category カテゴリ
   * @param targetDate 実施対象日
   * @param assigneeUserId 担当者ユーザID
   * @param status タスクステータス
   * @param assignReasonType 割当理由区分
   * @param doneAt 完了日
   * @param skippedReason スキップ理由
   * @param histories 担当変更履歴
   */
  protected HouseworkTaskModel(
      Long houseworkTaskId,
      Long householdId,
      Long houseworkId,
      String name,
      String description,
      String category,
      LocalDate targetDate,
      Long assigneeUserId,
      String status,
      String assignReasonType,
      LocalDate doneAt,
      String skippedReason,
      List<HouseworkTaskAssignmentHistModel> histories) {
    this.houseworkTaskId = houseworkTaskId;
    this.householdId = householdId;
    this.houseworkId = houseworkId;
    this.name = name;
    this.description = description;
    this.category = category;
    this.targetDate = targetDate;
    this.assigneeUserId = assigneeUserId;
    this.status = status;
    this.assignReasonType = assignReasonType;
    this.doneAt = doneAt;
    this.skippedReason = skippedReason;
    this.histories = histories;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param houseworkTaskId 家事タスクID
   * @param householdId 世帯ID
   * @param houseworkId 家事ID
   * @param name 家事名
   * @param description 説明
   * @param category カテゴリ
   * @param targetDate 実施対象日
   * @param assigneeUserId 担当者ユーザID
   * @param status タスクステータス
   * @param assignReasonType 割当理由区分
   * @param doneAt 完了日
   * @param skippedReason スキップ理由
   * @param histories 担当変更履歴
   * @return インスタンスを返す。
   */
  public static HouseworkTaskModel reconstruct(
      Long houseworkTaskId,
      Long householdId,
      Long houseworkId,
      String name,
      String description,
      String category,
      LocalDate targetDate,
      Long assigneeUserId,
      String status,
      String assignReasonType,
      LocalDate doneAt,
      String skippedReason,
      List<HouseworkTaskAssignmentHistModel> histories) {
    return new HouseworkTaskModel(
        houseworkTaskId,
        householdId,
        houseworkId,
        name,
        description,
        category,
        targetDate,
        assigneeUserId,
        status,
        assignReasonType,
        doneAt,
        skippedReason,
        histories);
  }

  /**
   * 担当者を変更する。
   *
   * @param assigneeUserId 担当者ユーザID
   * @param assignReasonType 割当理由区分
   * @param operator 操作を行ったユーザのユーザID
   */
  public void changeAssignee(Long assigneeUserId, String assignReasonType, Long operator) {
    // 担当が変わっていた場合履歴を追加
    if (!Objects.equals(assigneeUserId, this.assigneeUserId)) {
      HouseworkTaskAssignmentHistModel hist =
          HouseworkTaskAssignmentHistModel.create(
              this.houseworkTaskId,
              this.householdId,
              this.assigneeUserId,
              assigneeUserId,
              operator,
              assignReasonType,
              null,
              LocalDateTime.now());
      this.histories.add(hist);
    }
    this.assigneeUserId = assigneeUserId;
    this.assignReasonType = assignReasonType;
  }

  /** タスクを完了にする。 */
  public void complete() {
    this.status = TaskStatus.DONE.getCode();
    this.doneAt = LocalDate.now();
  }

  /**
   * タスクをスキップする。
   *
   * @param reason スキップ理由
   */
  public void skip(String reason) {
    this.status = TaskStatus.SKIPPED.getCode();
    this.skippedReason = reason;
    this.doneAt = LocalDate.now();
  }
}
