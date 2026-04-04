package com.hwhub.backend.presentation.rest.housework.dto;

import com.hwhub.backend.domain.enums.Category;
import com.hwhub.backend.domain.enums.RecurrenceType;
import com.hwhub.backend.domain.model.HouseworkModel;
import com.hwhub.backend.validation.annotation.ByteSize;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Data;

@Data
public class HouseworkSaveRequest {

  @NotNull private Long householdId;

  @NotBlank
  @ByteSize(max = 100)
  private String name;

  @ByteSize(max = 500)
  private String description;

  @NotBlank
  @Size(max = 10)
  @Pattern(regexp = "^[A-Z0-9_]+$")
  @EnumValue(enumClass = Category.class)
  private String category;

  @NotBlank
  @Size(max = 10)
  @Pattern(regexp = "^[A-Z0-9_]+$")
  @EnumValue(enumClass = RecurrenceType.class)
  private String recurrenceType;

  // 7桁ビットマスクであるため0～127
  @Min(0)
  @Max(127)
  private Integer weeklyDays;

  private Integer dayOfMonth;
  private Integer nthWeek;
  private Integer weekday;

  @NotNull private LocalDate startDate;

  @NotNull private LocalDate endDate;

  private Long defaultAssigneeUserId;

  public HouseworkModel toModelForCreate() {
    return HouseworkModel.create(
        this.householdId,
        this.name,
        this.description,
        this.category,
        this.recurrenceType,
        this.weeklyDays,
        this.dayOfMonth,
        this.nthWeek,
        this.weekday,
        this.startDate,
        this.endDate,
        this.defaultAssigneeUserId);
  }
}
