package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.HouseholdMemberModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseholdMember;

public final class HouseholdMemberConverter {
  private HouseholdMemberConverter() {}

  public static MHouseholdMember toEntity(HouseholdMemberModel model) {
    if (model == null) return null;

    MHouseholdMember entity = new MHouseholdMember();
    entity.setHouseholdId(model.getHouseholdId());
    entity.setUserId(model.getUserId());
    entity.setNickname(model.getNickname());
    entity.setStatus(model.getStatus());

    return entity;
  }

  public static HouseholdMemberModel toModel(MHouseholdMember entity) {
    if (entity == null) return null;

    return HouseholdMemberModel.reconstruct(
        entity.getHouseholdId(),
        entity.getUserId(),
        null,
        null,
        null,
        entity.getNickname(),
        entity.getStatus(),
        null);
  }
}
