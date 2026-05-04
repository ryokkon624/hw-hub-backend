package com.hwhub.backend.domain.enums;

public enum TaskRecalcStatus implements CodeEnum {
    PENDING("0"),
    PROCESSING("1"),
    DONE("2"),
    FAILED("9");

    private final String code;

    TaskRecalcStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static TaskRecalcStatus fromCode(String code) {
        for (TaskRecalcStatus v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid TaskRecalcStatus code: " + code);
    }
}
