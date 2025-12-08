package com.hwhub.backend.presentation.rest.shopping.attachment.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUploadUrlRequest(@NotBlank String fileName, @NotBlank String mimeType) {}
