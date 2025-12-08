package com.hwhub.backend.presentation.rest.household.dto;

import com.hwhub.backend.domain.model.HouseholdModel;
import lombok.Data;

@Data
public class HouseholdDto {
  Long householdId;
  String name;
  Long ownerUserId;

  public static HouseholdDto fromModel(HouseholdModel model) {
    HouseholdDto householdDto = new HouseholdDto();

    householdDto.setHouseholdId(model.getHouseholdId());
    householdDto.setName(model.getName());
    householdDto.setOwnerUserId(model.getOwnerUserId());

    return householdDto;
  }
}
