package com.hwhub.backend.presentation.rest.inquiry.dto;

import jakarta.validation.constraints.NotBlank;

public record InquiryMessageRequest(@NotBlank String body) {}
