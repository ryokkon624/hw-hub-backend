package com.hwhub.backend.presentation.rest.shopping.dto;

import com.hwhub.backend.validation.annotation.ByteSize;
import jakarta.validation.constraints.NotBlank;

public record UpdateShoppingItemRequest(
    @NotBlank @ByteSize(max = 100) String name,
    @ByteSize(max = 255) String memo,
    @NotBlank String storeType,
    @NotBlank String favorite // m_code:0013（"0" or "1"）
    ) {}
