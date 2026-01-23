package com.hwhub.backend.presentation.rest.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestResetRequest(@NotBlank @Email String email) {}
