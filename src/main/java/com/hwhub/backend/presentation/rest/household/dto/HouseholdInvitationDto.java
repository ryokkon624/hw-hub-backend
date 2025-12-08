package com.hwhub.backend.presentation.rest.household.dto;

import java.time.LocalDateTime;

public record HouseholdInvitationDto(
    Long householdId,
    String invitationToken,
    String invitedEmail,
    String status,
    LocalDateTime expiresAt,
    Long acceptedUserId,
    String acceptedUserName,
    Long inviterUserId,
    String inviterDisplayName,
    LocalDateTime createdAt) {}
