package com.hwhub.backend.presentation.rest.user.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateIconUploadUrlRequest(@NotBlank String fileName, @NotBlank String mimeType) {}
