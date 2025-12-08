package com.hwhub.backend.presentation.rest.invitation.dto;

import com.hwhub.backend.domain.model.HouseholdInvitationModel;

public record InvitationResponce(
    Long householdId,
    String householdName,
    String inviterDisplayName,
    String invitedEmail,
    String status,
    boolean expired) {

  public static InvitationResponce fromModel(HouseholdInvitationModel model) {
    return new InvitationResponce(
        model.getHouseholdId(),
        model.getHouseholdName(),
        model.getInviterName(),
        model.getInvitedEmail(),
        model.getStatus(),
        model.isExpired());
  }
}
