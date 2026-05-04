package com.hwhub.backend.domain.enums;

public enum OAuthFlow implements CodeEnum {
    LINK("LINK"),
    LOGIN("LOGIN");

    private final String code;

    OAuthFlow(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static OAuthFlow fromCode(String code) {
        for (OAuthFlow v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid OAuthFlow code: " + code);
    }
}
