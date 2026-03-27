package com.hwhub.backend.domain.model.inquiry;

/** 問い合わせステータス別件数の集計結果 Entity */
public record InquiryStatusSummary(
    int open, int aiAnswered, int pendingStaff, int staffAnswered, int recentUnclosed) {}
