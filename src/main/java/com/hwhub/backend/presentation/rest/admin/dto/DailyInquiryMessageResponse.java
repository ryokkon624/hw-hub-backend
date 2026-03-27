package com.hwhub.backend.presentation.rest.admin.dto;

import com.hwhub.backend.domain.model.inquiry.DailyInquiryMessage;

public record DailyInquiryMessageResponse(String date, int user, int ai, int staff) {

  public static DailyInquiryMessageResponse from(DailyInquiryMessage row) {
    return new DailyInquiryMessageResponse(
        row.date().toString(), row.user(), row.ai(), row.staff());
  }
}
