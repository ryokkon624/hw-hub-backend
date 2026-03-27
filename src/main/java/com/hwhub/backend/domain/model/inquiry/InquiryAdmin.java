package com.hwhub.backend.domain.model.inquiry;

import java.time.LocalDateTime;

/** 管理者向けの問い合わせ一覧表示用の読み取り専用 Value Object */
public record InquiryAdmin(
    Long inquiryId,
    Long userId,
    String userEmail,
    String userDisplayName,
    String category,
    String status,
    String title,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    int totalMessageCount,
    int userMessageCount,
    int aiMessageCount,
    int staffMessageCount) {}
