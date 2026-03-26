package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.enums.UserRole;
import com.hwhub.backend.domain.model.UserRoleModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserRole;
import org.springframework.stereotype.Component;

@Component
public final class UserRoleConverter {
  public static UserRoleModel toModel(MUserRole entity) {
    return UserRoleModel.reconstruct(
        entity.getUserRoleId(), entity.getUserId(), UserRole.fromCode(entity.getRole()));
  }
}
