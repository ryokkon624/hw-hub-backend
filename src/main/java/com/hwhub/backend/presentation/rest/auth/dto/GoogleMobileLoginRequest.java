package com.hwhub.backend.presentation.rest.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record GoogleMobileLoginRequest(@NotBlank String idToken) {}
