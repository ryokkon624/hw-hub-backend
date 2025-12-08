package com.hwhub.backend.presentation.rest.user.dto;

import com.hwhub.backend.domain.enums.LocaleType;
import com.hwhub.backend.validation.annotation.ByteSize;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(
    @NotBlank @ByteSize(max = 100) String displayName,
    @NotBlank
        @Size(max = 5) // "ja", "en", "es"
        @EnumValue(enumClass = LocaleType.class)
        String locale) {}
