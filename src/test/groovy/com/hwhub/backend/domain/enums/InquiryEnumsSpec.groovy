package com.hwhub.backend.domain.enums

import spock.lang.Specification

class InquiryEnumsSpec extends Specification {

    // ==================================
    // InquiryCategory
    // ==================================

    def "InquiryCategoryのfromCodeは有効なコードを正しく変換する"() {
        expect:
        InquiryCategory.fromCode(code) == expected

        where:
        code | expected
        "10" | InquiryCategory.GENERAL
        "20" | InquiryCategory.HOUSEWORK
        "21" | InquiryCategory.SHOPPING
        "30" | InquiryCategory.ACCOUNT_SETTINGS
        "40" | InquiryCategory.BUG_REPORT
        "90" | InquiryCategory.OTHER
    }

    def "InquiryCategoryのgetCodeは各enumに対して正しいコードを返す"() {
        expect:
        category.code == expectedCode

        where:
        category                          | expectedCode
        InquiryCategory.GENERAL           | "10"
        InquiryCategory.HOUSEWORK         | "20"
        InquiryCategory.SHOPPING          | "21"
        InquiryCategory.ACCOUNT_SETTINGS  | "30"
        InquiryCategory.BUG_REPORT        | "40"
        InquiryCategory.OTHER             | "90"
    }

    def "InquiryCategoryのfromCodeは無効なコードでIllegalArgumentExceptionをスローする"() {
        when:
        InquiryCategory.fromCode("99")

        then:
        thrown(IllegalArgumentException)
    }

    // ==================================
    // InquiryStatus
    // ==================================

    def "InquiryStatusのfromCodeは有効なコードを正しく変換する"() {
        expect:
        InquiryStatus.fromCode(code) == expected

        where:
        code | expected
        "00" | InquiryStatus.OPEN
        "10" | InquiryStatus.AI_ANSWERED
        "20" | InquiryStatus.PENDING_STAFF
        "25" | InquiryStatus.STAFF_ANSWERED
        "90" | InquiryStatus.CLOSED
    }

    def "InquiryStatusのgetCodeは各enumに対して正しいコードを返す"() {
        expect:
        status.code == expectedCode

        where:
        status                       | expectedCode
        InquiryStatus.OPEN           | "00"
        InquiryStatus.AI_ANSWERED    | "10"
        InquiryStatus.PENDING_STAFF  | "20"
        InquiryStatus.STAFF_ANSWERED | "25"
        InquiryStatus.CLOSED         | "90"
    }

    def "InquiryStatusのfromCodeは無効なコードでIllegalArgumentExceptionをスローする"() {
        when:
        InquiryStatus.fromCode("99")

        then:
        thrown(IllegalArgumentException)
    }

    // ==================================
    // SenderType
    // ==================================

    def "SenderTypeのfromCodeは有効なコードを正しく変換する"() {
        expect:
        SenderType.fromCode(code) == expected

        where:
        code    | expected
        "USER"  | SenderType.YOU
        "AI"    | SenderType.AI_SUPPORT
        "STAFF" | SenderType.STAFF
    }

    def "SenderTypeのgetCodeは各enumに対して正しいコードを返す"() {
        expect:
        senderType.code == expectedCode

        where:
        senderType             | expectedCode
        SenderType.YOU         | "USER"
        SenderType.AI_SUPPORT  | "AI"
        SenderType.STAFF       | "STAFF"
    }

    def "SenderTypeのfromCodeは無効なコードでIllegalArgumentExceptionをスローする"() {
        when:
        SenderType.fromCode("UNKNOWN")

        then:
        thrown(IllegalArgumentException)
    }
}
