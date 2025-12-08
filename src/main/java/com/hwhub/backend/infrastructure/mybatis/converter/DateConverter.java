package com.hwhub.backend.infrastructure.mybatis.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * java.util.Date と LocalDate/LocalDateTime の変換ユーティリティ。 Repository層（MyBatis 生成エンティティとの詰め替え）で使用する。
 */
public final class DateConverter {

  private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

  private DateConverter() {}

  /** Date → LocalDate */
  public static LocalDate toLocalDate(Date date) {
    if (date == null) return null;
    return date.toInstant().atZone(DEFAULT_ZONE).toLocalDate();
  }

  /** Date → LocalDateTime */
  public static LocalDateTime toLocalDateTime(Date date) {
    if (date == null) return null;
    return date.toInstant().atZone(DEFAULT_ZONE).toLocalDateTime();
  }

  /** LocalDate → Date */
  public static Date toDate(LocalDate localDate) {
    if (localDate == null) return null;
    return Date.from(localDate.atStartOfDay(DEFAULT_ZONE).toInstant());
  }

  /** LocalDateTime → Date */
  public static Date toDate(LocalDateTime localDateTime) {
    if (localDateTime == null) return null;
    return Date.from(localDateTime.atZone(DEFAULT_ZONE).toInstant());
  }
}
