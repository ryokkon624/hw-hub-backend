package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class HouseholdMemberStatusSpec extends Specification{

    def "getCodeは各Enumに対応するコードを返す"() {
        expect:
        HouseholdMemberStatus.INVITED.code == "0"
        HouseholdMemberStatus.ACTIVE.code == "1"
        HouseholdMemberStatus.LEFT.code == "9"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するEnum '#expected' を返す"() {
        expect:
        HouseholdMemberStatus.fromCode(code) == expected

        where:
        code || expected
        "0"  || HouseholdMemberStatus.INVITED
        "1"  || HouseholdMemberStatus.ACTIVE
        "9"  || HouseholdMemberStatus.LEFT
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        HouseholdMemberStatus.fromCode("99")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid HouseholdMemberStatus code: 99"
    }

    def "fromCodeはnullを渡した場合も例外を投げる"() {
        when:
        HouseholdMemberStatus.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
