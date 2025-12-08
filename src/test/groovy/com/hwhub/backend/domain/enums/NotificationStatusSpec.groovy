package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class NotificationStatusSpec extends Specification {

    def "getCodeは各NotificationStatusに対応するコードを返す"() {
        expect:
        NotificationStatus.ACTIVE.code == "1"
        NotificationStatus.INACTIVE.code == "0"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するNotificationStatus '#expected' を返す"() {
        expect:
        NotificationStatus.fromCode(code) == expected

        where:
        code || expected
        "1"  || NotificationStatus.ACTIVE
        "0"  || NotificationStatus.INACTIVE
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        NotificationStatus.fromCode("9")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid NotificationStatus code: 9"
    }

    def "fromCodeはnullを渡した場合もIllegalArgumentExceptionを投げる"() {
        when:
        NotificationStatus.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
