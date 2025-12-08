package com.hwhub.backend.presentation.rest.housework.dto;

import com.hwhub.backend.domain.model.HouseworkModel;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/** 家事1件分のDTO。 API入出力用の形。Domainモデル(HouseworkModel)とは明示的に変換する。 */
@Getter
@Setter
public class HouseworkDto {

  private Long houseworkId;
  private Long householdId;
  private String name;
  private String description;
  private String category;
  private String recurrenceType;
  private Integer weeklyDays;
  private Integer dayOfMonth;
  private Integer nthWeek;
  private Integer weekday;
  private LocalDate startDate;
  private LocalDate endDate;
  private Long defaultAssigneeUserId;

  // ★ Domain -> DTO 変換ヘルパ
  public static HouseworkDto fromModel(HouseworkModel model) {
    HouseworkDto dto = new HouseworkDto();

    dto.setHouseworkId(model.getHouseworkId());
    dto.setHouseholdId(model.getHouseholdId());
    dto.setName(model.getName());
    dto.setDescription(model.getDescription());
    dto.setCategory(model.getCategory());
    dto.setRecurrenceType(model.getRecurrenceType());
    dto.setWeeklyDays(model.getWeeklyDays());
    dto.setDayOfMonth(model.getDayOfMonth());
    dto.setNthWeek(model.getNthWeek());
    dto.setWeekday(model.getWeekday());
    dto.setStartDate(model.getStartDate());
    dto.setEndDate(model.getEndDate());
    dto.setDefaultAssigneeUserId(model.getDefaultAssigneeUserId());

    return dto;
  }
}
