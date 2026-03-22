package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.enums.UserRole;
import com.hwhub.backend.domain.model.UserRoleModel;
import java.util.List;

public interface UserRoleRepository {
  List<UserRoleModel> findByUserId(Long userId);

  void insert(Long userId, UserRole role, Long operatorUserId, String program);

  void delete(Long userId, UserRole role, Long operatorUserId, String program);

  boolean exists(Long userId, UserRole role);
}
