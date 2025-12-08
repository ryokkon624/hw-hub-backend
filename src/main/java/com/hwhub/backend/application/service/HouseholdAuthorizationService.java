package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.repository.HouseholdMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseholdAuthorizationService {

  private final HouseholdMemberRepository householdMemberRepository;

  /** 指定した userId が householdId に属していなければ例外を投げる. */
  public void assertUserBelongsToHousehold(Long householdId, Long userId) {
    boolean isMember = householdMemberRepository.existsActiveByHouseholdIdAndUserId(householdId, userId);
    if (!isMember) {
      throw new AccessDeniedException(
          "User does not belong to household: userId=" + userId + ", householdId=" + householdId);
    }
  }

  /** アクセス可であるかを返す。 */
  public boolean canAccessHousehold(Long householdId, Long userId) {
    return householdMemberRepository.existsActiveByHouseholdIdAndUserId(householdId, userId);
  }
}
