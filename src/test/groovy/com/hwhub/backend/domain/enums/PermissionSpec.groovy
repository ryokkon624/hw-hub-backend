package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class PermissionSpec extends Specification {

    def "getCode は各 Enum に対応するコードを返す"() {
        expect:
        Permission.USER_LIST_VIEW.code == "10"
        Permission.ROLE_MANAGEMENT.code == "11"
        Permission.INQUIRY_REPLY.code == "20"
    }

    @Unroll
    def "fromCode はコード '#code' に対応する Enum '#expected' を返す"() {
        expect:
        Permission.fromCode(code) == expected

        where:
        code || expected
        "10" || Permission.USER_LIST_VIEW
        "11" || Permission.ROLE_MANAGEMENT
        "20" || Permission.INQUIRY_REPLY
    }

    def "fromCode は不正なコードの場合 IllegalArgumentException を投げる"() {
        when:
        Permission.fromCode("99")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid Permission code: 99"
    }

    def "fromCode は null を渡した場合 IllegalArgumentException を投げる"() {
        when:
        Permission.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "values は 3 種類のパーミッションを含む"() {
        expect:
        Permission.values().length == 3
        Permission.values().contains(Permission.USER_LIST_VIEW)
        Permission.values().contains(Permission.ROLE_MANAGEMENT)
        Permission.values().contains(Permission.INQUIRY_REPLY)
    }
}
