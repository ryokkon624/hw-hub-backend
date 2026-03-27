package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.enums.InquiryStatus;
import com.hwhub.backend.domain.model.inquiry.AdminInquirySearchCondition;
import com.hwhub.backend.domain.model.inquiry.DailyInquiryMessage;
import com.hwhub.backend.domain.model.inquiry.DailyInquiryStatus;
import com.hwhub.backend.domain.model.inquiry.InquiryAdmin;
import com.hwhub.backend.domain.model.inquiry.InquiryId;
import com.hwhub.backend.domain.model.inquiry.InquiryMessageId;
import com.hwhub.backend.domain.model.inquiry.InquiryMessageModel;
import com.hwhub.backend.domain.model.inquiry.InquiryModel;
import com.hwhub.backend.domain.model.inquiry.InquiryStatusSummary;
import com.hwhub.backend.domain.model.inquiry.InquirySummary;
import com.hwhub.backend.domain.repository.InquiryRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.InquiryConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.entity.InquiryWithMessagesEntity;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.InquiryCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiry;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiryMessage;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.TInquiryMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.TInquiryMessageMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisInquiryRepository implements InquiryRepository {

  private final TInquiryMapper inquiryMapper;
  private final TInquiryMessageMapper inquiryMessageMapper;
  private final InquiryCustomMapper customMapper;

  @Override
  public InquiryId insert(InquiryModel inquiry, Long operatorUserId, String program) {
    TInquiry entity = InquiryConverter.toEntity(inquiry);
    entity.setCreateUserId(operatorUserId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(operatorUserId);
    entity.setUpdateProgram(program);
    inquiryMapper.insertSelective(entity);

    Long generatedId = entity.getInquiryId();
    if (generatedId == null) {
      throw new IllegalStateException("inquiry_id was not generated");
    }

    for (InquiryMessageModel message : inquiry.getMessages()) {
      TInquiryMessage msgEntity = InquiryConverter.toMessageEntity(message);
      msgEntity.setInquiryId(generatedId);
      msgEntity.setCreateUserId(operatorUserId);
      msgEntity.setCreateProgram(program);
      msgEntity.setUpdateUserId(operatorUserId);
      msgEntity.setUpdateProgram(program);
      inquiryMessageMapper.insertSelective(msgEntity);
    }

    return new InquiryId(generatedId);
  }

  @Override
  public Optional<InquiryModel> findById(InquiryId inquiryId) {
    InquiryWithMessagesEntity entity = customMapper.findInquiryWithMessages(inquiryId.value());
    if (entity == null) {
      return Optional.empty();
    }
    return Optional.of(InquiryConverter.toModel(entity));
  }

  @Override
  public List<InquirySummary> findSummariesByUserId(Long userId) {
    return customMapper.findByUserId(userId).stream().map(InquiryConverter::toSummary).toList();
  }

  @Override
  public InquiryMessageId addMessage(
      InquiryMessageModel message, Long operatorUserId, String program) {
    TInquiryMessage entity = InquiryConverter.toMessageEntity(message);
    entity.setCreateUserId(operatorUserId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(operatorUserId);
    entity.setUpdateProgram(program);
    inquiryMessageMapper.insertSelective(entity);

    Long generatedId = entity.getMessageId();
    if (generatedId == null) {
      throw new IllegalStateException("message_id was not generated");
    }

    return new InquiryMessageId(generatedId);
  }

  @Override
  public void updateStatus(
      InquiryId inquiryId, InquiryStatus status, Long operatorUserId, String program) {
    TInquiry entity = new TInquiry();
    entity.setInquiryId(inquiryId.value());
    entity.setStatus(status.getCode());
    entity.setUpdateUserId(operatorUserId);
    entity.setUpdateProgram(program);
    inquiryMapper.updateByPrimaryKeySelective(entity);
  }

  @Override
  public List<InquiryAdmin> findPendingStaff() {
    return customMapper.findPendingStaff().stream().map(InquiryConverter::toModel4Admin).toList();
  }

  @Override
  public List<InquiryAdmin> searchInquiries(AdminInquirySearchCondition condition) {
    return customMapper.searchInquiries(condition).stream()
        .map(InquiryConverter::toModel4Admin)
        .toList();
  }

  @Override
  public List<DailyInquiryStatus> findDailyStats(int days) {
    return customMapper.findDailyStats(days);
  }

  @Override
  public List<DailyInquiryMessage> findMessageDailyStats(int days) {
    return customMapper.findMessageDailyStats(days);
  }

  @Override
  public InquiryStatusSummary findStatusSummary() {
    return customMapper.findStatusSummary();
  }
}
