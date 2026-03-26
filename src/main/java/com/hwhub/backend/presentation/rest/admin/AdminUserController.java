package com.hwhub.backend.presentation.rest.admin;

import com.hwhub.backend.application.service.AdminUserService;
import com.hwhub.backend.application.service.UserRoleService;
import com.hwhub.backend.domain.enums.Permission;
import com.hwhub.backend.domain.model.AdminUserSearchCondition;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.presentation.rest.admin.dto.AdminCreateUserRequest;
import com.hwhub.backend.presentation.rest.admin.dto.AdminUpdateUserRequest;
import com.hwhub.backend.presentation.rest.admin.dto.AdminUserResponse;
import com.hwhub.backend.presentation.rest.admin.dto.AdminUserRolesResponse;
import com.hwhub.backend.security.RequiresPermission;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminUserController {

  private final UserRoleService userRoleService;
  private final AdminUserService adminUserService;

  /** メールアドレスでユーザーを検索する。 ロール管理に使用。 ROLE_MANAGEMENT パーミッション必須。 */
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  @GetMapping("/users")
  public List<AdminUserRolesResponse> searchUsers(
      @RequestParam("email") String email, Authentication authentication) {
    return userRoleService.searchUsers(email).stream().map(AdminUserRolesResponse::from).toList();
  }

  /** 管理者: ユーザー一覧検索 */
  @RequiresPermission(Permission.USER_LIST_VIEW)
  @GetMapping("/users/search")
  public List<AdminUserResponse> searchUsers(
      @RequestParam(name = "email", required = false) String email,
      @RequestParam(name = "isActive", required = false) Boolean isActive,
      @RequestParam(name = "locale", required = false) String locale) {

    AdminUserSearchCondition condition = new AdminUserSearchCondition(email, isActive, locale);

    return adminUserService.searchUsers(condition).stream().map(AdminUserResponse::from).toList();
  }

  /** 管理者: ユーザー登録 */
  @RequiresPermission(Permission.USER_LIST_VIEW)
  @PostMapping("/users")
  public AdminUserResponse createUser(
      @RequestBody @Valid AdminCreateUserRequest request, Authentication authentication) {
    Long operatorUserId = Long.valueOf(authentication.getName());
    UserModel created =
        adminUserService.createUser(
            request.email(),
            request.password(),
            request.displayName(),
            request.locale(),
            operatorUserId);
    return AdminUserResponse.from(created);
  }

  /** 管理者: ユーザー更新 */
  @RequiresPermission(Permission.USER_LIST_VIEW)
  @PutMapping("/users/{userId}")
  public AdminUserResponse updateUser(
      @PathVariable("userId") Long userId,
      @RequestBody @Valid AdminUpdateUserRequest request,
      Authentication authentication) {
    Long operatorUserId = Long.valueOf(authentication.getName());
    UserModel updated =
        adminUserService.updateUser(
            userId,
            request.displayName(),
            request.locale(),
            request.password(),
            request.isActive(),
            operatorUserId);
    return AdminUserResponse.from(updated);
  }
}
