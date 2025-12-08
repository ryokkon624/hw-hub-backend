package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.HouseworkTaskAssignmentHistModel
import com.hwhub.backend.domain.model.HouseworkTaskModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTask
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskAssignmentHistory
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class HouseworkTaskConverterSpec extends Specification{

    def "toModelはentityがnullのときnullを返す"() {
        expect:
        HouseworkTaskConverter.toModel(null, Collections.emptyList()) == null
    }

    def "toModelはエンティティと履歴リストからモデルを構築する"() {
        given: "1件のタスクエンティティと1件の履歴エンティティ"
        LocalDate targetDate = LocalDate.of(2025, 4, 1)
        LocalDate doneAt     = LocalDate.of(2025, 4, 2)
        LocalDateTime changedAt = LocalDateTime.of(2025, 4, 1, 12, 0, 0)

        def taskEntity = new THouseworkTask()
        taskEntity.setHouseworkTaskId(1L)
        taskEntity.setHouseholdId(10L)
        taskEntity.setHouseworkId(100L)
        taskEntity.setName("玄関掃除")
        taskEntity.setDescription("ほうきで掃き掃除")
        taskEntity.setCategory("CLEANING")
        taskEntity.setTargetDate(DateConverter.toDate(targetDate))
        taskEntity.setAssigneeUserId(1000L)
        taskEntity.setStatus("0")
        taskEntity.setAssignReasonType("1")
        taskEntity.setDoneAt(DateConverter.toDate(doneAt))
        taskEntity.setSkippedReason("")

        def histEntity = new THouseworkTaskAssignmentHistory()
        histEntity.setHouseworkTaskAssignmentHistoryId(100L)
        histEntity.setHouseworkTaskId(1L)
        histEntity.setHouseholdId(10L)
        histEntity.setFromAssigneeUserId(1000L)
        histEntity.setToAssigneeUserId(2000L)
        histEntity.setOperatedUserId(3000L)
        histEntity.setAssignReasonType("2")
        histEntity.setNote("お願いして交代")
        histEntity.setChangedAt(DateConverter.toDate(changedAt))

        def historyEntities = [histEntity]

        when: "toModelでドメインモデルに変換する"
        def model = HouseworkTaskConverter.toModel(taskEntity, historyEntities)

        then: "タスク本体のプロパティが正しくコピーされている"
        model != null
        with(model) {
            houseworkTaskId == 1L
            householdId == 10L
            houseworkId == 100L
            name == "玄関掃除"
            description == "ほうきで掃き掃除"
            category == "CLEANING"
            targetDate == targetDate
            assigneeUserId == 1000L
            status == "0"
            assignReasonType == "1"
            doneAt == doneAt
            skippedReason == ""
        }

        and: "履歴が1件分変換されている"
        model.histories != null
        model.histories.size() == 1

        and: "履歴の中身も1対1でコピーされている"
        HouseworkTaskAssignmentHistModel histModel = model.histories[0]
        with(histModel) {
            houseworkTaskAssignmentHistoryId == 100L
            houseworkTaskId == 1L
            householdId == 10L
            fromAssigneeUserId == 1000L
            toAssigneeUserId == 2000L
            operatedUserId == 3000L
            assignReasonType == "2"
            note == "お願いして交代"
            changedAt == changedAt
        }
    }

    def "toModelは履歴リストが空の場合historiesも空リストになる"() {
        given: "履歴なしのタスクエンティティ"
        LocalDate targetDate = LocalDate.of(2025, 5, 1)

        def taskEntity = new THouseworkTask()
        taskEntity.setHouseworkTaskId(2L)
        taskEntity.setHouseholdId(20L)
        taskEntity.setHouseworkId(200L)
        taskEntity.setName("ゴミ出し")
        taskEntity.setDescription("燃えるゴミを出す")
        taskEntity.setCategory("TRASH")
        taskEntity.setTargetDate(DateConverter.toDate(targetDate))
        taskEntity.setAssigneeUserId(2000L)
        taskEntity.setStatus("0")
        taskEntity.setAssignReasonType("0")
        taskEntity.setDoneAt(null)
        taskEntity.setSkippedReason(null)

        def historyEntities = Collections.emptyList()

        when: "履歴なしでtoModelを呼び出す"
        def model = HouseworkTaskConverter.toModel(taskEntity, historyEntities)

        then: "タスク本体は変換され、historiesは空リストとなる"
        model != null
        model.histories != null
        model.histories.isEmpty()
    }

    def "toEntityはmodelがnullのときnullを返す"() {
        expect:
        HouseworkTaskConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへ全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたHouseworkTaskModel"
        LocalDate targetDate = LocalDate.of(2025, 6, 1)
        LocalDate doneAt     = LocalDate.of(2025, 6, 2)

        def model = HouseworkTaskModel.reconstruct(
                3L,                  // houseworkTaskId
                30L,                 // householdId
                300L,                // houseworkId
                "ベランダ掃除",         // name
                "デッキブラシで掃除",     // description
                "OUTSIDE",           // category
                targetDate,          // targetDate
                3000L,               // assigneeUserId
                "1",                 // status
                "9",                 // assignReasonType
                doneAt,              // doneAt
                "雨なので後ろ倒し",       // skippedReason
                Collections.emptyList() // histories（toEntityでは使用しない）
        )

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = HouseworkTaskConverter.toEntity(model)

        then: "IDやステータスなどが正しくコピーされている"
        entity != null
        with(entity) {
            houseworkTaskId == 3L
            householdId == 30L
            houseworkId == 300L
            assigneeUserId == 3000L
            status == "1"
            assignReasonType == "9"
            skippedReason == "雨なので後ろ倒し"
        }

        and: "日付フィールドはLocalDateとの相互変換で同じ値になる"
        DateConverter.toLocalDate(entity.targetDate) == targetDate
        DateConverter.toLocalDate(entity.doneAt) == doneAt
    }
}
