package com.hwhub.backend.presentation.rest.housework.dto;

import com.hwhub.backend.domain.model.HouseworkTemplate.HouseworkTemplateModel;

public record HouseworkTemplateResponse(
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
    Integer nthWeek,
    Integer weekday) {

  public static HouseworkTemplateResponse from(HouseworkTemplateModel model) {
    return new HouseworkTemplateResponse(
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
        model.getNthWeek() == null ? null : Integer.parseInt(model.getNthWeek().getCode()),
        model.getWeekday() == null ? null : Integer.parseInt(model.getWeekday().getCode()));
  }
}
