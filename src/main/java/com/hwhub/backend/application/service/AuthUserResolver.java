package com.hwhub.backend.application.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUserResolver {

  public Long requireUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new IllegalStateException("Unauthenticated");
    }

    Object principal = auth.getPrincipal();

    // JwtAuthenticationFilter が userId.toString()を格納しているため
    if (principal instanceof String s) {
      try {
        return Long.parseLong(s);
      } catch (NumberFormatException e) {
        throw new IllegalStateException("Invalid principal (not userId): " + s, e);
      }
    }

    // principal の型を変えた時に原因が分かるように。 just in case
    throw new IllegalStateException("Unsupported principal type: " + principal.getClass());
  }
}
