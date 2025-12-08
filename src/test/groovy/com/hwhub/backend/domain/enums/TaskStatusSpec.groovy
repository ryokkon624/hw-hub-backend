package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class TaskStatusSpec extends Specification {

    def "getCodeは各TaskStatusに対応するコードを返す"() {
        expect:
        TaskStatus.NOT_DONE.code == "0"
        TaskStatus.DONE.code     == "1"
        TaskStatus.SKIPPED.code  == "9"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するTaskStatus '#expected' を返す"() {
        expect:
        TaskStatus.fromCode(code) == expected

        where:
        code || expected
        "0"  || TaskStatus.NOT_DONE
        "1"  || TaskStatus.DONE
        "9"  || TaskStatus.SKIPPED
    }

    def "fromCodeは不正なコードを渡すとIllegalArgumentExceptionを投げる"() {
        when:
        TaskStatus.fromCode("99")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid TaskStatus code: 99"
    }

    def "fromCodeはnullを渡すとIllegalArgumentExceptionを投げる"() {
        when:
        TaskStatus.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
