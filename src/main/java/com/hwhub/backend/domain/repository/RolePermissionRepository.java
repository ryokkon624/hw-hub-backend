package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.enums.UserRole;
import java.util.List;

public interface RolePermissionRepository {
  /** 指定ロールが持つ Permission の code_value リストを返す */
  List<String> findPermissionsByRole(UserRole role);

  /** 複数ロールが持つ Permission を集約して返す（重複なし） */
  List<String> findPermissionsByRoles(List<UserRole> roles);
}
