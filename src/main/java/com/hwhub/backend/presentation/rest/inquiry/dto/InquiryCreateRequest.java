package com.hwhub.backend.presentation.rest.inquiry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InquiryCreateRequest(
    @NotNull Long householdId,
    @NotBlank String category,
    @NotBlank String title,
    @NotBlank String body) {}
