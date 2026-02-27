package com.hwhub.backend.application.service;

import com.hwhub.backend.application.service.notification.NotificationPublisher;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.HouseholdMemberModel;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.HouseholdMemberRepository;
import com.hwhub.backend.domain.repository.HouseholdRepository;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HouseholdService {

  private final HouseholdRepository householdRepository;
  private final HouseholdAuthorizationService householdAuthorizationService;
  private final HouseholdMemberService householdMemberService;
  private final UserService userService;
  private final HouseholdMemberRepository householdMemberRepository;
  private final NotificationPublisher notificationPublisher;

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

  @Transactional
  public void deleteHousehold(Long householdId, Long userId) {
    HouseholdModel household = householdRepository.findById(householdId);
    if (household == null) {
      throw new ResourceNotFoundException("Household not found: " + householdId);
    }

    if (!household.isOwner(userId)) {
      throw new AccessDeniedException("Only the owner can delete the household.");
    }

    // 他の有効なメンバーがいる場合は削除不可（安全策）
    List<HouseholdMemberModel> activeMembers =
        householdMemberRepository.findActiveByHouseholdId(householdId);
    if (activeMembers.size() > 1) {
      throw new IllegalArgumentException(
          "Cannot delete household with other active members. Please remove them first.");
    }

    // メンバーを物理削除（全員）
    householdMemberRepository.deleteByHouseholdId(householdId);

    // バッチ削除対象タイマーとして updated_at を更新
    householdRepository.update(household, userId, ProgramType.ONL_HLD.getCode());
  }

  @Transactional
  public void transferOwnership(Long householdId, Long currentUserId, Long newOwnerUserId) {
    HouseholdModel household = householdRepository.findById(householdId);
    if (household == null) {
      throw new ResourceNotFoundException("Household not found: " + householdId);
    }

    if (!household.isOwner(currentUserId)) {
      throw new AccessDeniedException("Only the owner can transfer ownership.");
    }

    // 新しいオーナーが世帯に参加しているかチェック
    List<HouseholdMemberModel> activeMembers =
        householdMemberRepository.findActiveByHouseholdId(householdId);
    boolean isNewOwnerMember =
        activeMembers.stream().anyMatch(m -> m.getUserId().equals(newOwnerUserId));

    if (!isNewOwnerMember) {
      throw new IllegalArgumentException(
          "The new owner must be an active member of the household.");
    }

    household.changeOwner(newOwnerUserId);
    householdRepository.update(household, currentUserId, ProgramType.ONL_HLD.getCode());

    // 通知
    notificationPublisher.publishAssigned2Owner(
        householdId, currentUserId, newOwnerUserId, ProgramType.ONL_HLD.getCode());
  }
}
