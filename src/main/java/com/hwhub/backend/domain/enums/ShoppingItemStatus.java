package com.hwhub.backend.domain.enums;

public enum ShoppingItemStatus implements CodeEnum {
    NOT_PURCHASED("0"),
    IN_BASKET("1"),
    PURCHASED("9");

    private final String code;

    ShoppingItemStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static ShoppingItemStatus fromCode(String code) {
        for (ShoppingItemStatus v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid ShoppingItemStatus code: " + code);
    }
}
