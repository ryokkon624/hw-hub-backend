package com.hwhub.backend.presentation.rest.auth.dto;

import com.hwhub.backend.validation.annotation.ByteSize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    @NotBlank @Email String email,
    @NotBlank String password,
    @NotBlank @ByteSize(max = 100) String displayName,
    @NotBlank String locale,
    String invitationToken) {}
