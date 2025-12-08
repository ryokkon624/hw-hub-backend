package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class InvitationStatusSpec extends Specification {

    def "getCodeは各Enumに対応するコードを返す"() {
        expect:
        InvitationStatus.PENDING.code  == "0"
        InvitationStatus.ACCEPTED.code == "1"
        InvitationStatus.DECLINED.code == "7"
        InvitationStatus.REVOKED.code  == "8"
        InvitationStatus.EXPIRED.code  == "9"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するEnum '#expected' を返す"() {
        expect:
        InvitationStatus.fromCode(code) == expected

        where:
        code || expected
        "0"  || InvitationStatus.PENDING
        "1"  || InvitationStatus.ACCEPTED
        "7"  || InvitationStatus.DECLINED
        "8"  || InvitationStatus.REVOKED
        "9"  || InvitationStatus.EXPIRED
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        InvitationStatus.fromCode("99")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid InvitationStatus code: 99"
    }

    def "fromCodeはnullを渡した場合も例外を投げる"() {
        when:
        InvitationStatus.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
