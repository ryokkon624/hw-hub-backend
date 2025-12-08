package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.HouseworkModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHousework
import spock.lang.Specification

import java.time.LocalDate

class HouseworkConverterSpec extends Specification{

    def "toModelは引数がnullのときnullを返す"() {
        expect:
        HouseworkConverter.toModel(null) == null
    }

    def "toModelはエンティティからモデルへ全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたMHouseworkエンティティ"
        LocalDate startDate = LocalDate.of(2025, 1, 1)
        LocalDate endDate   = LocalDate.of(2025, 12, 31)

        def entity = new MHousework()
        entity.setHouseworkId(1L)
        entity.setHouseholdId(10L)
        entity.setName("猫トイレの砂交換")
        entity.setDescription("全部入れ替え")
        entity.setCategory("PET")
        entity.setRecurrenceType("1")
        entity.setWeeklyDays(0b0101010)   // 適当なビットマスク
        entity.setDayOfMonth(15)
        entity.setNthWeek(2)
        entity.setWeekday(3)
        entity.setStartDate(DateConverter.toDate(startDate))
        entity.setEndDate(DateConverter.toDate(endDate))
        entity.setDefaultAssigneeUserId(100L)

        when: "toModelでドメインモデルに変換する"
        def model = HouseworkConverter.toModel(entity)

        then: "全フィールドが1対1でコピーされている"
        model != null
        with(model) {
            houseworkId == 1L
            householdId == 10L
            name == "猫トイレの砂交換"
            description == "全部入れ替え"
            category == "PET"
            recurrenceType == "1"
            weeklyDays == 0b0101010
            dayOfMonth == 15
            nthWeek == 2
            weekday == 3
            startDate == startDate
            endDate == endDate
            defaultAssigneeUserId == 100L
        }
    }

    def "toEntityは引数がnullのときnullを返す"() {
        expect:
        HouseworkConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへ全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたHouseworkModel"
        LocalDate startDate = LocalDate.of(2026, 4, 1)
        LocalDate endDate   = LocalDate.of(2026, 4, 30)

        def model = HouseworkModel.reconstruct(
                2L,                  // houseworkId
                20L,                 // householdId
                "お風呂掃除",           // name
                "バスタブと床を掃除",     // description
                "CLEANING",          // category
                "2",                 // recurrenceType
                16,           // weeklyDays
                10,                  // dayOfMonth
                1,                   // nthWeek
                6,                   // weekday
                startDate,           // startDate
                endDate,             // endDate
                200L                 // defaultAssigneeUserId
        )

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = HouseworkConverter.toEntity(model)

        then: "エンティティが生成され、各フィールドがコピーされている"
        entity != null
        with(entity) {
            houseworkId == 2L
            householdId == 20L
            name == "お風呂掃除"
            description == "バスタブと床を掃除"
            category == "CLEANING"
            recurrenceType == "2"
            weeklyDays == 16
            dayOfMonth == 10
            nthWeek == 1
            weekday == 6
            defaultAssigneeUserId == 200L
        }

        and: "日付フィールドはLocalDateとの相互変換で同じ値になる"
        DateConverter.toLocalDate(entity.startDate) == startDate
        DateConverter.toLocalDate(entity.endDate) == endDate
    }
}
