package com.hwhub.backend.presentation.rest.inquiry.dto;

import java.time.LocalDateTime;

public record InquirySummary(
    long inquiryId, String category, String status, String title, LocalDateTime createdAt) {

  public static InquirySummary from(com.hwhub.backend.domain.model.inquiry.InquirySummary summary) {
    return new InquirySummary(
        summary.inquiryId().value(),
        summary.category().getCode(),
        summary.status().getCode(),
        summary.title(),
        summary.createdAt());
  }
}
