package com.hwhub.backend.presentation.rest.household.dto;

import jakarta.validation.constraints.Email;

public record CreateInvitationRequest(@Email String invitedEmail) {}
