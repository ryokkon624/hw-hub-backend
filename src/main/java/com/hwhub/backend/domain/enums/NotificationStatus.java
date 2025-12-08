package com.hwhub.backend.domain.enums;

public enum NotificationStatus implements CodeEnum {
    ACTIVE("1"),
    INACTIVE("0");

    private final String code;

    NotificationStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static NotificationStatus fromCode(String code) {
        for (NotificationStatus v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid NotificationStatus code: " + code);
    }
}
