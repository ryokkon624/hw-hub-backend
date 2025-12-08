package com.hwhub.backend.presentation.rest.household.dto;

import java.util.List;
import lombok.Data;

@Data
public class HouseholdMembersDto {
  private List<HouseholdMemberDto> members;
}
