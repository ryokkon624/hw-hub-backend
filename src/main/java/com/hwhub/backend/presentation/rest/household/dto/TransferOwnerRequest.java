package com.hwhub.backend.presentation.rest.household.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferOwnerRequest {
  @NotNull private Long newOwnerUserId;
}
