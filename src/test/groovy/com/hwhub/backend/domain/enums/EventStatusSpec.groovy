package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class EventStatusSpec extends Specification {

    @Unroll
    def "getCodeが正しいコード値を返すこと: #type -> #expectedCode"() {
        expect:
        type.getCode() == expectedCode

        where:
        type                   || expectedCode
        EventStatus.PENDING    || "0"
        EventStatus.PROCESSING || "1"
        EventStatus.DONE       || "2"
    }

    @Unroll
    def "fromCodeで正しい列挙子が取得できること: #code -> #expectedType"() {
        expect:
        EventStatus.fromCode(code) == expectedType

        where:
        code || expectedType
        "0"  || EventStatus.PENDING
        "1"  || EventStatus.PROCESSING
        "2"  || EventStatus.DONE
    }

    def "fromCodeで存在しないコードを指定した場合、IllegalArgumentExceptionがスローされること"() {
        when:
        EventStatus.fromCode("99")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid EventStatus code: 99"
    }
}
