package com.hwhub.backend.domain.model.inquiry;

import java.time.LocalDate;

/** 日別・送信者タイプ別のメッセージ件数集計結果 Entity */
public record DailyInquiryMessage(LocalDate date, int user, int ai, int staff) {}
