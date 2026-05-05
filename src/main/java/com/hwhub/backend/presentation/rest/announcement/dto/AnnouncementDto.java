package com.hwhub.backend.presentation.rest.announcement.dto;

import com.hwhub.backend.application.service.AnnouncementService.AnnouncementSummary;
import java.time.LocalDateTime;

/** アナウンスバナーのレスポンスDTO。 */
public record AnnouncementDto(
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

  /**
   * AnnouncementSummary からDTOを生成する。
   *
   * @param summary サービス層のサマリーオブジェクト
   * @return AnnouncementDto
   */
  public static AnnouncementDto from(AnnouncementSummary summary) {
    return new AnnouncementDto(
        summary.id(),
        summary.titleJa(),
        summary.titleEn(),
        summary.titleEs(),
        summary.bodyJa(),
        summary.bodyEn(),
        summary.bodyEs(),
        summary.severity(),
        summary.targetScope(),
        summary.startAt(),
        summary.endAt());
  }
}
