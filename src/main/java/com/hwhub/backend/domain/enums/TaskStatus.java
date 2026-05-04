package com.hwhub.backend.domain.enums;

public enum TaskStatus implements CodeEnum {
  NOT_DONE("0"),
  DONE("1"),
  SKIPPED("9");

  private final String code;

  TaskStatus(String code) {
    this.code = code;
  }

  @Override
  public String getCode() {
    return code;
  }

  public static TaskStatus fromCode(String code) {
    for (TaskStatus v : values()) {
      if (v.code.equals(code)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Invalid TaskStatus code: " + code);
  }
}
