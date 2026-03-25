package com.hwhub.backend.domain.model.inquiry;

import java.time.LocalDate;

/** 管理者向け問い合わせ検索条件。 */
public record AdminInquirySearchCondition(
    LocalDate createdAtFrom,
    LocalDate createdAtTo,
    LocalDate updatedAtFrom,
    LocalDate updatedAtTo,
    Long userId,
    String category,
    String status) {}
