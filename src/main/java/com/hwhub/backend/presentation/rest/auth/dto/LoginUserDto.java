package com.hwhub.backend.presentation.rest.auth.dto;

import com.hwhub.backend.domain.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserDto {
  private Long userId;
  private String email;
  private String authProvider;
  private String displayName;
  private String locale;
  private String iconUrl;

  public static LoginUserDto fromModel(UserModel model) {
    if (model == null) {
      return null;
    }
    return new LoginUserDto(
        model.getUserId(),
        model.getEmail(),
        model.getAuthProvider(),
        model.getDisplayName(),
        model.getLocale(),
        model.getIconUrl());
  }
}
