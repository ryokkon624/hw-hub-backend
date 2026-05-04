package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class ThemeModeSpec extends Specification {

    def "getCodeは各ThemeModeに対応するコードを返す"() {
        expect:
        ThemeMode.SYSTEM.code == "SYSTEM"
        ThemeMode.LIGHT.code == "LIGHT"
        ThemeMode.DARK.code == "DARK"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するThemeMode '#expected' を返す"() {
        expect:
        ThemeMode.fromCode(code) == expected

        where:
        code     || expected
        "SYSTEM" || ThemeMode.SYSTEM
        "LIGHT"  || ThemeMode.LIGHT
        "DARK"   || ThemeMode.DARK
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        ThemeMode.fromCode("XXX")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid ThemeMode code: XXX"
    }

    def "fromCodeはnullを渡した場合も例外を投げる"() {
        when:
        ThemeMode.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
