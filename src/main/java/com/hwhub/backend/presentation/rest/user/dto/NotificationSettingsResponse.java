package com.hwhub.backend.presentation.rest.user.dto;

import java.util.Map;

public record NotificationSettingsResponse(
    boolean notificationEnabled, Map<String, Boolean> groupSettings) {}
