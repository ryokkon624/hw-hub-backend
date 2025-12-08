package com.hwhub.backend.presentation.rest.housework.dto;

import com.hwhub.backend.domain.model.HouseworkTask4AssignModel;
import java.time.LocalDate;

public record HouseworkTaskResponse(
    Long houseworkTaskId,
    Long householdId,
    Long houseworkId,
    String houseworkName,
    String categoryCode,
    LocalDate targetDate,
    Long assigneeUserId,
    String assigneeNickname,
    String status,
    String assignReasonType,
    LocalDate doneAt,
    String skippedReason) {

  public static HouseworkTaskResponse fromModel(HouseworkTask4AssignModel model) {
    return new HouseworkTaskResponse(
        model.getHouseworkTaskId(),
        model.getHouseholdId(),
        model.getHouseworkId(),
        model.getName(),
        model.getCategory(),
        model.getTargetDate(),
        model.getAssigneeUserId(),
        model.getAssigneeNickname(),
        model.getStatus(),
        model.getAssignReasonType(),
        model.getDoneAt(),
        model.getSkippedReason());
  }
}
