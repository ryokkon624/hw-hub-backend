package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.enums.InquiryStatus;
import com.hwhub.backend.domain.model.inquiry.InquiryId;
import com.hwhub.backend.domain.model.inquiry.InquiryMessageId;
import com.hwhub.backend.domain.model.inquiry.InquiryMessageModel;
import com.hwhub.backend.domain.model.inquiry.InquiryModel;
import com.hwhub.backend.domain.model.inquiry.InquirySummary;
import java.util.List;
import java.util.Optional;

public interface InquiryRepository {

  InquiryId insert(InquiryModel inquiry, Long operatorUserId, String program);

  Optional<InquiryModel> findById(InquiryId inquiryId);

  List<InquirySummary> findSummariesByUserId(Long userId);

  InquiryMessageId addMessage(InquiryMessageModel message, Long operatorUserId, String program);

  void updateStatus(InquiryId inquiryId, InquiryStatus status, Long operatorUserId, String program);
}
