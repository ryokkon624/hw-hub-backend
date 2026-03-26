package com.hwhub.backend.presentation.rest.inquiry.dto;

import jakarta.validation.constraints.NotBlank;

public record InquiryCreateRequest(
    @NotBlank String category, @NotBlank String title, @NotBlank String body) {}
