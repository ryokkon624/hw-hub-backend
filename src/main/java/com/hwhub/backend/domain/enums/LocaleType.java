package com.hwhub.backend.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocaleType implements CodeEnum {
  JA("ja"),
  EN("en"),
  ES("es");

  private final String code;

  @Override
  public String getCode() {
    return code;
  }

  public static LocaleType fromCode(String code) {
    for (LocaleType v : values()) {
      if (v.code.equals(code)) return v;
    }
    throw new IllegalArgumentException("Unknown locale code: " + code);
  }
}
