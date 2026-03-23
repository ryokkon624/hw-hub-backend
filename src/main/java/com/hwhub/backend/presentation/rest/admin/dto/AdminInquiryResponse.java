package com.hwhub.backend.presentation.rest.admin.dto;

import com.hwhub.backend.domain.model.inquiry.InruiryAdmin;
import java.time.LocalDateTime;

public record AdminInquiryResponse(
    long inquiryId,
    long userId,
    String userEmail,
    String userDisplayName,
    String category,
    String status,
    String title,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    int totalMessageCount,
    int userMessageCount,
    int aiMessageCount,
    int staffMessageCount) {

  public static AdminInquiryResponse from(InruiryAdmin model) {
    return new AdminInquiryResponse(
        model.inquiryId(),
        model.userId(),
        model.userEmail(),
        model.userDisplayName(),
        model.category(),
        model.status(),
        model.title(),
        model.createdAt(),
        model.updatedAt(),
        model.totalMessageCount(),
        model.userMessageCount(),
        model.aiMessageCount(),
        model.staffMessageCount());
  }
}
