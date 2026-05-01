package com.hwhub.backend.presentation.rest.inquiry;

import com.hwhub.backend.application.service.inquiry.InquiryService;
import com.hwhub.backend.domain.enums.InquiryCategory;
import com.hwhub.backend.domain.enums.SenderType;
import com.hwhub.backend.domain.model.inquiry.InquiryId;
import com.hwhub.backend.domain.model.inquiry.InquiryModel;
import com.hwhub.backend.domain.model.inquiry.InquirySummary;
import com.hwhub.backend.presentation.rest.inquiry.dto.InquiryCreateRequest;
import com.hwhub.backend.presentation.rest.inquiry.dto.InquiryDetailResponse;
import com.hwhub.backend.presentation.rest.inquiry.dto.InquiryListResponse;
import com.hwhub.backend.presentation.rest.inquiry.dto.InquiryMessageRequest;
import com.hwhub.backend.security.CurrentUserId;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inquiries")
public class InquiryController {

  private final InquiryService inquiryService;

  @PostMapping
  public Map<String, Object> createInquiry(
      @RequestBody @Valid InquiryCreateRequest request, @CurrentUserId Long userId) {
    InquiryCategory category = InquiryCategory.fromCode(request.category());
    InquiryId inquiryId =
        inquiryService.createInquiry(userId, category, request.title(), request.body());
    return Map.of("inquiryId", inquiryId.value());
  }

  @GetMapping
  public InquiryListResponse getInquiries(@CurrentUserId Long userId) {
    List<InquirySummary> summaries = inquiryService.getInquiries(userId);
    return InquiryListResponse.from(summaries);
  }

  @GetMapping("/{inquiryId}")
  public InquiryDetailResponse getInquiry(
      @PathVariable("inquiryId") Long inquiryId, @CurrentUserId Long userId) {
    InquiryModel inquiry = inquiryService.getInquiry(new InquiryId(inquiryId), userId);
    return InquiryDetailResponse.from(inquiry);
  }

  @PostMapping("/{inquiryId}/messages")
  public void addMessage(
      @PathVariable("inquiryId") Long inquiryId,
      @RequestBody @Valid InquiryMessageRequest request,
      @CurrentUserId Long userId) {
    inquiryService.addMessage(new InquiryId(inquiryId), userId, request.body(), SenderType.YOU);
  }

  @PostMapping("/{inquiryId}/close")
  public void closeInquiry(@PathVariable("inquiryId") Long inquiryId, @CurrentUserId Long userId) {
    inquiryService.closeInquiry(new InquiryId(inquiryId), userId);
  }

  @PostMapping("/{inquiryId}/escalate")
  public void escalateToStaff(
      @PathVariable("inquiryId") Long inquiryId, @CurrentUserId Long userId) {
    inquiryService.escalateToStaff(new InquiryId(inquiryId), userId);
  }
}
