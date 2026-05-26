package com.hwhub.backend.presentation.rest.inquiry.dto;

import com.hwhub.backend.domain.enums.UiClient;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InquiryCreateRequest(
    @NotBlank String category,
    @NotBlank String title,
    @NotBlank String body,
    @NotNull @EnumValue(enumClass = UiClient.class) String uiClient,
    @NotBlank String uiVersion,
    @NotBlank String apiVersion) {}
