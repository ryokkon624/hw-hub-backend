package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class NotificationGroupSpec extends Specification {

    def "getCodeは正しいコードを返す"() {
        expect:
        NotificationGroup.HOUSEHOLD.getCode() == "100"
        NotificationGroup.TASK_ASSIGNMENT.getCode() == "200"
    }

    @Unroll
    def "fromCodeは#codeに対して#expectedを返す"() {
        expect:
        NotificationGroup.fromCode(code) == expected

        where:
        code  || expected
        "100" || NotificationGroup.HOUSEHOLD
        "200" || NotificationGroup.TASK_ASSIGNMENT
    }

    def "fromCodeは無効なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        NotificationGroup.fromCode("999")

        then:
        thrown(IllegalArgumentException)
    }

    def "valuesは2つの値を含む"() {
        expect:
        NotificationGroup.values().length == 2
    }
}
