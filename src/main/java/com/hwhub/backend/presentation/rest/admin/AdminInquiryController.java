package com.hwhub.backend.presentation.rest.admin;

import com.hwhub.backend.application.service.AdminInquiryService;
import com.hwhub.backend.domain.enums.Permission;
import com.hwhub.backend.domain.model.inquiry.AdminInquirySearchCondition;
import com.hwhub.backend.domain.model.inquiry.InquiryModel;
import com.hwhub.backend.presentation.rest.admin.dto.AdminInquiryReplyRequest;
import com.hwhub.backend.presentation.rest.admin.dto.AdminInquiryResponse;
import com.hwhub.backend.presentation.rest.inquiry.dto.InquiryDetailResponse;
import com.hwhub.backend.security.RequiresPermission;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/inquiries")
public class AdminInquiryController {

  private final AdminInquiryService adminInquiryService;

  /** 対応待ち一覧 */
  @RequiresPermission(Permission.INQUIRY_REPLY)
  @GetMapping
  public List<AdminInquiryResponse> getPendingStaff() {
    return adminInquiryService.findPendingStaff().stream().map(AdminInquiryResponse::from).toList();
  }

  /** 全件検索 */
  @RequiresPermission(Permission.INQUIRY_REPLY)
  @GetMapping("/search")
  public List<AdminInquiryResponse> search(
      @RequestParam(name = "createdAtFrom", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate createdAtFrom,
      @RequestParam(name = "createdAtTo", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate createdAtTo,
      @RequestParam(name = "updatedAtFrom", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate updatedAtFrom,
      @RequestParam(name = "updatedAtTo", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate updatedAtTo,
      @RequestParam(name = "userId", required = false) Long userId,
      @RequestParam(name = "category", required = false) String category,
      @RequestParam(name = "status", required = false) String status) {

    AdminInquirySearchCondition condition =
        new AdminInquirySearchCondition(
            createdAtFrom, createdAtTo, updatedAtFrom, updatedAtTo, userId, category, status);

    return adminInquiryService.searchInquiries(condition).stream()
        .map(AdminInquiryResponse::from)
        .toList();
  }

  /** 詳細取得（管理者用・userId チェックなし） */
  @RequiresPermission(Permission.INQUIRY_REPLY)
  @GetMapping("/{inquiryId}")
  public InquiryDetailResponse getDetail(@PathVariable("inquiryId") Long inquiryId) {
    InquiryModel inquiry = adminInquiryService.getInquiryAsAdmin(inquiryId);
    return InquiryDetailResponse.from(inquiry);
  }

  /** スタッフ返信 */
  @RequiresPermission(Permission.INQUIRY_REPLY)
  @PostMapping("/{inquiryId}/reply")
  public void reply(
      @PathVariable("inquiryId") Long inquiryId,
      @RequestBody @Valid AdminInquiryReplyRequest request,
      Authentication authentication) {
    Long operatorUserId = Long.valueOf(authentication.getName());
    adminInquiryService.replyAsStaff(inquiryId, request.body(), operatorUserId);
  }
}
