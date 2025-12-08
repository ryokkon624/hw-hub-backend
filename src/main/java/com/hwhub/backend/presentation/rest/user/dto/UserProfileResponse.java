package com.hwhub.backend.presentation.rest.user.dto;

import com.hwhub.backend.domain.model.UserModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserProfileResponse {
  private Long userId;
  private String email;
  private String displayName;
  private String locale;
  private String iconUrl;

  public static UserProfileResponse fromModel(UserModel model) {
    UserProfileResponse dto = new UserProfileResponse();

    dto.setUserId(model.getUserId());
    dto.setEmail(model.getEmail());
    dto.setDisplayName(model.getDisplayName());
    dto.setLocale(model.getLocale());
    dto.setIconUrl(model.getIconUrl());

    return dto;
  }
}
