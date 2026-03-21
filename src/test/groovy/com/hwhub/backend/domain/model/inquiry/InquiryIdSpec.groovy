package com.hwhub.backend.domain.model.inquiry

import spock.lang.Specification

class InquiryIdSpec extends Specification {

    def "正の値でInquiryIdが生成される"() {
        when:
        def id = new InquiryId(value)

        then:
        id.value() == value

        where:
        value << [1L, 100L, Long.MAX_VALUE]
    }

    def "0以下の値はIllegalArgumentExceptionをスローする"() {
        when:
        new InquiryId(value)

        then:
        thrown(IllegalArgumentException)

        where:
        value << [0L, -1L, Long.MIN_VALUE]
    }

    def "正の値でInquiryMessageIdが生成される"() {
        when:
        def id = new InquiryMessageId(value)

        then:
        id.value() == value

        where:
        value << [1L, 50L, Long.MAX_VALUE]
    }

    def "0以下の値はInquiryMessageIdでIllegalArgumentExceptionをスローする"() {
        when:
        new InquiryMessageId(value)

        then:
        thrown(IllegalArgumentException)

        where:
        value << [0L, -1L, Long.MIN_VALUE]
    }
}
