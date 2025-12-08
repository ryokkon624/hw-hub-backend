package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.HouseworkTaskAssignmentHistModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskAssignmentHistory
import spock.lang.Specification

import java.time.LocalDateTime

class HouseworkTaskAssignmentHistConverterSpec extends Specification {
    
    def "toModelは引数がnullのときnullを返す"() {
        expect:
        HouseworkTaskAssignmentHistConverter.toModel(null) == null
    }

    def "toModelはエンティティからモデルへ全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたTHouseworkTaskAssignmentHistoryエンティティ"
        LocalDateTime changedAt = LocalDateTime.of(2025, 2, 1, 10, 30, 0)

        def entity = new THouseworkTaskAssignmentHistory()
        entity.setHouseworkTaskAssignmentHistoryId(1L)
        entity.setHouseworkTaskId(100L)
        entity.setHouseholdId(10L)
        entity.setFromAssigneeUserId(1000L)
        entity.setToAssigneeUserId(2000L)
        entity.setOperatedUserId(3000L)
        entity.setAssignReasonType("1")      // 変更理由種別コード
        entity.setNote("お願いして引き継ぎ")
        entity.setChangedAt(DateConverter.toDate(changedAt))

        when: "toModelでドメインモデルに変換する"
        def model = HouseworkTaskAssignmentHistConverter.toModel(entity)

        then: "全フィールドが1対1でコピーされている"
        model != null
        with(model) {
            houseworkTaskAssignmentHistoryId == 1L
            houseworkTaskId == 100L
            householdId == 10L
            fromAssigneeUserId == 1000L
            toAssigneeUserId == 2000L
            operatedUserId == 3000L
            assignReasonType == "1"
            note == "お願いして引き継ぎ"
            changedAt == changedAt
        }
    }

    def "toEntityは引数がnullのときnullを返す"() {
        expect:
        HouseworkTaskAssignmentHistConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへ全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたHouseworkTaskAssignmentHistModel"
        LocalDateTime changedAt = LocalDateTime.of(2025, 3, 15, 21, 45, 0)

        def model = HouseworkTaskAssignmentHistModel.reconstruct(
                2L,                 // houseworkTaskAssignmentHistoryId
                200L,               // houseworkTaskId
                20L,                // householdId
                4000L,              // fromAssigneeUserId
                5000L,              // toAssigneeUserId
                6000L,              // operatedUserId
                "9",                // assignReasonType
                "システムによる自動再割り当て", // note
                changedAt           // changedAt
        )

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = HouseworkTaskAssignmentHistConverter.toEntity(model)

        then: "エンティティが生成され、各フィールドがコピーされている"
        entity != null
        with(entity) {
            houseworkTaskAssignmentHistoryId == 2L
            houseworkTaskId == 200L
            householdId == 20L
            fromAssigneeUserId == 4000L
            toAssigneeUserId == 5000L
            operatedUserId == 6000L
            assignReasonType == "9"
            note == "システムによる自動再割り当て"
        }

        and: "changedAt は DateConverter を介して LocalDateTime と往復しても同じ値になる"
        DateConverter.toLocalDateTime(entity.changedAt) == changedAt
    }
}
