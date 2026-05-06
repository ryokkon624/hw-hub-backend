package com.hwhub.backend.application.service.announcement;

import com.hwhub.backend.domain.model.AnnouncementModel;
import java.time.LocalDateTime;

/**
 * アナウンスのサマリーDTO（プレゼンテーション層への戻り値）。
 *
 * @param id アナウンスID
 * @param titleJa タイトル（日本語）
 * @param titleEn タイトル（英語）
 * @param titleEs タイトル（スペイン語）
 * @param bodyJa 本文（日本語）
 * @param bodyEn 本文（英語）
 * @param bodyEs 本文（スペイン語）
 * @param severity 重要度
 * @param targetScope 対象スコープ
 * @param startAt 有効開始日時
 * @param endAt 有効終了日時
 */
public record AnnouncementSummary(
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

  /** AnnouncementModel からサマリーを生成する。 */
  public static AnnouncementSummary from(AnnouncementModel model) {
    return new AnnouncementSummary(
        model.getId(),
        model.getTitleJa(),
        model.getTitleEn(),
        model.getTitleEs(),
        model.getBodyJa(),
        model.getBodyEn(),
        model.getBodyEs(),
        model.getSeverity(),
        model.getTargetScope(),
        model.getStartAt(),
        model.getEndAt());
  }
}
