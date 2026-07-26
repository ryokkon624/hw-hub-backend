package com.hwhub.backend.security;

import com.hwhub.backend.domain.enums.UserRole;
import com.hwhub.backend.domain.model.UserRoleModel;
import com.hwhub.backend.domain.repository.RolePermissionRepository;
import com.hwhub.backend.domain.repository.UserRoleRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RequiresPermissionAspect {

  private final UserRoleRepository userRoleRepository;
  private final RolePermissionRepository rolePermissionRepository;

  @Before("@annotation(requiresPermission)")
  public void checkPermission(RequiresPermission requiresPermission) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new AccessDeniedException("Unauthorized");
    }
    Long userId = Long.valueOf(auth.getName());

    List<UserRoleModel> userRoles = userRoleRepository.findByUserId(userId);
    if (userRoles.isEmpty()) {
      throw new AccessDeniedException("No role assigned");
    }

    List<UserRole> roles = userRoles.stream().map(m -> m.getRole()).toList();
    List<String> permissions = rolePermissionRepository.findPermissionsByRoles(roles);

    boolean hasPermission =
        Arrays.stream(requiresPermission.value()).anyMatch(p -> permissions.contains(p.getCode()));

    if (!hasPermission) {
      throw new AccessDeniedException("Permission denied");
    }
  }
}
