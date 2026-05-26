package com.hwhub.backend.presentation.rest.inquiry.dto;

import com.hwhub.backend.domain.model.inquiry.InquiryModel;
import java.time.LocalDateTime;
import java.util.List;

public record InquiryDetailResponse(
    long inquiryId,
    String category,
    String status,
    String title,
    LocalDateTime createdAt,
    List<InquiryMessageDto> messages,
    String uiClient,
    String uiVersion,
    String apiVersion) {

  public static InquiryDetailResponse from(InquiryModel inquiry) {
    return new InquiryDetailResponse(
        inquiry.getInquiryId().value(),
        inquiry.getCategory().getCode(),
        inquiry.getStatus().getCode(),
        inquiry.getTitle(),
        inquiry.getCreatedAt(),
        inquiry.getMessages().stream().map(InquiryMessageDto::from).toList(),
        inquiry.getUiClient().getCode(),
        inquiry.getUiVersion(),
        inquiry.getApiVersion());
  }
}
