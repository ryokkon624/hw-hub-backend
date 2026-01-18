package com.hwhub.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hwhub.auth.email-verification")
public record EmailVerificationProperties(
    boolean enabled,
    boolean sendMail,
    int tokenTtlMinutes,
    int resendCooldownSeconds,
    int maxRequestsPerDay,
    String frontBaseUrl,
    String verifyPath) {}
