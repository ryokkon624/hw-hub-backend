package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class TaskRecalcStatusSpec extends Specification {

    def "getCodeは各TaskRecalcStatusに対応するコードを返す"() {
        expect:
        TaskRecalcStatus.PENDING.code     == "0"
        TaskRecalcStatus.PROCESSING.code  == "1"
        TaskRecalcStatus.DONE.code        == "2"
        TaskRecalcStatus.FAILED.code      == "9"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するTaskRecalcStatus '#expected' を返す"() {
        expect:
        TaskRecalcStatus.fromCode(code) == expected

        where:
        code || expected
        "0"  || TaskRecalcStatus.PENDING
        "1"  || TaskRecalcStatus.PROCESSING
        "2"  || TaskRecalcStatus.DONE
        "9"  || TaskRecalcStatus.FAILED
    }

    def "fromCodeは不正なコードを渡すとIllegalArgumentExceptionを投げる"() {
        when:
        TaskRecalcStatus.fromCode("99")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid TaskRecalcStatus code: 99"
    }

    def "fromCodeはnullを渡すとIllegalArgumentExceptionを投げる"() {
        when:
        TaskRecalcStatus.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
