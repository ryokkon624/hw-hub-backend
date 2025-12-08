package com.hwhub.backend.presentation.rest.user.dto;

import com.hwhub.backend.domain.model.HouseholdModel;
import lombok.Data;

@Data
public class UserHouseholdDto {
  private Long householdId;
  private String name;
  private Long ownerUserId;

  public static UserHouseholdDto fromModel(HouseholdModel model) {
    UserHouseholdDto dto = new UserHouseholdDto();

    dto.setHouseholdId(model.getHouseholdId());
    dto.setName(model.getName());
    dto.setOwnerUserId(model.getOwnerUserId());

    return dto;
  }
}
