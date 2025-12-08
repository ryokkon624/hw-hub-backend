package com.hwhub.backend.presentation.rest.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateIconRequest(@NotBlank String fileKey) {}
