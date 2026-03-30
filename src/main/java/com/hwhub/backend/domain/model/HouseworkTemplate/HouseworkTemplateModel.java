package com.hwhub.backend.domain.model.HouseworkTemplate;

import com.hwhub.backend.domain.enums.Category;
import com.hwhub.backend.domain.enums.NthWeek;
import com.hwhub.backend.domain.enums.RecurrenceType;
import com.hwhub.backend.domain.enums.Weekday;
import java.util.Objects;
import lombok.Getter;

@Getter
public class HouseworkTemplateModel {
  private final HouseworkTemplateId houseworkTemplateId;
  private final String nameJa;
  private final String nameEn;
  private final String nameEs;
  private final String descriptionJa;
  private final String descriptionEn;
  private final String descriptionEs;
  private final String recommendationJa;
  private final String recommendationEn;
  private final String recommendationEs;
  private final Category category;
  private final RecurrenceType recurrenceType;
  private final Integer weeklyDays;
  private final Integer dayOfMonth;
  private final NthWeek nthWeek;
  private final Weekday weekday;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param houseworkTemplateId 家事テンプレートID
   * @param nameJa 日本語名
   * @param nameEn 英語名
   * @param nameEs スペイン語名
   * @param descriptionJa 日本語説明
   * @param descriptionEn 英語説明
   * @param descriptionEs スペイン語説明
   * @param recommendationJa おすすめ日本語
   * @param recommendationEn おすすめ英語
   * @param recommendationEs おすすめスペイン語
   * @param category カテゴリ
   * @param recurrenceType 周期タイプ
   * @param weeklyDays 週次実行曜日ビットマスク
   * @param dayOfMonth 月次実行日
   * @param nthWeek 第n週
   * @param weekday 対象曜日
   */
  private HouseworkTemplateModel(
      HouseworkTemplateId houseworkTemplateId,
      String nameJa,
      String nameEn,
      String nameEs,
      String descriptionJa,
      String descriptionEn,
      String descriptionEs,
      String recommendationJa,
      String recommendationEn,
      String recommendationEs,
      Category category,
      RecurrenceType recurrenceType,
      Integer weeklyDays,
      Integer dayOfMonth,
      NthWeek nthWeek,
      Weekday weekday) {
    this.houseworkTemplateId = houseworkTemplateId;
    this.nameJa = Objects.requireNonNull(nameJa);
    this.nameEn = Objects.requireNonNull(nameEn);
    this.nameEs = Objects.requireNonNull(nameEs);
    this.descriptionJa = descriptionJa;
    this.descriptionEn = descriptionEn;
    this.descriptionEs = descriptionEs;
    this.recommendationJa = recommendationJa;
    this.recommendationEn = recommendationEn;
    this.recommendationEs = recommendationEs;
    this.category = Objects.requireNonNull(category);
    this.recurrenceType = Objects.requireNonNull(recurrenceType);
    this.weeklyDays = weeklyDays;
    this.dayOfMonth = dayOfMonth;
    this.nthWeek = nthWeek;
    this.weekday = weekday;
    // 整合性チェック
    if (this.descriptionJa != null || this.descriptionEn != null || this.descriptionEs != null) {
      if (this.descriptionJa == null || this.descriptionEn == null || this.descriptionEs == null) {
        throw new IllegalArgumentException(
            "descriptionJa, descriptionEn, descriptionEs are required when description is not null");
      }
    }
    if (this.recommendationJa != null
        || this.recommendationEn != null
        || this.recommendationEs != null) {
      if (this.recommendationJa == null
          || this.recommendationEn == null
          || this.recommendationEs == null) {
        throw new IllegalArgumentException(
            "recommendationJa, recommendationEn, recommendationEs are required when recommendation is not null");
      }
    }
    if (this.recurrenceType == RecurrenceType.WEEKLY) {
      if (this.weeklyDays == null) {
        throw new IllegalArgumentException("weeklyDays is required when recurrenceType is WEEKLY");
      }
    }
    if (this.recurrenceType == RecurrenceType.MONTHLY) {
      if (this.dayOfMonth == null) {
        throw new IllegalArgumentException("dayOfMonth is required when recurrenceType is MONTHLY");
      }
    }
    if (this.recurrenceType == RecurrenceType.NTH_WEEKDAY) {
      if (this.nthWeek == null) {
        throw new IllegalArgumentException(
            "nthWeek is required when recurrenceType is NTH_WEEKDAY");
      }
      if (this.weekday == null) {
        throw new IllegalArgumentException(
            "weekday is required when recurrenceType is NTH_WEEKDAY");
      }
    }
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param houseworkTemplateId 家事テンプレートID
   * @param nameJa 日本語名
   * @param nameEn 英語名
   * @param nameEs スペイン語名
   * @param descriptionJa 日本語説明
   * @param descriptionEn 英語説明
   * @param descriptionEs スペイン語説明
   * @param recommendationJa おすすめ日本語
   * @param recommendationEn おすすめ英語
   * @param recommendationEs おすすめスペイン語
   * @param category カテゴリ
   * @param recurrenceType 周期タイプ
   * @param weeklyDays 週次実行曜日ビットマスク
   * @param dayOfMonth 月次実行日
   * @param nthWeek 第n週
   * @param weekday 対象曜日
   * @return インスタンスを返す。
   */
  public static HouseworkTemplateModel reconstruct(
      HouseworkTemplateId houseworkTemplateId,
      String nameJa,
      String nameEn,
      String nameEs,
      String descriptionJa,
      String descriptionEn,
      String descriptionEs,
      String recommendationJa,
      String recommendationEn,
      String recommendationEs,
      Category category,
      RecurrenceType recurrenceType,
      Integer weeklyDays,
      Integer dayOfMonth,
      NthWeek nthWeek,
      Weekday weekday) {
    return new HouseworkTemplateModel(
        houseworkTemplateId,
        nameJa,
        nameEn,
        nameEs,
        descriptionJa,
        descriptionEn,
        descriptionEs,
        recommendationJa,
        recommendationEn,
        recommendationEs,
        category,
        recurrenceType,
        weeklyDays,
        dayOfMonth,
        nthWeek,
        weekday);
  }

  /**
   * 新規追加時のファクトリメソッド。
   *
   * @param nameJa 日本語名
   * @param nameEn 英語名
   * @param nameEs スペイン語名
   * @param descriptionJa 日本語説明
   * @param descriptionEn 英語説明
   * @param descriptionEs スペイン語説明
   * @param recommendationJa おすすめ日本語
   * @param recommendationEn おすすめ英語
   * @param recommendationEs おすすめスペイン語
   * @param category カテゴリ
   * @param recurrenceType 周期タイプ
   * @param weeklyDays 週次実行曜日ビットマスク
   * @param dayOfMonth 月次実行日
   * @param nthWeek 第n週
   * @param weekday 対象曜日
   * @return インスタンスを返す。
   */
  public static HouseworkTemplateModel create(
      String nameJa,
      String nameEn,
      String nameEs,
      String descriptionJa,
      String descriptionEn,
      String descriptionEs,
      String recommendationJa,
      String recommendationEn,
      String recommendationEs,
      Category category,
      RecurrenceType recurrenceType,
      Integer weeklyDays,
      Integer dayOfMonth,
      NthWeek nthWeek,
      Weekday weekday) {
    return new HouseworkTemplateModel(
        null,
        nameJa,
        nameEn,
        nameEs,
        descriptionJa,
        descriptionEn,
        descriptionEs,
        recommendationJa,
        recommendationEn,
        recommendationEs,
        category,
        recurrenceType,
        weeklyDays,
        dayOfMonth,
        nthWeek,
        weekday);
  }
}
