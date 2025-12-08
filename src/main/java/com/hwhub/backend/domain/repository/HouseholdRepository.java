package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseholdModel;

public interface HouseholdRepository {

  HouseholdModel insert(HouseholdModel model, Long userId, String program);

  HouseholdModel findById(Long householdId);

  void update(HouseholdModel model, Long userId, String program);
}
