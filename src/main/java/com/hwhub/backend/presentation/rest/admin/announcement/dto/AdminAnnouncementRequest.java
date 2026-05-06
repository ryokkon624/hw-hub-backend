package com.hwhub.backend.presentation.rest.admin.announcement.dto;

import com.hwhub.backend.domain.model.AnnouncementModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/** アナウンスマスタメンテの登録・更新リクエストDTO。 */
public record AdminAnnouncementRequest(
    @NotBlank String titleJa,
    @NotBlank String titleEn,
    @NotBlank String titleEs,
    @NotBlank String bodyJa,
    @NotBlank String bodyEn,
    @NotBlank String bodyEs,
    @NotBlank String severity,
    @NotBlank String targetScope,
    @NotNull LocalDateTime startAt,
    @NotNull LocalDateTime endAt) {

  /** 新規作成用モデルに変換する。 */
  public AnnouncementModel toModel() {
    return AnnouncementModel.create(
        titleJa, titleEn, titleEs, bodyJa, bodyEn, bodyEs, severity, targetScope, startAt, endAt);
  }

  /** 更新用モデルに変換する（id付き）。 */
  public AnnouncementModel toModel(Long id) {
    return AnnouncementModel.reconstruct(
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
