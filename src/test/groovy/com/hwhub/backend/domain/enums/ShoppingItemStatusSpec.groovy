package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class ShoppingItemStatusSpec extends Specification {

    def "getCodeは各ShoppingItemStatusに対応するコードを返す"() {
        expect:
        ShoppingItemStatus.NOT_PURCHASED.code == "0"
        ShoppingItemStatus.IN_BASKET.code     == "1"
        ShoppingItemStatus.PURCHASED.code    == "9"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するShoppingItemStatus '#expected' を返す"() {
        expect:
        ShoppingItemStatus.fromCode(code) == expected

        where:
        code || expected
        "0"  || ShoppingItemStatus.NOT_PURCHASED
        "1"  || ShoppingItemStatus.IN_BASKET
        "9"  || ShoppingItemStatus.PURCHASED
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        ShoppingItemStatus.fromCode("99")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid ShoppingItemStatus code: 99"
    }

    def "fromCodeはnullを渡した場合もIllegalArgumentExceptionを投げる"() {
        when:
        ShoppingItemStatus.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
