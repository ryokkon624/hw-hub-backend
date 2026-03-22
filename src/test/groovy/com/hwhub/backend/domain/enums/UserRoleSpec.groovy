package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class UserRoleSpec extends Specification {

    def "getCode は各 Enum に対応するコードを返す"() {
        expect:
        UserRole.ADMIN.code == "ADMIN"
        UserRole.SUPPORT.code == "SPPRT"
    }

    @Unroll
    def "fromCode はコード '#code' に対応する Enum '#expected' を返す"() {
        expect:
        UserRole.fromCode(code) == expected

        where:
        code    || expected
        "ADMIN" || UserRole.ADMIN
        "SPPRT" || UserRole.SUPPORT
    }

    def "fromCode は不正なコードの場合 IllegalArgumentException を投げる"() {
        when:
        UserRole.fromCode("UNKNOWN")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid UserRole code: UNKNOWN"
    }

    def "fromCode は null を渡した場合 IllegalArgumentException を投げる"() {
        when:
        UserRole.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "values は ADMIN と SUPPORT の 2 種類を含む"() {
        expect:
        UserRole.values().length == 2
        UserRole.values().contains(UserRole.ADMIN)
        UserRole.values().contains(UserRole.SUPPORT)
    }
}
