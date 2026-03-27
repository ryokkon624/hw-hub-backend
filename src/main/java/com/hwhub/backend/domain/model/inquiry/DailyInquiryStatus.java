package com.hwhub.backend.domain.model.inquiry;

import java.time.LocalDate;

/** 日別・ステータス別の問い合わせ件数集計結果 Entity */
public record DailyInquiryStatus(
    LocalDate date, int open, int aiAnswered, int pendingStaff, int staffAnswered, int closed) {}
