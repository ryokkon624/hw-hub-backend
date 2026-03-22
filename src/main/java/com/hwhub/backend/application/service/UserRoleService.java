package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.Permission;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.enums.UserRole;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.model.UserRoleModel;
import com.hwhub.backend.domain.repository.RolePermissionRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.domain.repository.UserRoleRepository;
import com.hwhub.backend.presentation.rest.admin.dto.AdminUserResponse;
import com.hwhub.backend.security.RequiresPermission;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

  private final UserRoleRepository userRoleRepository;
  private final RolePermissionRepository rolePermissionRepository;
  private final UserRepository userRepository;

  /** ログインユーザーのロールとパーミッションを返す。 GET /api/users/me/roles から呼ばれる。 */
  @Transactional(readOnly = true)
  public UserRoleResult getMyRolesAndPermissions(Long userId) {
    List<UserRoleModel> userRoles = userRoleRepository.findByUserId(userId);
    List<UserRole> roles = userRoles.stream().map(UserRoleModel::getRole).toList();
    List<String> permissions =
        roles.isEmpty() ? List.of() : rolePermissionRepository.findPermissionsByRoles(roles);

    return new UserRoleResult(roles.stream().map(UserRole::getCode).toList(), permissions);
  }

  /** メールアドレスでユーザーを検索してロール情報を付与して返す。 */
  @Transactional(readOnly = true)
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  public List<AdminUserResponse> searchUsers(String email) {
    List<UserModel> users = userRepository.searchByEmail(email);

    return users.stream()
        .map(
            user -> {
              List<UserRoleModel> roles = userRoleRepository.findByUserId(user.getUserId());
              List<String> roleCodes = roles.stream().map(r -> r.getRole().getCode()).toList();
              return new AdminUserResponse(
                  user.getUserId(),
                  user.getEmail(),
                  user.getDisplayName(),
                  user.getLocale(),
                  user.isActive(),
                  roleCodes);
            })
        .toList();
  }

  /** ユーザーにロールを付与する。 すでに持っている場合は何もしない（冪等）。 */
  @Transactional
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  public void assignRole(Long targetUserId, UserRole role, Long operatorUserId) {
    if (userRoleRepository.exists(targetUserId, role)) return;
    userRoleRepository.insert(
        targetUserId, role, operatorUserId, ProgramType.ONL_USR_ROLE.getCode());
  }

  /** ユーザーからロールを削除する。 */
  @Transactional
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  public void removeRole(Long targetUserId, UserRole role, Long operatorUserId) {
    userRoleRepository.delete(
        targetUserId, role, operatorUserId, ProgramType.ONL_USR_ROLE.getCode());
  }

  public record UserRoleResult(List<String> roles, List<String> permissions) {}
}
