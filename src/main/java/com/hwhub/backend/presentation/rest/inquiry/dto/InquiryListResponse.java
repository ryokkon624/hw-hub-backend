package com.hwhub.backend.presentation.rest.inquiry.dto;

import java.util.List;

public record InquiryListResponse(List<InquirySummary> items) {

  public static InquiryListResponse from(
      List<com.hwhub.backend.domain.model.inquiry.InquirySummary> summaries) {
    return new InquiryListResponse(summaries.stream().map(InquirySummary::from).toList());
  }
}
