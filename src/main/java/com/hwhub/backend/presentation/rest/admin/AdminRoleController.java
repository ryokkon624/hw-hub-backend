package com.hwhub.backend.presentation.rest.admin;

import com.hwhub.backend.application.service.UserRoleService;
import com.hwhub.backend.domain.enums.Permission;
import com.hwhub.backend.domain.enums.UserRole;
import com.hwhub.backend.presentation.rest.admin.dto.AssignRoleRequest;
import com.hwhub.backend.presentation.rest.admin.dto.UserRoleResponse;
import com.hwhub.backend.security.CurrentUserId;
import com.hwhub.backend.security.RequiresPermission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminRoleController {

  private final UserRoleService userRoleService;

  /** 自分のロール・パーミッション取得（全認証済みユーザーが呼べる） */
  @GetMapping("/users/me/roles")
  public UserRoleResponse getMyRoles(@CurrentUserId Long userId) {
    return UserRoleResponse.from(userRoleService.getMyRolesAndPermissions(userId));
  }

  /** ロール付与（ROLE_MANAGEMENT パーミッション必須） */
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  @PostMapping("/admin/roles/{userId}")
  public void assignRole(
      @PathVariable("userId") Long userId,
      @RequestBody @Valid AssignRoleRequest request,
      @CurrentUserId Long operatorUserId) {
    UserRole role = UserRole.fromCode(request.role());
    userRoleService.assignRole(userId, role, operatorUserId);
  }

  /** ロール削除（ROLE_MANAGEMENT パーミッション必須） */
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  @DeleteMapping("/admin/roles/{userId}/{role}")
  public void removeRole(
      @PathVariable("userId") Long userId,
      @PathVariable("role") String role,
      @CurrentUserId Long operatorUserId) {
    UserRole userRole = UserRole.fromCode(role);
    userRoleService.removeRole(userId, userRole, operatorUserId);
  }
}
