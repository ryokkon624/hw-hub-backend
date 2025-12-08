package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class PurchaseLocationTypeSpec extends Specification {

    def "getCodeは各PurchaseLocationTypeに対応するコードを返す"() {
        expect:
        PurchaseLocationType.SUPERMARKET.code == "1"
        PurchaseLocationType.ONLINE.code      == "2"
        PurchaseLocationType.DRUGSTORE.code   == "3"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するPurchaseLocationType '#expected' を返す"() {
        expect:
        PurchaseLocationType.fromCode(code) == expected

        where:
        code || expected
        "1"  || PurchaseLocationType.SUPERMARKET
        "2"  || PurchaseLocationType.ONLINE
        "3"  || PurchaseLocationType.DRUGSTORE
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        PurchaseLocationType.fromCode("9")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid PurchaseLocationType code: 9"
    }

    def "fromCodeはnullを渡した場合もIllegalArgumentExceptionを投げる"() {
        when:
        PurchaseLocationType.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
