package com.hwhub.backend.presentation.rest.admin.dto;

import java.util.List;

public record AdminUserResponse(
    long userId,
    String email,
    String displayName,
    String locale,
    boolean isActive,
    List<String> roles) {}
