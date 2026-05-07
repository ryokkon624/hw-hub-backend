package com.hwhub.backend.presentation.rest.auth.dto;

public record RegisterResponse(
    boolean emailVerificationRequired,
    String accessToken,
    String refreshToken,
    LoginUserDto user,
    String verificationExpiresAt) {}
