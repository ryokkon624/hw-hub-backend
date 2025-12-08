package com.hwhub.backend.domain.enums;

public enum HouseholdMemberStatus implements CodeEnum {
    INVITED("0"),
    ACTIVE("1"),
    LEFT("9");

    private final String code;

    HouseholdMemberStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static HouseholdMemberStatus fromCode(String code) {
        for (HouseholdMemberStatus v : values()) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Invalid HouseholdMemberStatus code: " + code);
    }
}
