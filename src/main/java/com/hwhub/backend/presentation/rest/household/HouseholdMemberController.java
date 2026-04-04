package com.hwhub.backend.presentation.rest.household;

import com.hwhub.backend.application.service.HouseholdAuthorizationService;
import com.hwhub.backend.application.service.HouseholdInvitationService;
import com.hwhub.backend.application.service.HouseholdMemberService;
import com.hwhub.backend.application.service.HouseholdService;
import com.hwhub.backend.domain.model.HouseholdInvitationModel;
import com.hwhub.backend.domain.model.HouseholdMemberModel;
import com.hwhub.backend.presentation.rest.household.dto.CreateInvitationRequest;
import com.hwhub.backend.presentation.rest.household.dto.HouseholdInvitationDto;
import com.hwhub.backend.presentation.rest.household.dto.HouseholdMemberDto;
import com.hwhub.backend.presentation.rest.household.dto.UpdateHouseholdRequest;
import com.hwhub.backend.presentation.rest.household.dto.UpdateMyNicknameRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/households")
@RequiredArgsConstructor
public class HouseholdMemberController {

  private final HouseholdService householdService;
  private final HouseholdMemberService memberService;
  private final HouseholdAuthorizationService authService;
  private final HouseholdInvitationService invService;

  @GetMapping("/{householdId}/members")
  public List<HouseholdMemberDto> getMembers(
      @PathVariable("householdId") Long householdId, Authentication authentication) {
    Long loginUserId = Long.valueOf(authentication.getName());

    // 認可チェック
    authService.assertUserBelongsToHousehold(householdId, loginUserId);

    List<HouseholdMemberModel> members = memberService.getMembers(householdId);

    return members.stream().map(HouseholdMemberDto::fromModel).toList();
  }

  @PutMapping("/{householdId}/members/me/nickname")
  public ResponseEntity<Void> updateMyNickname(
      @PathVariable("householdId") Long householdId,
      @Valid @RequestBody UpdateMyNicknameRequest request,
      Authentication authentication) {
    Long loginUserId = Long.valueOf(authentication.getName());

    memberService.updateMyNickname(householdId, loginUserId, request.nickname());

    // 特に返すものがなないため、 204 No Content
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{householdId}")
  public ResponseEntity<Void> updateHouseholdName(
      @PathVariable("householdId") Long householdId,
      @Valid @RequestBody UpdateHouseholdRequest request,
      Authentication authentication) {
    Long userId = Long.valueOf(authentication.getName());

    householdService.updateHouseholdName(householdId, userId, request.name());

    // 特に返すものがなないため、 204 No Content
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{householdId}/invitations")
  public List<HouseholdInvitationDto> getInvitations(
      @PathVariable("householdId") Long householdId, Authentication authentication) {

    Long userId = Long.valueOf(authentication.getName());

    List<HouseholdInvitationModel> list = invService.getInvitations(householdId, userId);

    return list.stream()
        .map(
            e ->
                new HouseholdInvitationDto(
                    e.getHouseholdId(),
                    e.getInvitationToken(),
                    e.getInvitedEmail(),
                    e.getStatus(),
                    e.getExpiresAt(),
                    e.getAcceptedUserId(),
                    e.getAcceptedUserName(),
                    e.getInviterUserId(),
                    e.getInviterName(),
                    e.getCreatedAt()))
        .toList();
  }

  @PostMapping("/{householdId}/invitations")
  public HouseholdInvitationDto createInvitation(
      @PathVariable("householdId") Long householdId,
      @Valid @RequestBody CreateInvitationRequest request,
      Authentication authentication) {

    Long userId = Long.valueOf(authentication.getName());

    HouseholdInvitationModel model =
        invService.createInvitation(householdId, request.invitedEmail(), userId);

    return new HouseholdInvitationDto(
        model.getHouseholdId(),
        model.getInvitationToken(),
        model.getInvitedEmail(),
        model.getStatus(),
        model.getExpiresAt(),
        model.getAcceptedUserId(),
        model.getAcceptedUserName(),
        model.getInviterUserId(),
        model.getInviterName(),
        model.getCreatedAt());
  }

  @DeleteMapping("/{householdId}/members/me")
  public ResponseEntity<Void> deleteMyself(
      @PathVariable("householdId") Long householdId, Authentication authentication) {
    Long userId = Long.valueOf(authentication.getName());

    memberService.deleteMyself(householdId, userId);

    // 特に返すものがなないため、 204 No Content
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{householdId}/members/{userId}")
  public ResponseEntity<Void> deleteMember(
      @PathVariable("householdId") Long householdId,
      @PathVariable("userId") Long userId,
      Authentication authentication) {
    Long loginUserId = Long.valueOf(authentication.getName());

    memberService.deleteMember(householdId, userId, loginUserId);

    return ResponseEntity.noContent().build();
  }
}
