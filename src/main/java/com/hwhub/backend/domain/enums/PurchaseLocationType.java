package com.hwhub.backend.domain.enums;

public enum PurchaseLocationType implements CodeEnum {
  SUPERMARKET("1"),
  ONLINE("2"),
  DRUGSTORE("3");

  private final String code;

  PurchaseLocationType(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static PurchaseLocationType fromCode(String code) {
    for (PurchaseLocationType v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid PurchaseLocationType code: " + code);
  }
}
