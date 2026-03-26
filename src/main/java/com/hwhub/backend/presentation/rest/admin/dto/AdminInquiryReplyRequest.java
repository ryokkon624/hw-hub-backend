package com.hwhub.backend.presentation.rest.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminInquiryReplyRequest(@NotBlank String body) {}
