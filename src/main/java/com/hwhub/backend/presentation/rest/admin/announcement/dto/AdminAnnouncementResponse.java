package com.hwhub.backend.presentation.rest.admin.announcement.dto;

import com.hwhub.backend.application.service.announcement.AnnouncementSummary;
import java.time.LocalDateTime;

/** アナウンスマスタメンテのレスポンスDTO。 */
public record AdminAnnouncementResponse(
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

  /** AnnouncementSummary からレスポンスDTOを生成する。 */
  public static AdminAnnouncementResponse from(AnnouncementSummary summary) {
    return new AdminAnnouncementResponse(
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
