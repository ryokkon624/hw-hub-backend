package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.enums.Category
import com.hwhub.backend.domain.enums.NthWeek
import com.hwhub.backend.domain.enums.RecurrenceType
import com.hwhub.backend.domain.enums.Weekday
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateId
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseworkTemplate
import spock.lang.Specification
import spock.lang.Unroll

class HouseworkTemplateConverterSpec extends Specification {

    def "toModel: entity が null の場合は null を返すこと"() {
        expect:
        HouseworkTemplateConverter.toModel(null) == null
    }

    @Unroll
    def "toModel: 各フィールドが正しく変換されること (type=#typeCode, nthWeek=#nthW, weekday=#wd)"() {
        given:
        def entity = new MHouseworkTemplate(
                houseworkTemplateId: 1L,
                nameJa: "J", nameEn: "E", nameEs: "S",
                descriptionJa: "DJ", descriptionEn: "DE", descriptionEs: "DS",
                recommendationJa: "RJ", recommendationEn: "RE", recommendationEs: "RS",
                category: "CLEAN",
                recurrenceType: typeCode,
                weeklyDays: 1,
                dayOfMonth: null,
                nthWeek: nthW,
                weekday: wd
        )

        when:
        def model = HouseworkTemplateConverter.toModel(entity)

        then:
        model.houseworkTemplateId.value() == 1L
        model.nameJa == "J"
        model.category == Category.CLEANING
        model.recurrenceType == RecurrenceType.fromCode(typeCode)
        model.nthWeek == expectedNthW
        model.weekday == expectedWd

        where:
        typeCode | nthW | wd   || expectedNthW         | expectedWd
        "3"      | 1    | 1    || NthWeek.FIRST_WEEK   | Weekday.MONDAY
        "1"      | null | 2    || null                 | Weekday.TUESDAY
        "1"      | 3    | null || NthWeek.THIRD_WEEK   | null
        "1"      | null | null || null                 | null
    }

    def "toEntity: model が null の場合は null を返すこと"() {
        expect:
        HouseworkTemplateConverter.toEntity(null) == null
    }

    @Unroll
    def "toEntity: 各フィールドが正しく変換されること (id=#hid, type=#type, nthWeek=#nthW, weekday=#wd)"() {
        given:
        def model = HouseworkTemplateModel.reconstruct(
                hid, "J", "E", "S", "DJ", "DE", "DS",
                "RJ", "RE", "RS", Category.KITCHEN, type,
                127, null, nthW, wd
        )

        when:
        def entity = HouseworkTemplateConverter.toEntity(model)

        then:
        entity.houseworkTemplateId == expectedHid
        entity.nameJa == "J"
        entity.category == "KITCHEN"
        entity.recurrenceType == type.getCode()
        entity.nthWeek == expectedNthW
        entity.weekday == expectedWd

        where:
        hid                         | type                       | nthW                 | wd              || expectedHid | expectedNthW | expectedWd
        new HouseworkTemplateId(2L) | RecurrenceType.NTH_WEEKDAY | NthWeek.SECOND_WEEK  | Weekday.TUESDAY || 2L          | 2            | 2
        null                        | RecurrenceType.WEEKLY      | NthWeek.FOURTH_WEEK  | Weekday.FRIDAY  || null        | 4            | 5
        new HouseworkTemplateId(3L) | RecurrenceType.WEEKLY      | null                 | Weekday.MONDAY  || 3L          | null         | 1
        new HouseworkTemplateId(4L) | RecurrenceType.WEEKLY      | NthWeek.LAST_WEEK    | null            || 4L          | 5            | null
        null                        | RecurrenceType.WEEKLY      | null                 | null            || null        | null         | null
    }
}
