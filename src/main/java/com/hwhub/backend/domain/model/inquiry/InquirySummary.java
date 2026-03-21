package com.hwhub.backend.domain.model.inquiry;

import com.hwhub.backend.domain.enums.InquiryCategory;
import com.hwhub.backend.domain.enums.InquiryStatus;
import java.time.LocalDateTime;

/** 問い合わせ一覧表示用の読み取り専用 Value Object */
public record InquirySummary(
    InquiryId inquiryId,
    InquiryCategory category,
    InquiryStatus status,
    String title,
    LocalDateTime createdAt) {}
