package com.hwhub.backend.domain.model;

import com.hwhub.backend.domain.enums.UserRole;
import lombok.Getter;

@Getter
public class UserRoleModel {
  private final long userRoleId;
  private final long userId;
  private final UserRole role;

  private UserRoleModel(long userRoleId, long userId, UserRole role) {
    this.userRoleId = userRoleId;
    this.userId = userId;
    this.role = role;
  }

  public static UserRoleModel reconstruct(long userRoleId, long userId, UserRole role) {
    return new UserRoleModel(userRoleId, userId, role);
  }
}
