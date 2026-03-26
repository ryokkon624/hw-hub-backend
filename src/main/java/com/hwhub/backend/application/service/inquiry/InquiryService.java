package com.hwhub.backend.application.service.inquiry;

import com.hwhub.backend.application.service.notification.NotificationPublisher;
import com.hwhub.backend.domain.enums.InquiryCategory;
import com.hwhub.backend.domain.enums.InquiryStatus;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.enums.SenderType;
import com.hwhub.backend.domain.model.inquiry.InquiryId;
import com.hwhub.backend.domain.model.inquiry.InquiryMessageModel;
import com.hwhub.backend.domain.model.inquiry.InquiryModel;
import com.hwhub.backend.domain.model.inquiry.InquirySummary;
import com.hwhub.backend.domain.repository.InquiryRepository;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryService {

  private final InquiryRepository inquiryRepository;
  private final NotificationPublisher notificationPublisher;

  @Transactional
  public InquiryId createInquiry(Long userId, InquiryCategory category, String title, String body) {
    InquiryModel inquiry = InquiryModel.newInquiry(userId, category, title, body);
    return inquiryRepository.insert(inquiry, userId, ProgramType.ONL_INQRY.getCode());
  }

  @Transactional(readOnly = true)
  public List<InquirySummary> getInquiries(Long userId) {
    return inquiryRepository.findSummariesByUserId(userId);
  }

  @Transactional(readOnly = true)
  public InquiryModel getInquiry(InquiryId inquiryId, Long userId) {
    InquiryModel inquiry =
        inquiryRepository
            .findById(inquiryId)
            .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found"));
    if (inquiry.getUserId() != userId) {
      throw new ResourceNotFoundException("Inquiry not found");
    }
    return inquiry;
  }

  @Transactional
  public void addMessage(InquiryId inquiryId, Long userId, String body, SenderType senderType) {
    addMessage(inquiryId, userId, body, senderType, ProgramType.ONL_INQRY);
  }

  @Transactional
  public void addMessage(
      InquiryId inquiryId, Long userId, String body, SenderType senderType, ProgramType program) {

    // Userからのメッセージの場合は問い合わせの起票者かチェックする
    InquiryModel inquiry;
    if (senderType == SenderType.YOU) {
      inquiry = getInquiry(inquiryId, userId);
    } else {
      inquiry =
          inquiryRepository
              .findById(inquiryId)
              .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found"));
    }

    InquiryMessageModel message = inquiry.addMessage(body, senderType);
    inquiryRepository.addMessage(message, userId, program.getCode());

    boolean doUpdateStatus =
        inquiry.getStatus() == InquiryStatus.PENDING_STAFF
            || inquiry.getStatus() == InquiryStatus.OPEN;

    if (senderType == SenderType.YOU && inquiry.getStatus() == InquiryStatus.STAFF_ANSWERED) {
      inquiryRepository.updateStatus(
          inquiryId, InquiryStatus.PENDING_STAFF, userId, program.getCode());
    } else if (senderType == SenderType.STAFF && doUpdateStatus) {
      inquiryRepository.updateStatus(
          inquiryId, InquiryStatus.STAFF_ANSWERED, userId, program.getCode());
    }

    if (senderType != SenderType.YOU) {
      notificationPublisher.publishInquiryReplied(
          inquiryId.value(), inquiry.getTitle(), inquiry.getUserId(), userId, program.getCode());
    }
  }

  @Transactional
  public void closeInquiry(InquiryId inquiryId, Long userId) {
    InquiryModel inquiry = getInquiry(inquiryId, userId);

    inquiry.close();
    inquiryRepository.updateStatus(
        inquiryId, inquiry.getStatus(), userId, ProgramType.ONL_INQRY.getCode());
  }

  @Transactional
  public void escalateToStaff(InquiryId inquiryId, Long userId) {
    InquiryModel inquiry = getInquiry(inquiryId, userId);

    inquiry.escalateToStaff();
    inquiryRepository.updateStatus(
        inquiryId, inquiry.getStatus(), userId, ProgramType.ONL_INQRY.getCode());
  }
}
