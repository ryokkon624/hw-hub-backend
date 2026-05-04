package com.hwhub.backend.domain.enums;

public enum EventStatus implements CodeEnum {
    PENDING("0"),
    PROCESSING("1"),
    DONE("2");

    private final String code;

    EventStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static EventStatus fromCode(String code) {
        for (EventStatus v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid EventStatus code: " + code);
    }
}
