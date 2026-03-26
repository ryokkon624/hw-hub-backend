package com.hwhub.backend.presentation.rest.admin.dto;

import com.hwhub.backend.application.service.UserRoleService.SearchUserResult;
import java.util.List;

public record AdminUserRolesResponse(
    long userId,
    String email,
    String displayName,
    String locale,
    boolean isActive,
    List<String> roles) {

  public static AdminUserRolesResponse from(SearchUserResult result) {
    return new AdminUserRolesResponse(
        result.user().getUserId(),
        result.user().getEmail(),
        result.user().getDisplayName(),
        result.user().getLocale(),
        result.user().isActive(),
        result.roles().stream().map(r -> r.getRole().getCode()).toList());
  }
}
