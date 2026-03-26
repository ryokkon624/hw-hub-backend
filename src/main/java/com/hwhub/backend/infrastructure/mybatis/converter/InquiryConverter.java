package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.enums.InquiryCategory;
import com.hwhub.backend.domain.enums.InquiryStatus;
import com.hwhub.backend.domain.model.inquiry.InquiryId;
import com.hwhub.backend.domain.model.inquiry.InquiryMessageModel;
import com.hwhub.backend.domain.model.inquiry.InquiryModel;
import com.hwhub.backend.domain.model.inquiry.InquirySummary;
import com.hwhub.backend.domain.model.inquiry.InruiryAdmin;
import com.hwhub.backend.infrastructure.mybatis.custom.entity.AdminInquiryEntity;
import com.hwhub.backend.infrastructure.mybatis.custom.entity.InquiryWithMessagesEntity;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiry;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiryMessage;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public final class InquiryConverter {

  public static InquiryModel toModel(InquiryWithMessagesEntity entity) {
    if (entity == null) {
      return null;
    }
    List<InquiryMessageModel> messageModels =
        entity.getMessages() == null
            ? Collections.emptyList()
            : entity.getMessages().stream().map(InquiryConverter::toMessageModel).toList();
    return InquiryModel.reconstruct(
        entity.getInquiryId(),
        entity.getUserId(),
        entity.getCategory(),
        entity.getStatus(),
        entity.getTitle(),
        messageModels,
        DateConverter.toLocalDateTime(entity.getCreatedAt()));
  }

  public static InquirySummary toSummary(TInquiry entity) {
    if (entity == null) {
      return null;
    }
    return new InquirySummary(
        new InquiryId(entity.getInquiryId()),
        InquiryCategory.fromCode(entity.getCategory()),
        InquiryStatus.fromCode(entity.getStatus()),
        entity.getTitle(),
        DateConverter.toLocalDateTime(entity.getCreatedAt()));
  }

  public static InruiryAdmin toModel4Admin(AdminInquiryEntity entity) {
    if (entity == null) {
      return null;
    }
    return new InruiryAdmin(
        entity.getInquiryId(),
        entity.getUserId(),
        entity.getUserEmail(),
        entity.getUserDisplayName(),
        entity.getCategory(),
        entity.getStatus(),
        entity.getTitle(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getTotalMessageCount(),
        entity.getUserMessageCount(),
        entity.getAiMessageCount(),
        entity.getStaffMessageCount());
  }

  public static TInquiry toEntity(InquiryModel inquiry) {
    if (inquiry == null) {
      return null;
    }
    TInquiry entity = new TInquiry();
    if (inquiry.getInquiryId() != null) {
      entity.setInquiryId(inquiry.getInquiryId().value());
    }
    entity.setUserId(inquiry.getUserId());
    entity.setCategory(inquiry.getCategory().getCode());
    entity.setStatus(inquiry.getStatus().getCode());
    entity.setTitle(inquiry.getTitle());
    return entity;
  }

  public static InquiryMessageModel toMessageModel(TInquiryMessage entity) {
    if (entity == null) {
      return null;
    }
    return InquiryMessageModel.reconstruct(
        entity.getMessageId(),
        entity.getInquiryId(),
        entity.getSeq(),
        entity.getSenderType(),
        entity.getBody(),
        DateConverter.toLocalDateTime(entity.getCreatedAt()));
  }

  public static TInquiryMessage toMessageEntity(InquiryMessageModel message) {
    if (message == null) {
      return null;
    }
    TInquiryMessage entity = new TInquiryMessage();
    if (message.getMessageId() != null) {
      entity.setMessageId(message.getMessageId().value());
    }
    if (message.getInquiryId() != null) {
      entity.setInquiryId(message.getInquiryId().value());
    }
    entity.setSeq(message.getSeq());
    entity.setSenderType(message.getSenderType().getCode());
    entity.setBody(message.getBody());
    return entity;
  }
}
