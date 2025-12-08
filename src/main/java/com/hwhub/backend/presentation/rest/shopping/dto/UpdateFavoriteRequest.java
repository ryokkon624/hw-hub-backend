package com.hwhub.backend.presentation.rest.shopping.dto;

import com.hwhub.backend.domain.enums.FavoriteFlag;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.constraints.NotBlank;

public record UpdateFavoriteRequest(
    @NotBlank @EnumValue(enumClass = FavoriteFlag.class) String favorite) {}
