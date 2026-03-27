package com.hwhub.backend.presentation.rest.admin.dto;

import com.hwhub.backend.domain.model.inquiry.DailyInquiryStatus;

public record DailyInquiryStatusResponse(
    String date, int open, int aiAnswered, int pendingStaff, int staffAnswered, int closed) {

  public static DailyInquiryStatusResponse from(DailyInquiryStatus row) {
    return new DailyInquiryStatusResponse(
        row.date().toString(),
        row.open(),
        row.aiAnswered(),
        row.pendingStaff(),
        row.staffAnswered(),
        row.closed());
  }
}
