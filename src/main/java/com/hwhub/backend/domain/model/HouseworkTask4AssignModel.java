package com.hwhub.backend.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import lombok.Getter;

@Getter
public class HouseworkTask4AssignModel extends HouseworkTaskModel {

  private String assigneeNickname;

  private HouseworkTask4AssignModel(
      Long houseworkTaskId,
      Long householdId,
      Long houseworkId,
      String name,
      String description,
      String category,
      LocalDate targetDate,
      Long assigneeUserId,
      String status,
      String assignReasonType,
      LocalDate doneAt,
      String skippedReason,
      String assigneeNickname) {
    super(
        houseworkTaskId,
        householdId,
        houseworkId,
        name,
        description,
        category,
        targetDate,
        assigneeUserId,
        status,
        assignReasonType,
        doneAt,
        skippedReason,
        new ArrayList<>());
    this.assigneeNickname = assigneeNickname;
  }
}
