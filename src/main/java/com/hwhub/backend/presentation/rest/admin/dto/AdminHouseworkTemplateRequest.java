package com.hwhub.backend.presentation.rest.admin.dto;

import com.hwhub.backend.domain.enums.Category;
import com.hwhub.backend.domain.enums.NthWeek;
import com.hwhub.backend.domain.enums.RecurrenceType;
import com.hwhub.backend.domain.enums.Weekday;
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateId;
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel;
import jakarta.validation.constraints.NotBlank;

public record AdminHouseworkTemplateRequest(
    @NotBlank String nameJa,
    @NotBlank String nameEn,
    @NotBlank String nameEs,
    String descriptionJa,
    String descriptionEn,
    String descriptionEs,
    String recommendationJa,
    String recommendationEn,
    String recommendationEs,
    @NotBlank String category,
    @NotBlank String recurrenceType,
    Integer weeklyDays,
    Integer dayOfMonth,
    String nthWeek,
    String weekday) {

  /** 新規作成用 */
  public HouseworkTemplateModel toModel() {
    return HouseworkTemplateModel.create(
        nameJa,
        nameEn,
        nameEs,
        descriptionJa,
        descriptionEn,
        descriptionEs,
        recommendationJa,
        recommendationEn,
        recommendationEs,
        Category.fromCode(category),
        RecurrenceType.fromCode(recurrenceType),
        weeklyDays,
        dayOfMonth,
        nthWeek != null ? NthWeek.fromCode(nthWeek) : null,
        weekday != null ? Weekday.fromCode(weekday) : null);
  }

  /** 更新用（id付き） */
  public HouseworkTemplateModel toModel(HouseworkTemplateId id) {
    return HouseworkTemplateModel.reconstruct(
        id,
        nameJa,
        nameEn,
        nameEs,
        descriptionJa,
        descriptionEn,
        descriptionEs,
        recommendationJa,
        recommendationEn,
        recommendationEs,
        Category.fromCode(category),
        RecurrenceType.fromCode(recurrenceType),
        weeklyDays,
        dayOfMonth,
        nthWeek != null ? NthWeek.fromCode(nthWeek) : null,
        weekday != null ? Weekday.fromCode(weekday) : null);
  }
}
