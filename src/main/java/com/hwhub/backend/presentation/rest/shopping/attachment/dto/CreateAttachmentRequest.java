package com.hwhub.backend.presentation.rest.shopping.attachment.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAttachmentRequest(
    @NotBlank String fileKey, @NotBlank String fileName, @NotBlank String mimeType) {}
