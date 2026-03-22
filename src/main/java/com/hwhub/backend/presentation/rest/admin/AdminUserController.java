package com.hwhub.backend.presentation.rest.admin;

import com.hwhub.backend.application.service.UserRoleService;
import com.hwhub.backend.domain.enums.Permission;
import com.hwhub.backend.presentation.rest.admin.dto.AdminUserResponse;
import com.hwhub.backend.security.RequiresPermission;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminUserController {

  private final UserRoleService userRoleService;

  /** メールアドレスでユーザーを検索する。 ロール管理に使用。 ROLE_MANAGEMENT パーミッション必須。 */
  @RequiresPermission(Permission.ROLE_MANAGEMENT)
  @GetMapping("/users")
  public List<AdminUserResponse> searchUsers(
      @RequestParam("email") String email, Authentication authentication) {
    return userRoleService.searchUsers(email);
  }
}
