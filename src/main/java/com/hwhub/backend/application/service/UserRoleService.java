package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.Permission;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.enums.UserRole;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.model.UserRoleModel;
import com.hwhub.backend.domain.repository.RolePermissionRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.domain.repository.UserRoleRepository;
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

  /**
   * 指定されたユーザーのロールとパーミッションを返す。 GET /api/users/me/roles から呼ばれる。
   *
   * @param userId ユーザーID
   * @return ロールとパーミッションのリスト
   */
  @Transactional(readOnly = true)
  public UserRoleResult getMyRolesAndPermissions(Long userId) {
    List<UserRoleModel> userRoles = userRoleRepository.findByUserId(userId);
    List<UserRole> roles = userRoles.stream().map(m -> m.getRole()).toList();
    List<String> permissions =
        roles.isEmpty() ? List.of() : rolePermissionRepository.findPermissionsByRoles(roles);

    return new UserRoleResult(roles.stream().map(r -> r.getCode()).toList(), permissions);
  }

  /**
   * メールアドレスでユーザーを検索してロール情報を付与して返す。
   *
   * @param email メールアドレス
   * @return ユーザーとロールのリスト
   */
  @Transactional(readOnly = true)
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  public List<SearchUserResult> searchUsers(String email) {
    List<UserModel> users = userRepository.searchByEmail(email);

    return users.stream()
        .map(
            user -> {
              List<UserRoleModel> roles = userRoleRepository.findByUserId(user.getUserId());
              return new SearchUserResult(user, roles);
            })
        .toList();
  }

  /**
   * 指定されたユーザーにロールを付与する。 すでに持っている場合は何もしない（冪等）。
   *
   * @param targetUserId 対象ユーザーID
   * @param role ロール
   * @param operatorUserId 操作ユーザーID
   */
  @Transactional
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  public void assignRole(Long targetUserId, UserRole role, Long operatorUserId) {
    if (userRoleRepository.exists(targetUserId, role)) return;
    userRoleRepository.insert(
        targetUserId, role, operatorUserId, ProgramType.ONL_USR_ROLE.getCode());
  }

  /**
   * ユーザーからロールを削除する。
   *
   * @param targetUserId 対象ユーザーID
   * @param role ロール
   * @param operatorUserId 操作ユーザーID
   */
  @Transactional
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  public void removeRole(Long targetUserId, UserRole role, Long operatorUserId) {
    userRoleRepository.delete(
        targetUserId, role, operatorUserId, ProgramType.ONL_USR_ROLE.getCode());
  }

  public record UserRoleResult(List<String> roles, List<String> permissions) {}

  public record SearchUserResult(UserModel user, List<UserRoleModel> roles) {}
}
