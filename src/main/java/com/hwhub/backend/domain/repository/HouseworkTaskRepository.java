package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseworkTask4AssignModel;
import com.hwhub.backend.domain.model.HouseworkTaskModel;
import java.util.List;

public interface HouseworkTaskRepository {

  List<HouseworkTask4AssignModel> findForAssign(Long householdId, String status);

  HouseworkTaskModel findById(Long houseworkTaskId);

  HouseworkTaskModel update(HouseworkTaskModel houseworkTaskModel, Long userId, String program);

  void clearAssignee(Long householdId, Long userId, Long loginUserId, String program);
}
