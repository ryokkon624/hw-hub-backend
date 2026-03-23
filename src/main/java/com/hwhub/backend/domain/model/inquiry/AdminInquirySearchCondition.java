package com.hwhub.backend.domain.model.inquiry;

import java.time.LocalDate;
import lombok.Data;

/** 管理者向け問い合わせ検索条件。 */
@Data
public class AdminInquirySearchCondition {
  private LocalDate createdAtFrom;
  private LocalDate createdAtTo;
  private LocalDate updatedAtFrom;
  private LocalDate updatedAtTo;
  private Long userId;
  private String category;
  private String status;
}
