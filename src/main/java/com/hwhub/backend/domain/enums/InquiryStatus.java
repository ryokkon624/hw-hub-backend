package com.hwhub.backend.domain.enums;

public enum InquiryStatus implements CodeEnum {
    OPEN("00"),
    AI_ANSWERED("10"),
    PENDING_STAFF("20"),
    STAFF_ANSWERED("25"),
    CLOSED("90");

    private final String code;

    InquiryStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static InquiryStatus fromCode(String code) {
        for (InquiryStatus v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid InquiryStatus code: " + code);
    }
}
