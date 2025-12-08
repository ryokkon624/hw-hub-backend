package com.hwhub.backend.presentation.rest.housework.dto;

import com.hwhub.backend.domain.enums.TaskAssignReason;
import com.hwhub.backend.validation.annotation.EnumValue;

public record UpdateAssigneeRequest(
    Long assigneeUserId, @EnumValue(enumClass = TaskAssignReason.class) String assignReasonType) {}
