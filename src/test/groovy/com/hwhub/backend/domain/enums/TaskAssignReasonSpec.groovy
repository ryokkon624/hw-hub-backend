package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class TaskAssignReasonSpec extends Specification {

    def "getCodeは各TaskAssignReasonに対応するコードを返す"() {
        expect:
        TaskAssignReason.SELF_ASSIGNED.code   == "0"
        TaskAssignReason.BY_REQUEST.code      == "1"
        TaskAssignReason.FORCED.code          == "2"
        TaskAssignReason.SYSTEM_ASSIGNED.code == "9"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するTaskAssignReason '#expected' を返す"() {
        expect:
        TaskAssignReason.fromCode(code) == expected

        where:
        code || expected
        "0"  || TaskAssignReason.SELF_ASSIGNED
        "1"  || TaskAssignReason.BY_REQUEST
        "2"  || TaskAssignReason.FORCED
        "9"  || TaskAssignReason.SYSTEM_ASSIGNED
    }

    def "fromCodeは不正なコードを渡すとIllegalArgumentExceptionを投げる"() {
        when:
        TaskAssignReason.fromCode("99")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid TaskAssignReason code: 99"
    }

    def "fromCodeはnullを渡すとIllegalArgumentExceptionを投げる"() {
        when:
        TaskAssignReason.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
