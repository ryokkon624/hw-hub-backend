package com.hwhub.backend.presentation.rest.housework.dto;

import com.hwhub.backend.domain.enums.TaskStatus;
import com.hwhub.backend.validation.annotation.ByteSize;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.constraints.NotBlank;

public record UpdateStatusRequest(
    @NotBlank @EnumValue(enumClass = TaskStatus.class) String status,
    @ByteSize(max = 255) String skippedReason) {}
