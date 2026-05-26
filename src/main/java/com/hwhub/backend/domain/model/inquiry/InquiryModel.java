package com.hwhub.backend.domain.model.inquiry;

import com.hwhub.backend.domain.enums.InquiryCategory;
import com.hwhub.backend.domain.enums.InquiryStatus;
import com.hwhub.backend.domain.enums.SenderType;
import com.hwhub.backend.domain.enums.UiClient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class InquiryModel {
  private final InquiryId inquiryId;
  private final long userId;
  private final InquiryCategory category;
  private InquiryStatus status;
  private final String title;
  private final List<InquiryMessageModel> messages;
  private final LocalDateTime createdAt;
  private final UiClient uiClient;
  private final String uiVersion;
  private final String apiVersion;

  private InquiryModel(
      InquiryId inquiryId,
      long userId,
      InquiryCategory category,
      InquiryStatus status,
      String title,
      List<InquiryMessageModel> messages,
      LocalDateTime createdAt,
      UiClient uiClient,
      String uiVersion,
      String apiVersion) {
    this.inquiryId = inquiryId;
    this.userId = userId;
    this.category = category;
    this.status = status;
    this.title = title;
    this.messages = messages;
    this.createdAt = createdAt;
    this.uiClient = uiClient;
    this.uiVersion = uiVersion;
    this.apiVersion = apiVersion;
  }

  public static InquiryModel newInquiry(
      long userId,
      InquiryCategory category,
      String title,
      String firstMessageBody,
      UiClient uiClient,
      String uiVersion,
      String apiVersion) {
    List<InquiryMessageModel> messages = new ArrayList<>();
    messages.add(InquiryMessageModel.newMessage(null, 1, SenderType.YOU, firstMessageBody));
    return new InquiryModel(
        null,
        userId,
        category,
        InquiryStatus.OPEN,
        title,
        messages,
        null,
        uiClient,
        uiVersion,
        apiVersion);
  }

  public static InquiryModel reconstruct(
      Long inquiryId,
      long userId,
      String category,
      String status,
      String title,
      List<InquiryMessageModel> messages,
      LocalDateTime createdAt,
      String uiClient,
      String uiVersion,
      String apiVersion) {
    return new InquiryModel(
        new InquiryId(inquiryId),
        userId,
        InquiryCategory.fromCode(category),
        InquiryStatus.fromCode(status),
        title,
        new ArrayList<>(messages),
        createdAt,
        UiClient.fromCode(uiClient),
        uiVersion,
        apiVersion);
  }

  public InquiryMessageModel addMessage(String body, SenderType senderType) {
    return InquiryMessageModel.newMessage(this.inquiryId, messages.size() + 1, senderType, body);
  }

  public void close() {
    if (this.status == InquiryStatus.CLOSED) {
      throw new IllegalStateException("Inquiry is already closed");
    }
    this.status = InquiryStatus.CLOSED;
  }

  public void escalateToStaff() {
    if (this.status != InquiryStatus.AI_ANSWERED) {
      throw new IllegalStateException(
          "Escalation to staff is only allowed from AI_ANSWERED status");
    }
    this.status = InquiryStatus.PENDING_STAFF;
  }

  public void applyAiAnswer(String body) {
    this.status = InquiryStatus.AI_ANSWERED;
    InquiryMessageModel aiMessage =
        InquiryMessageModel.newMessage(
            this.inquiryId, messages.size() + 1, SenderType.AI_SUPPORT, body);
    this.messages.add(aiMessage);
  }
}
