package com.hwhub.backend.presentation.rest.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateThemeRequest(@NotBlank String themeMode) {}
