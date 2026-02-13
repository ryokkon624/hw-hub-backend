package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class AuthProviderSpec extends Specification {

    def "getCodeは各値に対して正しい文字列を返す"() {
        expect:
        AuthProvider.LOCAL.code == "LOCAL"
        AuthProvider.GOOGLE.code == "GOOGLE"
    }

    @Unroll
    def "fromCodeはコード #code に対して #expected を返す"() {
        expect:
        AuthProvider.fromCode(code) == expected

        where:
        code     | expected
        "LOCAL"  | AuthProvider.LOCAL
        "GOOGLE" | AuthProvider.GOOGLE
    }

    def "fromCodeは未知のコードに対して例外を投げる"() {
        when:
        AuthProvider.fromCode("UNKNOWN")

        then:
        thrown(IllegalArgumentException)
    }
}
