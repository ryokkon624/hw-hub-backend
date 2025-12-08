package com.hwhub.backend.domain.model;

import com.hwhub.backend.domain.enums.RecurrenceType;
import java.time.LocalDate;
import lombok.Getter;

/** アプリケーションが扱う「家事」のドメインモデル。 DBの m_housework にほぼ対応しつつ、 一部は意味のある型（RecurrenceType など）で表現する。 */
@Getter
public class HouseworkModel {

  /** 家事ID */
  private Long houseworkId;

  /** 世帯ID */
  private Long householdId;

  /** 家事名 */
  private String name;

  /** 説明・メモ */
  private String description;

  /** カテゴリのコード値（m_code の code_value） */
  private String category;

  /** 周期タイプ（毎週／毎月など） */
  private String recurrenceType;

  /** 毎週の実行曜日ビットマスク（weekly のときのみ利用） 1ビットずつ 月=1, 火=2, … のように持つ想定。 例: 月・水・金なら 1+4+16 = 21 */
  private Integer weeklyDays;

  /** 毎月何日か（毎月・日付指定のときのみ） */
  private Integer dayOfMonth;

  /** 毎月第何週か（1〜4） */
  private Integer nthWeek;

  /** 対象曜日（1:月〜7:日 などのコード） */
  private Integer weekday;

  /** 有効開始日 */
  private LocalDate startDate;

  /** 有効終了日 */
  private LocalDate endDate;

  /** デフォルト担当者ユーザID（null可） */
  private Long defaultAssigneeUserId;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param houseworkId 家事ID
   * @param householdId 世帯ID
   * @param name 家事名
   * @param description 説明
   * @param category カテゴリ
   * @param recurrenceType 周期タイプ
   * @param weeklyDays 週次実行曜日ビットマスク
   * @param dayOfMonth 月次実行日
   * @param nthWeek 第n週
   * @param weekday 対象曜日
   * @param startDate 有効開始日
   * @param endDate 有効終了日
   * @param defaultAssigneeUserId デフォルト担当者ユーザID
   */
  private HouseworkModel(
      Long houseworkId,
      Long householdId,
      String name,
      String description,
      String category,
      String recurrenceType,
      Integer weeklyDays,
      Integer dayOfMonth,
      Integer nthWeek,
      Integer weekday,
      LocalDate startDate,
      LocalDate endDate,
      Long defaultAssigneeUserId) {
    this.houseworkId = houseworkId;
    this.householdId = householdId;
    this.name = name;
    this.description = description;
    this.category = category;
    this.recurrenceType = recurrenceType;
    this.weeklyDays = weeklyDays;
    this.dayOfMonth = dayOfMonth;
    this.nthWeek = nthWeek;
    this.weekday = weekday;
    this.startDate = startDate;
    this.endDate = endDate;
    this.defaultAssigneeUserId = defaultAssigneeUserId;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param houseworkId 家事ID
   * @param householdId 世帯ID
   * @param name 家事名
   * @param description 説明
   * @param category カテゴリ
   * @param recurrenceType 周期タイプ
   * @param weeklyDays 週次実行曜日ビットマスク
   * @param dayOfMonth 月次実行日
   * @param nthWeek 第n週
   * @param weekday 対象曜日
   * @param startDate 有効開始日
   * @param endDate 有効終了日
   * @param defaultAssigneeUserId デフォルト担当者ユーザID
   * @return インスタンスを返す。
   */
  public static HouseworkModel reconstruct(
      Long houseworkId,
      Long householdId,
      String name,
      String description,
      String category,
      String recurrenceType,
      Integer weeklyDays,
      Integer dayOfMonth,
      Integer nthWeek,
      Integer weekday,
      LocalDate startDate,
      LocalDate endDate,
      Long defaultAssigneeUserId) {
    return new HouseworkModel(
        houseworkId,
        householdId,
        name,
        description,
        category,
        recurrenceType,
        weeklyDays,
        dayOfMonth,
        nthWeek,
        weekday,
        startDate,
        endDate,
        defaultAssigneeUserId);
  }

  public static HouseworkModel create(
      Long householdId,
      String name,
      String description,
      String category,
      String recurrenceType,
      Integer weeklyDays,
      Integer dayOfMonth,
      Integer nthWeek,
      Integer weekday,
      LocalDate startDate,
      LocalDate endDate,
      Long defaultAssigneeUserId) {
    return new HouseworkModel(
        null,
        householdId,
        name,
        description,
        category,
        recurrenceType,
        weeklyDays,
        dayOfMonth,
        nthWeek,
        weekday,
        startDate,
        endDate,
        defaultAssigneeUserId);
  }

  public void setBasicInfo(String name, String description, String category) {
    this.name = name;
    this.description = description;
    this.category = category;
  }

  public void setRecurrenceWeekly(Integer weeklyDays) {
    this.recurrenceType = RecurrenceType.WEEKLY.getCode();
    this.weeklyDays = weeklyDays;
    this.dayOfMonth = null;
    this.nthWeek = null;
    this.weekday = null;
  }

  public void setRecurrenceMonthly(Integer dayOfMonth) {
    this.recurrenceType = RecurrenceType.MONTHLY.getCode();
    this.weeklyDays = null;
    this.dayOfMonth = dayOfMonth;
    this.nthWeek = null;
    this.weekday = null;
  }

  public void setRecurrenceNthweekday(Integer nthWeek, Integer weekday) {
    this.recurrenceType = RecurrenceType.NTH_WEEKDAY.getCode();
    this.weeklyDays = null;
    this.dayOfMonth = null;
    this.nthWeek = nthWeek;
    this.weekday = weekday;
  }

  public void setEffectivePriod(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public void setDefaultAssigneeUserId(Long defaultAssigneeUserId) {
    this.defaultAssigneeUserId = defaultAssigneeUserId;
  }
}
