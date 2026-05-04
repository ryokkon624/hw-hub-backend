package com.hwhub.backend.domain.enums;

public enum FavoriteFlag implements CodeEnum {
  NORMAL("0"),
  FAVORITE("1");

  private final String code;

  FavoriteFlag(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static FavoriteFlag fromCode(String code) {
    for (FavoriteFlag v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid FavoriteFlag code: " + code);
  }
}
