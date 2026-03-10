package com.hwhub.backend.presentation.rest.housework.dto;

import com.hwhub.backend.domain.enums.TaskStatus;
import com.hwhub.backend.validation.annotation.ByteSize;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record BulkUpdateStatusRequest(
    @NotEmpty List<@NotNull Long> taskIds,
    @NotBlank @EnumValue(enumClass = TaskStatus.class) String status,
    @ByteSize(max = 255) String skippedReason) {}
