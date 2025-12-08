package com.hwhub.backend.presentation.rest.household;

import com.hwhub.backend.application.service.HouseholdService;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.presentation.rest.household.dto.CreateHouseholdRequest;
import com.hwhub.backend.presentation.rest.household.dto.HouseholdDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/households")
@RequiredArgsConstructor
public class HouseholdController {

  private final HouseholdService householdService;

  @PostMapping
  public HouseholdDto create(
      @Valid @RequestBody CreateHouseholdRequest request, Authentication authentication) {
    Long userId = Long.valueOf(authentication.getName());

    HouseholdModel model = householdService.createHousehold(userId, request.getName());

    return HouseholdDto.fromModel(model);
  }
}
