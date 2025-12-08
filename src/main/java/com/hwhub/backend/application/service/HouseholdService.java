package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.HouseholdRepository;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HouseholdService {

  private final HouseholdRepository householdRepository;
  private final HouseholdAuthorizationService householdAuthorizationService;
  private final HouseholdMemberService householdMemberService;
  private final UserService userService;

  @Transactional
  public void updateHouseholdName(Long householdId, Long userId, String name) {
    // 認可チェック
    householdAuthorizationService.assertUserBelongsToHousehold(householdId, userId);

    HouseholdModel model = householdRepository.findById(householdId);
    if (model == null) {
      throw new ResourceNotFoundException("Household not found. householdId=" + householdId);
    }

    model.changeName(name);
    householdRepository.update(model, userId, ProgramType.ONL_HLD.getCode());
  }

  @Transactional
  public HouseholdModel createHousehold(Long userId, String name) {

    // 世帯登録
    HouseholdModel model = HouseholdModel.create(name, userId);
    HouseholdModel inserted =
        householdRepository.insert(model, userId, ProgramType.ONL_HLD.getCode());

    // 自身をメンバーとして登録。ニックネーム初期値はユーザの表示名。
    UserModel userModel = userService.getProfile(userId);
    householdMemberService.createMember(
        inserted.getHouseholdId(), userId, userModel.getDisplayName(), userId);

    return inserted;
  }
}
