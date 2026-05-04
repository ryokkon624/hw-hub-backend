package com.hwhub.backend.domain.enums;

public enum InquiryCategory implements CodeEnum {
    GENERAL("10"),
    HOUSEWORK("20"),
    SHOPPING("21"),
    ACCOUNT_SETTINGS("30"),
    BUG_REPORT("40"),
    OTHER("90");

    private final String code;

    InquiryCategory(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static InquiryCategory fromCode(String code) {
        for (InquiryCategory v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid InquiryCategory code: " + code);
    }
}
