package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.enums.UserRole;
import com.hwhub.backend.domain.repository.RolePermissionRepository;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.UserRoleCustomMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisRolePermissionRepository implements RolePermissionRepository {

  private final UserRoleCustomMapper customMapper;

  @Override
  public List<String> findPermissionsByRole(UserRole role) {
    return customMapper.findPermissionsByRoles(List.of(role.getCode()));
  }

  @Override
  public List<String> findPermissionsByRoles(List<UserRole> roles) {
    if (roles.isEmpty()) return List.of();
    List<String> codes = roles.stream().map(r -> r.getCode()).toList();
    return customMapper.findPermissionsByRoles(codes);
  }
}
