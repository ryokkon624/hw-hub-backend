package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseworkTask4AssignModel;
import com.hwhub.backend.domain.model.HouseworkTaskModel;
import java.time.LocalDate;
import java.util.List;

public interface HouseworkTaskRepository {

  List<HouseworkTask4AssignModel> findForAssign(
      Long householdId, String status, LocalDate lowerDate);

  HouseworkTaskModel findById(Long houseworkTaskId);

  HouseworkTaskModel update(HouseworkTaskModel houseworkTaskModel, Long userId, String program);

  void clearAssignee(Long householdId, Long userId, Long loginUserId, String program);

  long countByIdsAndHouseholdId(List<Long> taskIds, Long householdId);

  void bulkUpdateStatus(
      List<Long> taskIds,
      String status,
      String skippedReason,
      LocalDate doneAt,
      Long userId,
      String program);
}
