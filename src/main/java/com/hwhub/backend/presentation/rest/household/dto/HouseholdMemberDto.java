package com.hwhub.backend.presentation.rest.household.dto;

import com.hwhub.backend.domain.model.HouseholdMemberModel;
import lombok.Data;

@Data
public class HouseholdMemberDto {
  private Long householdId;
  private Long userId;
  private String displayName;
  private String iconUrl;
  private String nickname;
  private String status;
  private String role;

  public static HouseholdMemberDto fromModel(HouseholdMemberModel model) {
    HouseholdMemberDto dto = new HouseholdMemberDto();

    dto.setHouseholdId(model.getHouseholdId());
    dto.setUserId(model.getUserId());
    dto.setDisplayName(model.getDisplayName());
    dto.setIconUrl(model.getIconUrl());
    dto.setNickname(model.getNickname());
    dto.setStatus(model.getStatus());
    dto.setRole(model.getRole());

    return dto;
  }
}
