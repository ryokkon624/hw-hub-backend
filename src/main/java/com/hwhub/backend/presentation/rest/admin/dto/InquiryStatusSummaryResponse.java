package com.hwhub.backend.presentation.rest.admin.dto;

import com.hwhub.backend.domain.model.inquiry.InquiryStatusSummary;

public record InquiryStatusSummaryResponse(
    int open, int aiAnswered, int pendingStaff, int staffAnswered, int recentUnclosed) {

  public static InquiryStatusSummaryResponse from(InquiryStatusSummary row) {
    return new InquiryStatusSummaryResponse(
        row.open(),
        row.aiAnswered(),
        row.pendingStaff(),
        row.staffAnswered(),
        row.recentUnclosed());
  }
}
