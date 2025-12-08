package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class NthWeekSpec extends Specification {

    def "getCodeは各NthWeekに対応するコードを返す"() {
        expect:
        NthWeek.FIRST_WEEK.code  == "1"
        NthWeek.SECOND_WEEK.code == "2"
        NthWeek.THIRD_WEEK.code  == "3"
        NthWeek.FOURTH_WEEK.code == "4"
        NthWeek.LAST_WEEK.code   == "5"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するNthWeek '#expected' を返す"() {
        expect:
        NthWeek.fromCode(code) == expected

        where:
        code || expected
        "1"  || NthWeek.FIRST_WEEK
        "2"  || NthWeek.SECOND_WEEK
        "3"  || NthWeek.THIRD_WEEK
        "4"  || NthWeek.FOURTH_WEEK
        "5"  || NthWeek.LAST_WEEK
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        NthWeek.fromCode("9")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid NthWeek code: 9"
    }

    def "fromCodeはnullを渡した場合もIllegalArgumentExceptionを投げる"() {
        when:
        NthWeek.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
