package com.hwhub.backend.domain.model;

import java.time.LocalDateTime;
import lombok.Getter;

/** アナウンスバナーのドメインモデル。 */
@Getter
public class AnnouncementModel {

  private Long id;
  private String titleJa;
  private String titleEn;
  private String titleEs;
  private String bodyJa;
  private String bodyEn;
  private String bodyEs;
  private String severity;
  private String targetScope;
  private LocalDateTime startAt;
  private LocalDateTime endAt;

  private AnnouncementModel(
      Long id,
      String titleJa,
      String titleEn,
      String titleEs,
      String bodyJa,
      String bodyEn,
      String bodyEs,
      String severity,
      String targetScope,
      LocalDateTime startAt,
      LocalDateTime endAt) {
    this.id = id;
    this.titleJa = titleJa;
    this.titleEn = titleEn;
    this.titleEs = titleEs;
    this.bodyJa = bodyJa;
    this.bodyEn = bodyEn;
    this.bodyEs = bodyEs;
    this.severity = severity;
    this.targetScope = targetScope;
    this.startAt = startAt;
    this.endAt = endAt;
  }

  /**
   * 再構築ファクトリメソッド。infrastructure層からのみ呼び出されることを想定。
   *
   * @param id アナウンスID
   * @param titleJa タイトル（日本語）
   * @param titleEn タイトル（英語）
   * @param titleEs タイトル（スペイン語）
   * @param bodyJa 本文（日本語）
   * @param bodyEn 本文（英語）
   * @param bodyEs 本文（スペイン語）
   * @param severity 重要度（m_code 0028 の code_value）
   * @param targetScope 対象スコープ（m_code 0027 の code_value）
   * @param startAt 有効開始日時
   * @param endAt 有効終了日時
   * @return AnnouncementModel インスタンス
   */
  public static AnnouncementModel reconstruct(
      Long id,
      String titleJa,
      String titleEn,
      String titleEs,
      String bodyJa,
      String bodyEn,
      String bodyEs,
      String severity,
      String targetScope,
      LocalDateTime startAt,
      LocalDateTime endAt) {
    return new AnnouncementModel(
        id,
        titleJa,
        titleEn,
        titleEs,
        bodyJa,
        bodyEn,
        bodyEs,
        severity,
        targetScope,
        startAt,
        endAt);
  }
}
