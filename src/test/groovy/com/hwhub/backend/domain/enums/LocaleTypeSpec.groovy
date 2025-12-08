package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class LocaleTypeSpec extends Specification{

    def "getCodeは各LocaleTypeに対応するコードを返す"() {
        expect:
        LocaleType.JA.code == "ja"
        LocaleType.EN.code == "en"
        LocaleType.ES.code == "es"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するLocaleType '#expected' を返す"() {
        expect:
        LocaleType.fromCode(code) == expected

        where:
        code || expected
        "ja" || LocaleType.JA
        "en" || LocaleType.EN
        "es" || LocaleType.ES
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        LocaleType.fromCode("fr")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Unknown locale code: fr"
    }

    def "fromCodeはnullを渡した場合もIllegalArgumentExceptionを投げる"() {
        when:
        LocaleType.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
