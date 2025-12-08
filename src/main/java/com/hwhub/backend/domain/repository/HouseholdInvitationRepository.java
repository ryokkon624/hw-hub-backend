package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseholdInvitationModel;
import java.util.List;

public interface HouseholdInvitationRepository {

  HouseholdInvitationModel selectByToken(String token);

  List<HouseholdInvitationModel> selectByHouseholdId(Long householdId);

  void update(HouseholdInvitationModel model, Long userId, String program);

  HouseholdInvitationModel insert(HouseholdInvitationModel model, Long userId, String program);

  long countByActiveEmail(Long householdId, String email);
}
