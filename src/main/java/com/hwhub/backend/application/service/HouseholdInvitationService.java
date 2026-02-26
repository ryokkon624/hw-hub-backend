package com.hwhub.backend.application.service;

import com.hwhub.backend.application.service.notification.NotificationPublisher;
import com.hwhub.backend.domain.enums.HouseholdMemberStatus;
import com.hwhub.backend.domain.enums.InvitationStatus;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.HouseholdInvitationModel;
import com.hwhub.backend.domain.model.HouseholdMemberModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.HouseholdInvitationRepository;
import com.hwhub.backend.domain.repository.HouseholdMemberRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HouseholdInvitationService {

  private final UserRepository userRepository;
  private final HouseholdInvitationRepository invRepository;
  private final HouseholdMemberRepository memberRepository;
  private final HouseholdMemberService memberService;
  private final HouseholdAuthorizationService authorizationService;
  private final NotificationPublisher notificationPublisher;

  @Transactional(readOnly = true)
  public HouseholdInvitationModel getInvitation(String token) {
    HouseholdInvitationModel model = invRepository.selectByToken(token);
    if (Objects.isNull(model)) {
      throw new ResourceNotFoundException("invitation.notFound");
    }

    return model;
  }

  @Transactional(readOnly = true)
  public List<HouseholdInvitationModel> getInvitations(Long householdId, Long userId) {
    // 認可チェック
    if (!authorizationService.canAccessHousehold(householdId, userId)) {
      throw new AccessDeniedException(
          "User does not belong to household: userId=" + userId + ", householdId=" + householdId);
    }
    return invRepository.selectByHouseholdId(householdId);
  }

  @Transactional
  public void acceptInvitation(String token, Long userId) {
    HouseholdInvitationModel inv = invRepository.selectByToken(token);
    if (Objects.isNull(inv)) {
      throw new ResourceNotFoundException("invitation.notFound");
    }

    if (inv.isTerminal()) {
      throw new ResourceNotFoundException("invitation.alreadyHandled");
    }

    if (inv.isExpired()) {
      inv.setStatus(InvitationStatus.EXPIRED.getCode());
      invRepository.update(inv, userId, ProgramType.ONL_HLDINVI.getCode());
      throw new ResourceNotFoundException("invitation.expired");
    }

    UserModel user = userRepository.findById(userId).orElse(null);
    if (Objects.isNull(user)) {
      throw new ResourceNotFoundException("user.notFound");
    }

    // メンバー追加
    HouseholdMemberModel member = memberRepository.findById(inv.getHouseholdId(), userId);
    if (member == null
        || !StringUtils.equals(member.getStatus(), HouseholdMemberStatus.ACTIVE.getCode())) {
      memberService.createMember(inv.getHouseholdId(), userId, user.getDisplayName(), userId);
    } else {
      // 既にActiveで存在するため
      throw new IllegalStateException("already registered");
    }

    // Invitation更新
    inv.setStatus(InvitationStatus.ACCEPTED.getCode());
    inv.setAcceptedUserId(userId);
    invRepository.update(inv, userId, ProgramType.ONL_HLDINVI.getCode());

    // 通知
    notificationPublisher.publishAcceptInvitation(
        inv.getHouseholdId(), userId, inv.getInviterUserId(), ProgramType.ONL_HLDINVI.getCode());
  }

  @Transactional
  public void declineInvitation(String token, Long userId) {
    HouseholdInvitationModel inv = invRepository.selectByToken(token);
    if (Objects.isNull(inv)) {
      throw new ResourceNotFoundException("invitation.notFound");
    }

    if (inv.isTerminal()) {
      // 既に対応済みの場合何もしない
      return;
    }

    if (inv.isExpired()) {
      inv.setStatus(InvitationStatus.EXPIRED.getCode());
    } else {
      inv.setStatus(InvitationStatus.DECLINED.getCode());
    }
    inv.setAcceptedUserId(userId);
    invRepository.update(inv, userId, ProgramType.ONL_HLDINVI.getCode());

    // 通知
    notificationPublisher.publishDeclineInvitation(
        inv.getHouseholdId(), userId, inv.getInviterUserId(), ProgramType.ONL_HLDINVI.getCode());
  }

  @Transactional
  public void revokeInvitation(String token, Long userId) {
    HouseholdInvitationModel inv = invRepository.selectByToken(token);
    if (Objects.isNull(inv)) {
      throw new ResourceNotFoundException("invitation.notFound");
    }

    if (inv.isTerminal()) {
      // 既に対応済みの場合何もしない
      return;
    }

    inv.setStatus(InvitationStatus.REVOKED.getCode());
    invRepository.update(inv, userId, ProgramType.ONL_HLDINVI.getCode());
  }

  @Transactional
  public HouseholdInvitationModel createInvitation(Long householdId, String email, Long userId) {
    // 認可チェック
    if (!authorizationService.canAccessHousehold(householdId, userId)) {
      throw new AccessDeniedException(
          "User does not belong to household: userId=" + userId + ", householdId=" + householdId);
    }

    // 既存のinvitation取得しかぶっていないか
    long count = invRepository.countByActiveEmail(householdId, email);
    if (count > 0) {
      throw new IllegalStateException("invitation.alreadyExists");
    }

    HouseholdInvitationModel inv = HouseholdInvitationModel.create(householdId, userId, email);
    return invRepository.insert(inv, userId, ProgramType.ONL_HLDINVI.getCode());
  }
}
