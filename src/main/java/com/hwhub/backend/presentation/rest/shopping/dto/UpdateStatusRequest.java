package com.hwhub.backend.presentation.rest.shopping.dto;

import com.hwhub.backend.domain.enums.ShoppingItemStatus;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.constraints.NotBlank;

public record UpdateStatusRequest(
    @NotBlank @EnumValue(enumClass = ShoppingItemStatus.class) String status) {}
