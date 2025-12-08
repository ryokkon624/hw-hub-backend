package com.hwhub.backend.presentation.rest.household.dto;

import com.hwhub.backend.validation.annotation.ByteSize;
import jakarta.validation.constraints.NotBlank;

public record UpdateMyNicknameRequest(@NotBlank @ByteSize(max = 100) String nickname) {}
