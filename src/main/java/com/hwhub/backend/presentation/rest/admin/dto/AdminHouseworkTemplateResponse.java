package com.hwhub.backend.presentation.rest.admin.dto;

import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel;

public record AdminHouseworkTemplateResponse(
    long houseworkTemplateId,
    String nameJa,
    String nameEn,
    String nameEs,
    String descriptionJa,
    String descriptionEn,
    String descriptionEs,
    String recommendationJa,
    String recommendationEn,
    String recommendationEs,
    String category,
    String recurrenceType,
    Integer weeklyDays,
    Integer dayOfMonth,
    String nthWeek,
    String weekday) {

  public static AdminHouseworkTemplateResponse from(HouseworkTemplateModel model) {
    return new AdminHouseworkTemplateResponse(
        model.getHouseworkTemplateId().value(),
        model.getNameJa(),
        model.getNameEn(),
        model.getNameEs(),
        model.getDescriptionJa(),
        model.getDescriptionEn(),
        model.getDescriptionEs(),
        model.getRecommendationJa(),
        model.getRecommendationEn(),
        model.getRecommendationEs(),
        model.getCategory().getCode(),
        model.getRecurrenceType().getCode(),
        model.getWeeklyDays(),
        model.getDayOfMonth(),
        model.getNthWeek() != null ? model.getNthWeek().getCode() : null,
        model.getWeekday() != null ? model.getWeekday().getCode() : null);
  }
}
