package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class OAuthFlowSpec extends Specification {

    @Unroll
    def "getCodeは #flow に対して #expected を返す"() {
        expect:
        flow.code == expected

        where:
        flow            | expected
        OAuthFlow.LINK  | "LINK"
        OAuthFlow.LOGIN | "LOGIN"
    }

    @Unroll
    def "fromCodeは #code に対して #expected を返す"() {
        expect:
        OAuthFlow.fromCode(code) == expected

        where:
        code    | expected
        "LINK"  | OAuthFlow.LINK
        "LOGIN" | OAuthFlow.LOGIN
    }

    def "fromCodeは無効なコードに対してIllegalArgumentExceptionを投げる"() {
        when:
        OAuthFlow.fromCode("INVALID")

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == "Invalid OAuthFlow code: INVALID"
    }
}
