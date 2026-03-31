package com.hwhub.backend.domain.model.houseworktemplate

import spock.lang.Specification
import spock.lang.Unroll

class HouseworkTemplateIdSpec extends Specification {

    def "コンストラクタ: 正の数でインスタンスが生成されること"() {
        when:
        def id = new HouseworkTemplateId(1L)

        then:
        id.value() == 1L
    }

    @Unroll
    def "コンストラクタ: #value の時に IllegalArgumentException がスローされること"() {
        when:
        new HouseworkTemplateId(value)

        then:
        thrown(IllegalArgumentException)

        where:
        value << [0L, -1L, -100L]
    }
}
