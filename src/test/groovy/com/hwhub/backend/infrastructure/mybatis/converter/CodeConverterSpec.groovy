package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MCode
import spock.lang.Specification

class CodeConverterSpec extends Specification{

    def "toModel returns null when entity is null"() {
        expect:
        CodeConverter.toModel(null) == null
    }

    def "toModel converts all fields from entity to model"() {
        given:
        def entity = new MCode()
        entity.setCodeType("0001")
        entity.setCodeTypeName("周期タイプ")
        entity.setCodeTypeNameEn("Recurrence Type")
        entity.setCodeValue("1")
        entity.setName("毎週")
        entity.setDisplayNameJa("毎週（表示名）")
        entity.setDisplayNameEn("Weekly")
        entity.setDisplayNameEs("Semanal")
        entity.setRemarks("some remarks")
        entity.setDisplayOrder("10")

        when:
        def model = CodeConverter.toModel(entity)

        then:
        model != null
        with(model) {
            codeType == "0001"
            codeTypeName == "周期タイプ"
            codeTypeNameEn == "Recurrence Type"
            codeValue == "1"
            name == "毎週"
            displayNameJa == "毎週（表示名）"
            displayNameEn == "Weekly"
            displayNameEs == "Semanal"
            remarks == "some remarks"
            displayOrder == "10"
        }
    }
}
