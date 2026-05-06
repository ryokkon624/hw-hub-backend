package com.hwhub.backend.presentation.rest.announcement.dto;

import com.hwhub.backend.domain.model.AnnouncementModel;
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
   * AnnouncementModel からDTOを生成する。
   *
   * @param model ドメインモデル
   * @return AnnouncementDto
   */
  public static AnnouncementDto from(AnnouncementModel model) {
    return new AnnouncementDto(
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
