package com.hwhub.backend.domain.enums;

public enum TaskAssignReason implements CodeEnum {
    SELF_ASSIGNED("0"),
    BY_REQUEST("1"),
    FORCED("2"),
    SYSTEM_ASSIGNED("9");

    private final String code;

    TaskAssignReason(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static TaskAssignReason fromCode(String code) {
        for (TaskAssignReason v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid TaskAssignReason code: " + code);
    }
}
