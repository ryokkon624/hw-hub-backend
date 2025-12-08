package com.hwhub.backend.domain.model

import com.hwhub.backend.domain.enums.RecurrenceType
import spock.lang.Specification

import java.time.LocalDate

class HouseworkModelSpec extends Specification{

    // =========================
    // reconstruct
    // =========================

    def "reconstructは全プロパティをそのまま保持する"() {
        given:
        Long houseworkId = 1L
        Long householdId = 10L
        String name = "猫トイレ掃除"
        String description = "全部入れ替え"
        String category = "PET"
        String recurrenceType = RecurrenceType.WEEKLY.code
        Integer weeklyDays = 0b0101010
        Integer dayOfMonth = 31
        Integer nthWeek = 2
        Integer weekday = 1
        LocalDate startDate = LocalDate.of(2025, 1, 1)
        LocalDate endDate = LocalDate.of(2025, 12, 31)
        Long defaultAssigneeUserId = 99L

        when:
        def model = HouseworkModel.reconstruct(
                houseworkId,
                householdId,
                name,
                description,
                category,
                recurrenceType,
                weeklyDays,
                dayOfMonth,
                nthWeek,
                weekday,
                startDate,
                endDate,
                defaultAssigneeUserId
        )

        then:
        model.houseworkId == houseworkId
        model.householdId == householdId
        model.name == name
        model.description == description
        model.category == category
        model.recurrenceType == recurrenceType
        model.weeklyDays == weeklyDays
        model.dayOfMonth == dayOfMonth
        model.nthWeek == nthWeek
        model.weekday == weekday
        model.startDate == startDate
        model.endDate == endDate
        model.defaultAssigneeUserId == defaultAssigneeUserId
    }

    // =========================
    // create
    // =========================

    def "createはhouseworkIdがnullで生成される"() {
        given:
        Long householdId = 10L
        String name = "ごみ出し"
        String description = "燃えるごみ"
        String category = "GARBAGE"
        String recurrenceType = RecurrenceType.WEEKLY.code
        Integer weeklyDays = 0b0010000
        Integer dayOfMonth = null
        Integer nthWeek = null
        Integer weekday = null
        LocalDate startDate = LocalDate.of(2025, 1, 1)
        LocalDate endDate = LocalDate.of(2025, 6, 30)
        Long defaultAssigneeUserId = 5L

        when:
        def model = HouseworkModel.create(
                householdId,
                name,
                description,
                category,
                recurrenceType,
                weeklyDays,
                dayOfMonth,
                nthWeek,
                weekday,
                startDate,
                endDate,
                defaultAssigneeUserId
        )

        then:
        model.houseworkId == null
        model.householdId == householdId
        model.name == name
        model.description == description
        model.category == category
        model.recurrenceType == recurrenceType
        model.weeklyDays == weeklyDays
        model.dayOfMonth == dayOfMonth
        model.nthWeek == nthWeek
        model.weekday == weekday
        model.startDate == startDate
        model.endDate == endDate
        model.defaultAssigneeUserId == defaultAssigneeUserId
    }

    // =========================
    // setBasicInfo
    // =========================

    def "setBasicInfoはname, description, categoryを上書きする"() {
        given:
        def model = HouseworkModel.reconstruct(
                1L,
                10L,
                "旧名称",
                "旧メモ",
                "OLD_CAT",
                RecurrenceType.WEEKLY.code,
                0b1,
                null,
                null,
                null,
                LocalDate.now(),
                null,
                null
        )

        when:
        model.setBasicInfo("新しい家事名", "新しい説明", "NEW_CAT")

        then:
        model.name == "新しい家事名"
        model.description == "新しい説明"
        model.category == "NEW_CAT"
    }

    // =========================
    // setRecurrenceWeekly
    // =========================

    def "setRecurrenceWeeklyは週次用のフィールドのみ設定し他はnullにする"() {
        given:
        def model = HouseworkModel.reconstruct(
                1L,
                10L,
                "家事",
                "説明",
                "CAT",
                RecurrenceType.MONTHLY.code, // 元は別タイプ
                null,
                15,
                2,
                3,
                LocalDate.now(),
                null,
                null
        )

        when:
        model.setRecurrenceWeekly(0b0101010)

        then:
        model.recurrenceType == RecurrenceType.WEEKLY.code
        model.weeklyDays == 0b0101010

        and: "月次・第n週関連のフィールドはnullにクリアされる"
        model.dayOfMonth == null
        model.nthWeek == null
        model.weekday == null
    }

    // =========================
    // setRecurrenceMonthly
    // =========================

    def "setRecurrenceMonthlyはdayOfMonthのみ設定し他の周期関連フィールドをnullにする"() {
        given:
        def model = HouseworkModel.reconstruct(
                1L,
                10L,
                "家事",
                "説明",
                "CAT",
                RecurrenceType.WEEKLY.code, // 元は週次
                0b1111111,
                null,
                2,
                3,
                LocalDate.now(),
                null,
                null
        )

        when:
        model.setRecurrenceMonthly(25)

        then:
        model.recurrenceType == RecurrenceType.MONTHLY.code
        model.dayOfMonth == 25

        and: "週次・第n週関連のフィールドはnullにクリアされる"
        model.weeklyDays == null
        model.nthWeek == null
        model.weekday == null
    }

    // =========================
    // setRecurrenceNthweekday
    // =========================

    def "setRecurrenceNthweekdayはnthWeekとweekdayのみ設定し他をnullにする"() {
        given:
        def model = HouseworkModel.reconstruct(
                1L,
                10L,
                "家事",
                "説明",
                "CAT",
                RecurrenceType.MONTHLY.code,
                0b1010101,
                10,
                null,
                null,
                LocalDate.now(),
                null,
                null
        )

        when:
        model.setRecurrenceNthweekday(3, 2)

        then:
        model.recurrenceType == RecurrenceType.NTH_WEEKDAY.code
        model.nthWeek == 3
        model.weekday == 2

        and: "週次・月次フィールドはnullにクリアされる"
        model.weeklyDays == null
        model.dayOfMonth == null
    }

    // =========================
    // setEffectivePriod
    // =========================

    def "setEffectivePriodはstartDateとendDateを上書きする"() {
        given:
        def model = HouseworkModel.reconstruct(
                1L,
                10L,
                "家事",
                "説明",
                "CAT",
                RecurrenceType.WEEKLY.code,
                0b1,
                null,
                null,
                null,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 31),
                null
        )

        when:
        model.setEffectivePriod(
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 2, 28)
        )

        then:
        model.startDate == LocalDate.of(2025, 2, 1)
        model.endDate == LocalDate.of(2025, 2, 28)
    }

    // =========================
    // setDefaultAssigneeUserId
    // =========================

    def "setDefaultAssigneeUserIdはデフォルト担当者を上書きする"() {
        given:
        def model = HouseworkModel.reconstruct(
                1L,
                10L,
                "家事",
                "説明",
                "CAT",
                RecurrenceType.WEEKLY.code,
                0b1,
                null,
                null,
                null,
                LocalDate.now(),
                null,
                5L
        )

        when:
        model.setDefaultAssigneeUserId(99L)

        then:
        model.defaultAssigneeUserId == 99L
    }
}
