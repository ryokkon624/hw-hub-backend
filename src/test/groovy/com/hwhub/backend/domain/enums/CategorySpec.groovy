package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class CategorySpec extends Specification {

    def "getCodeは各Enumに対応するコードを返す"() {
        expect:
        Category.PET.code == "PET"
        Category.CLEANING.code == "CLEAN"
        Category.GARBAGE.code == "GARBAGE"
        Category.GARDEN.code == "GARDEN"
        Category.KITCHEN.code == "KITCHEN"
        Category.OTHER.code == "OTHER"
    }

    @Unroll
    def "fromCodeはコード'#code'に対応するEnum '#expected' を返す"() {
        expect:
        Category.fromCode(code) == expected

        where:
        code       || expected
        "PET"      || Category.PET
        "CLEAN"    || Category.CLEANING
        "GARBAGE"  || Category.GARBAGE
        "GARDEN"   || Category.GARDEN
        "KITCHEN"  || Category.KITCHEN
        "OTHER"    || Category.OTHER
    }

    def "fromCodeは不正なコードの場合IllegalArgumentExceptionを投げる"() {
        when:
        Category.fromCode("XXX")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid Category code: XXX"
    }

    def "fromCodeはnullを渡した場合も例外を投げる"() {
        when:
        Category.fromCode(null)

        then:
        thrown(IllegalArgumentException)
    }
}
