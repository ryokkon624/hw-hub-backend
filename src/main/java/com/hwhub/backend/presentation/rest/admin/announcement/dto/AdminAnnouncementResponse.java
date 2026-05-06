package com.hwhub.backend.presentation.rest.admin.announcement.dto;

import com.hwhub.backend.domain.model.AnnouncementModel;
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

  /** AnnouncementModel からレスポンスDTOを生成する。 */
  public static AdminAnnouncementResponse from(AnnouncementModel model) {
    return new AdminAnnouncementResponse(
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
