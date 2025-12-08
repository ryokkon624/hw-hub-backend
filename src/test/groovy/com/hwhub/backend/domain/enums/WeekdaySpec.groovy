package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class WeekdaySpec extends Specification {

    def "getCodeは各曜日に対応するコードを返す"() {
        expect:
        Weekday.SUNDAY.code    == "0"
        Weekday.MONDAY.code    == "1"
        Weekday.TUESDAY.code   == "2"
        Weekday.WEDNESDAY.code == "3"
        Weekday.THURSDAY.code  == "4"
        Weekday.FRIDAY.code    == "5"
        Weekday.SATURDAY.code  == "6"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するWeekday '#expected' を返す"() {
        expect:
        Weekday.fromCode(code) == expected

        where:
        code || expected
        "0"  || Weekday.SUNDAY
        "1"  || Weekday.MONDAY
        "2"  || Weekday.TUESDAY
        "3"  || Weekday.WEDNESDAY
        "4"  || Weekday.THURSDAY
        "5"  || Weekday.FRIDAY
        "6"  || Weekday.SATURDAY
    }

    def "fromCodeは不正なコードを渡すとIllegalArgumentExceptionを投げる"() {
        when:
        Weekday.fromCode("99")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid Weekday code: 99"
    }

    def "fromCodeはnullを渡すとIllegalArgumentExceptionを投げる"() {
        when:
        Weekday.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
