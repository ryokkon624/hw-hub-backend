package com.hwhub.backend.presentation.rest.shopping.dto;

import com.hwhub.backend.domain.enums.ShoppingItemStatus;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record BulkUpdateStatusRequest(
    @NotEmpty List<Long> ids,
    @NotBlank @EnumValue(enumClass = ShoppingItemStatus.class) String status) {}
