package com.hwhub.backend.domain.model.inquiry;

import com.hwhub.backend.domain.enums.SenderType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class InquiryMessageModel {
  private final InquiryMessageId messageId;
  private final InquiryId inquiryId;
  private final int seq;
  private final SenderType senderType;
  private final String body;
  private final LocalDateTime createdAt;

  private InquiryMessageModel(
      InquiryMessageId messageId,
      InquiryId inquiryId,
      int seq,
      SenderType senderType,
      String body,
      LocalDateTime createdAt) {
    this.messageId = messageId;
    this.inquiryId = inquiryId;
    this.seq = seq;
    this.senderType = senderType;
    this.body = body;
    this.createdAt = createdAt;
  }

  public static InquiryMessageModel newMessage(
      InquiryId inquiryId, int seq, SenderType senderType, String body) {
    return new InquiryMessageModel(null, inquiryId, seq, senderType, body, null);
  }

  public static InquiryMessageModel reconstruct(
      Long messageId,
      Long inquiryId,
      int seq,
      String senderType,
      String body,
      LocalDateTime createdAt) {
    return new InquiryMessageModel(
        new InquiryMessageId(messageId),
        new InquiryId(inquiryId),
        seq,
        SenderType.fromCode(senderType),
        body,
        createdAt);
  }
}
