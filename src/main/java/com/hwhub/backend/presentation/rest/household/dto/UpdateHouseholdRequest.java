package com.hwhub.backend.presentation.rest.household.dto;

import com.hwhub.backend.validation.annotation.ByteSize;
import jakarta.validation.constraints.NotBlank;

public record UpdateHouseholdRequest(@NotBlank @ByteSize(max = 100) String name) {}
