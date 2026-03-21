package com.hwhub.backend.presentation.rest.inquiry.dto;

import com.hwhub.backend.domain.model.inquiry.InquiryMessageModel;
import java.time.LocalDateTime;

public record InquiryMessageDto(
    long messageId, int seq, String senderType, String body, LocalDateTime createdAt) {

  public static InquiryMessageDto from(InquiryMessageModel message) {
    return new InquiryMessageDto(
        message.getMessageId().value(),
        message.getSeq(),
        message.getSenderType().getCode(),
        message.getBody(),
        message.getCreatedAt());
  }
}
