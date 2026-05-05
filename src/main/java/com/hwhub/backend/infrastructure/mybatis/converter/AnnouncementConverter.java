package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.AnnouncementModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MAnnouncement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/** {@link MAnnouncement} エンティティを {@link AnnouncementModel} に変換するコンバーター。 */
public final class AnnouncementConverter {

  private AnnouncementConverter() {}

  /**
   * エンティティをドメインモデルに変換する。
   *
   * @param entity MyBatis Generator 生成エンティティ
   * @return AnnouncementModel。entity が null の場合は null を返す。
   */
  public static AnnouncementModel toModel(MAnnouncement entity) {
    if (entity == null) {
      return null;
    }

    return AnnouncementModel.reconstruct(
        entity.getAnnouncementId(),
        entity.getTitleJa(),
        entity.getTitleEn(),
        entity.getTitleEs(),
        entity.getBodyJa(),
        entity.getBodyEn(),
        entity.getBodyEs(),
        entity.getSeverity(),
        entity.getTargetScope(),
        toLocalDateTime(entity.getStartAt()),
        toLocalDateTime(entity.getEndAt()));
  }

  private static LocalDateTime toLocalDateTime(Date date) {
    if (date == null) {
      return null;
    }
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
