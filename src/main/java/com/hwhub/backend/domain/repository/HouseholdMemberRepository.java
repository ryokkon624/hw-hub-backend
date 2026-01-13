package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseholdMemberModel;
import java.util.List;

public interface HouseholdMemberRepository {
  List<HouseholdMemberModel> findActiveByHouseholdId(Long householdId);

  boolean existsActiveByHouseholdIdAndUserId(Long householdId, Long userId);

  void insert(HouseholdMemberModel model, Long userId, String program);

  HouseholdMemberModel findById(Long householdId, Long userId);

  void update(HouseholdMemberModel model, Long userId, String program);

  void deleteByHouseholdId(Long householdId);
}
