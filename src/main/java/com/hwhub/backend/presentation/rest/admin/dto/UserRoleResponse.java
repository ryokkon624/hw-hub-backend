package com.hwhub.backend.presentation.rest.admin.dto;

import com.hwhub.backend.application.service.UserRoleService;
import java.util.List;

public record UserRoleResponse(List<String> roles, List<String> permissions) {
  public static UserRoleResponse from(UserRoleService.UserRoleResult result) {
    return new UserRoleResponse(result.roles(), result.permissions());
  }
}
