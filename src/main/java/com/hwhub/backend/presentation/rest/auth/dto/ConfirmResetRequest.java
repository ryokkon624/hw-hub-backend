package com.hwhub.backend.presentation.rest.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmResetRequest(@NotBlank String token, @NotBlank String newPassword) {}
