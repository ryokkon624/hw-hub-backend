package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.enums.Category;
import com.hwhub.backend.domain.enums.NthWeek;
import com.hwhub.backend.domain.enums.RecurrenceType;
import com.hwhub.backend.domain.enums.Weekday;
import com.hwhub.backend.domain.model.HouseworkTemplate.HouseworkTemplateId;
import com.hwhub.backend.domain.model.HouseworkTemplate.HouseworkTemplateModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseworkTemplate;

public final class HouseworkTemplateConverter {

  private HouseworkTemplateConverter() {}

  public static HouseworkTemplateModel toModel(MHouseworkTemplate entity) {
    if (entity == null) return null;

    return HouseworkTemplateModel.reconstruct(
        new HouseworkTemplateId(entity.getHouseworkTemplateId()),
        entity.getNameJa(),
        entity.getNameEn(),
        entity.getNameEs(),
        entity.getDescriptionJa(),
        entity.getDescriptionEn(),
        entity.getDescriptionEs(),
        entity.getRecommendationJa(),
        entity.getRecommendationEn(),
        entity.getRecommendationEs(),
        Category.fromCode(entity.getCategory()),
        RecurrenceType.fromCode(entity.getRecurrenceType()),
        entity.getWeeklyDays(),
        entity.getDayOfMonth(),
        entity.getNthWeek() == null ? null : NthWeek.fromCode(entity.getNthWeek().toString()),
        entity.getWeekday() == null ? null : Weekday.fromCode(entity.getWeekday().toString()));
  }

  public static MHouseworkTemplate toEntity(HouseworkTemplateModel model) {
    if (model == null) return null;

    MHouseworkTemplate entity = new MHouseworkTemplate();
    if (model.getHouseworkTemplateId() != null)
      entity.setHouseworkTemplateId(model.getHouseworkTemplateId().value());
    entity.setNameJa(model.getNameJa());
    entity.setNameEn(model.getNameEn());
    entity.setNameEs(model.getNameEs());
    entity.setDescriptionJa(model.getDescriptionJa());
    entity.setDescriptionEn(model.getDescriptionEn());
    entity.setDescriptionEs(model.getDescriptionEs());
    entity.setRecommendationJa(model.getRecommendationJa());
    entity.setRecommendationEn(model.getRecommendationEn());
    entity.setRecommendationEs(model.getRecommendationEs());
    entity.setCategory(model.getCategory().getCode());
    entity.setRecurrenceType(model.getRecurrenceType().getCode());
    entity.setWeeklyDays(model.getWeeklyDays());
    entity.setDayOfMonth(model.getDayOfMonth());
    if (model.getNthWeek() != null) {
      entity.setNthWeek(Integer.parseInt(model.getNthWeek().getCode()));
    }
    if (model.getWeekday() != null) {
      entity.setWeekday(Integer.parseInt(model.getWeekday().getCode()));
    }
    return entity;
  }
}
