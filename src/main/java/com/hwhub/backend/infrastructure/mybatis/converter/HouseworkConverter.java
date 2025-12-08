package com.hwhub.backend.infrastructure.mybatis.converter;

import static com.hwhub.backend.infrastructure.mybatis.converter.DateConverter.*;

import com.hwhub.backend.domain.model.HouseworkModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHousework;

public final class HouseworkConverter {

  private HouseworkConverter() {}

  public static HouseworkModel toModel(MHousework row) {
    if (row == null) return null;

    return HouseworkModel.reconstruct(
        row.getHouseworkId(),
        row.getHouseholdId(),
        row.getName(),
        row.getDescription(),
        row.getCategory(),
        row.getRecurrenceType(),
        row.getWeeklyDays(),
        row.getDayOfMonth(),
        row.getNthWeek(),
        row.getWeekday(),
        DateConverter.toLocalDate(row.getStartDate()),
        DateConverter.toLocalDate(row.getEndDate()),
        row.getDefaultAssigneeUserId());
  }

  public static MHousework toEntity(HouseworkModel model) {
    if (model == null) return null;

    MHousework entity = new MHousework();
    entity.setHouseworkId(model.getHouseworkId());
    entity.setHouseholdId(model.getHouseholdId());
    entity.setName(model.getName());
    entity.setDescription(model.getDescription());
    entity.setCategory(model.getCategory());
    entity.setRecurrenceType(model.getRecurrenceType());
    entity.setWeeklyDays(model.getWeeklyDays());
    entity.setDayOfMonth(model.getDayOfMonth());
    entity.setNthWeek(model.getNthWeek());
    entity.setWeekday(model.getWeekday());

    entity.setStartDate(toDate(model.getStartDate()));
    entity.setEndDate(toDate(model.getEndDate()));

    entity.setDefaultAssigneeUserId(model.getDefaultAssigneeUserId());

    return entity;
  }
}
