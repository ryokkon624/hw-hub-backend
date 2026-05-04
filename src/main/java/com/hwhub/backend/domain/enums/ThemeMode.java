package com.hwhub.backend.domain.enums;

public enum ThemeMode implements CodeEnum {
    SYSTEM("SYSTEM"),
    LIGHT("LIGHT"),
    DARK("DARK");

    private final String code;

    ThemeMode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static ThemeMode fromCode(String code) {
        for (ThemeMode v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid ThemeMode code: " + code);
    }
}
