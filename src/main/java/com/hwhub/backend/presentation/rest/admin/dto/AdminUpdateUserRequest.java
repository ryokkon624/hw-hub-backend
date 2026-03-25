package com.hwhub.backend.presentation.rest.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminUpdateUserRequest(
    @NotBlank String displayName,
    @NotBlank String locale,
    String password, // null/空白の場合は変更しない
    Boolean isActive) {}
