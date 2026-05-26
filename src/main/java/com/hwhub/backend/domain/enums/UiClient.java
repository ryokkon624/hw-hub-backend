package com.hwhub.backend.domain.enums;

public enum UiClient implements CodeEnum {
  WEB("web"),
  MOBILE("mobile");

  private final String code;

  UiClient(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static UiClient fromCode(String code) {
    for (UiClient v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid UiClient code: " + code);
  }
}
