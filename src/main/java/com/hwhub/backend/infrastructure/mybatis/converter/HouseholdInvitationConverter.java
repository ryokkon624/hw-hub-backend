package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.HouseholdInvitationModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseholdInvitation;

public final class HouseholdInvitationConverter {
  private HouseholdInvitationConverter() {}

  public static THouseholdInvitation toEntity(HouseholdInvitationModel model) {
    if (model == null) {
      return null;
    }

    THouseholdInvitation entity = new THouseholdInvitation();
    entity.setHouseholdId(model.getHouseholdId());
    entity.setInviterUserId(model.getInviterUserId());
    entity.setInvitedEmail(model.getInvitedEmail());
    entity.setInvitationToken(model.getInvitationToken());
    entity.setStatus(model.getStatus());
    entity.setExpiresAt(DateConverter.toDate(model.getExpiresAt()));
    entity.setAcceptedUserId(model.getAcceptedUserId());
    entity.setCreatedAt(DateConverter.toDate(model.getCreatedAt()));

    return entity;
  }
}
