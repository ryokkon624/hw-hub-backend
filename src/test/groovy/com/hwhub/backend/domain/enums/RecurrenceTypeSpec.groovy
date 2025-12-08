package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class RecurrenceTypeSpec extends Specification {

    def "getCodeは各RecurrenceTypeに対応するコードを返す"() {
        expect:
        RecurrenceType.WEEKLY.code      == "1"
        RecurrenceType.MONTHLY.code    == "2"
        RecurrenceType.NTH_WEEKDAY.code == "3"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するRecurrenceType '#expected' を返す"() {
        expect:
        RecurrenceType.fromCode(code) == expected

        where:
        code || expected
        "1"  || RecurrenceType.WEEKLY
        "2"  || RecurrenceType.MONTHLY
        "3"  || RecurrenceType.NTH_WEEKDAY
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        RecurrenceType.fromCode("9")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid RecurrenceType code: 9"
    }

    def "fromCodeはnullを渡した場合もIllegalArgumentExceptionを投げる"() {
        when:
        RecurrenceType.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
