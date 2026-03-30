package com.hwhub.backend.domain.model.houseworktemplate

import com.hwhub.backend.domain.enums.Category
import com.hwhub.backend.domain.enums.NthWeek
import com.hwhub.backend.domain.enums.RecurrenceType
import com.hwhub.backend.domain.enums.Weekday
import spock.lang.Specification
import spock.lang.Unroll

class HouseworkTemplateModelSpec extends Specification {

    def "reconstruct: すべてのプロパティが正しく保持されること"() {
        given:
        def id = new HouseworkTemplateId(1L)
        def nameJa = "家事名"
        def nameEn = "Housework"
        def nameEs = "Tarea"
        def descJa = "説明"
        def descEn = "Description"
        def descEs = "Descripción"
        def recJa = "おすすめ"
        def recEn = "Recommendation"
        def recEs = "Recomendación"
        def category = Category.CLEANING
        def type = RecurrenceType.WEEKLY
        def weeklyDays = 1

        when:
        def model = HouseworkTemplateModel.reconstruct(
                id, nameJa, nameEn, nameEs, descJa, descEn, descEs,
                recJa, recEn, recEs, category, type, weeklyDays, null, null, null
        )

        then:
        model.houseworkTemplateId == id
        model.nameJa == nameJa
        model.nameEn == nameEn
        model.nameEs == nameEs
        model.descriptionJa == descJa
        model.descriptionEn == descEn
        model.descriptionEs == descEs
        model.recommendationJa == recJa
        model.recommendationEn == recEn
        model.recommendationEs == recEs
        model.category == category
        model.recurrenceType == type
        model.weeklyDays == weeklyDays
    }

    def "create: IDがnullでインスタンスが生成されること"() {
        when:
        def model = HouseworkTemplateModel.create(
                "J", "E", "S", null, null, null,
                null, null, null, Category.CLEANING, RecurrenceType.WEEKLY, 1, null, null, null
        )

        then:
        model.houseworkTemplateId == null
        model.nameJa == "J"
    }

    @Unroll
    def "整合性チェック: 不正な組み合わせで IllegalArgumentException がスローされること (#desc)"() {
        when:
        HouseworkTemplateModel.reconstruct(
                new HouseworkTemplateId(1L),
                nameJa, nameEn, nameEs, descJa, descEn, descEs,
                recJa, recEn, recEs, Category.CLEANING, type, weeklyDays, dayOfMonth, nthWeek, weekday
        )

        then:
        thrown(IllegalArgumentException)

        where:
        desc                          | nameJa | nameEn | nameEs | descJa | descEn | descEs | recJa | recEn | recEs | type                       | weeklyDays | dayOfMonth | nthWeek      | weekday
        "説明Jaがnull"                 | "J"    | "E"    | "S"    | null   | "E"    | "S"    | null  | null  | null  | RecurrenceType.WEEKLY      | 1          | null       | null         | null
        "説明Enがnull"                 | "J"    | "E"    | "S"    | "J"    | null   | "S"    | null  | null  | null  | RecurrenceType.WEEKLY      | 1          | null       | null         | null
        "説明Esがnull"                 | "J"    | "E"    | "S"    | "J"    | "E"    | null   | null  | null  | null  | RecurrenceType.WEEKLY      | 1          | null       | null         | null
        "おすすめJaがnull"              | "J"    | "E"    | "S"    | null   | null   | null   | null  | "E"    | "S"    | RecurrenceType.WEEKLY      | 1          | null       | null         | null
        "おすすめEnがnull"              | "J"    | "E"    | "S"    | null   | null   | null   | "J"   | null   | "S"    | RecurrenceType.WEEKLY      | 1          | null       | null         | null
        "おすすめEsがnull"              | "J"    | "E"    | "S"    | null   | null   | null   | "J"   | "E"    | null   | RecurrenceType.WEEKLY      | 1          | null       | null         | null
        "WEEKLYでweeklyDaysがnull"    | "J"    | "E"    | "S"    | null   | null   | null   | null  | null  | null  | RecurrenceType.WEEKLY      | null       | null       | null         | null
        "MONTHLYでdayOfMonthがnull"   | "J"    | "E"    | "S"    | null   | null   | null   | null  | null  | null  | RecurrenceType.MONTHLY     | null       | null       | null         | null
        "NTH_WEEKDAYでnthWeekがnull"  | "J"    | "E"    | "S"    | null   | null   | null   | null  | null  | null  | RecurrenceType.NTH_WEEKDAY | null       | null       | null         | Weekday.MONDAY
        "NTH_WEEKDAYでweekdayがnull"  | "J"    | "E"    | "S"    | null   | null   | null   | null  | null  | null  | RecurrenceType.NTH_WEEKDAY | null       | null       | NthWeek.FIRST_WEEK | null
    }

    @Unroll
    def "Objects.requireNonNull チェック: #field が null の時に NullPointerException がスローされること"() {
        when:
        HouseworkTemplateModel.reconstruct(
                new HouseworkTemplateId(1L),
                nameJa, nameEn, nameEs, null, null, null,
                null, null, null, category, type, 1, null, null, null
        )

        then:
        thrown(NullPointerException)

        where:
        field            | nameJa | nameEn | nameEs | category           | type
        "nameJa"         | null   | "E"    | "S"    | Category.CLEANING | RecurrenceType.WEEKLY
        "nameEn"         | "J"    | null   | "S"    | Category.CLEANING | RecurrenceType.WEEKLY
        "nameEs"         | "J"    | "E"    | null   | Category.CLEANING | RecurrenceType.WEEKLY
        "category"       | "J"    | "E"    | "S"    | null               | RecurrenceType.WEEKLY
        "recurrenceType" | "J"    | "E"    | "S"    | Category.CLEANING | null
    }
}
