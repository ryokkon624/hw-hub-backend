package com.hwhub.backend.presentation.rest.household.dto;

import com.hwhub.backend.validation.annotation.ByteSize;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateHouseholdRequest {

  @NotBlank
  @ByteSize(max = 100)
  private String name;
}
