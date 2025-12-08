package com.hwhub.backend.domain.enums;

public enum InvitationStatus implements CodeEnum {
    PENDING("0"),
    ACCEPTED("1"),
    DECLINED("7"),
    REVOKED("8"),
    EXPIRED("9");

    private final String code;

    InvitationStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static InvitationStatus fromCode(String code) {
        for (InvitationStatus v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid InvitationStatus code: " + code);
    }
}
