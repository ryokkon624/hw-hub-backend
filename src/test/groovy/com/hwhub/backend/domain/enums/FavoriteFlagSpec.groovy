package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class FavoriteFlagSpec extends Specification {

    def "getCodeは各Enumに対応するコードを返す"() {
        expect:
        FavoriteFlag.NORMAL.code == "0"
        FavoriteFlag.FAVORITE.code == "1"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するEnum '#expected' を返す"() {
        expect:
        FavoriteFlag.fromCode(code) == expected

        where:
        code || expected
        "0"  || FavoriteFlag.NORMAL
        "1"  || FavoriteFlag.FAVORITE
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        FavoriteFlag.fromCode("9")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid FavoriteFlag code: 9"
    }

    def "fromCodeはnullを渡した場合も例外を投げる"() {
        when:
        FavoriteFlag.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
