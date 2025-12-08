package com.hwhub.backend.domain.model

import com.hwhub.backend.domain.enums.TaskStatus
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class HouseworkTaskModelSpec extends Specification {

    def "reconstructは引数で渡した値をそのままプロパティに設定する"() {
        given: "各種プロパティ値を用意する"
        Long houseworkTaskId = 1L
        Long householdId = 10L
        Long houseworkId = 100L
        String name = "猫トイレ掃除"
        String description = "砂を替える"
        String category = "PET"
        LocalDate targetDate = LocalDate.of(2024, 1, 2)
        Long assigneeUserId = 999L
        String status = TaskStatus.NOT_DONE.code
        String assignReasonType = "01"
        LocalDate doneAt = LocalDate.of(2024, 1, 3)
        String skippedReason = "体調不良"
        def histories = [Mock(HouseworkTaskAssignmentHistModel)]

        when: "reconstructでインスタンスを生成する"
        def model = HouseworkTaskModel.reconstruct(
                houseworkTaskId,
                householdId,
                houseworkId,
                name,
                description,
                category,
                targetDate,
                assigneeUserId,
                status,
                assignReasonType,
                doneAt,
                skippedReason,
                histories
        )

        then: "全てのプロパティがそのまま設定されている"
        model.houseworkTaskId == houseworkTaskId
        model.householdId == householdId
        model.houseworkId == houseworkId
        model.name == name
        model.description == description
        model.category == category
        model.targetDate == targetDate
        model.assigneeUserId == assigneeUserId
        model.status == status
        model.assignReasonType == assignReasonType
        model.doneAt == doneAt
        model.skippedReason == skippedReason
        model.histories == histories
    }

    def "changeAssigneeは担当者が変わったとき履歴を追加しプロパティを更新する"() {
        given: "既存担当者と履歴リストを持つタスクを用意する"
        def histories = new ArrayList<HouseworkTaskAssignmentHistModel>()

        Long houseworkTaskId = 1L
        Long householdId = 10L
        Long houseworkId = 100L
        Long currentAssignee = 1L
        Long newAssignee = 2L
        String reasonType = "01"
        Long operator = 999L

        def model = HouseworkTaskModel.reconstruct(
                houseworkTaskId,
                householdId,
                houseworkId,
                "タスク名",
                "説明",
                "CAT",
                LocalDate.of(2024, 1, 2),
                currentAssignee,
                TaskStatus.NOT_DONE.code,
                "00",
                null,
                null,
                histories
        )

        when: "別のユーザに担当変更する"
        model.changeAssignee(newAssignee, reasonType, operator)

        then: "担当者・理由区分が更新される"
        model.assigneeUserId == newAssignee
        model.assignReasonType == reasonType

        and: "履歴が1件追加されている"
        histories.size() == 1
        def hist = histories[0]
        hist.houseworkTaskAssignmentHistoryId == null
        hist.houseworkTaskId == houseworkTaskId
        hist.householdId == householdId
        hist.fromAssigneeUserId == currentAssignee
        hist.toAssigneeUserId == newAssignee
        hist.operatedUserId == operator
        hist.assignReasonType == reasonType
        hist.note == null
        hist.changedAt != null
    }
    
    def "changeAssigneeは担当者が変わらない場合履歴を追加しない"() {
        given: "担当者IDが既に設定されているタスク"
        def histories = new ArrayList<HouseworkTaskAssignmentHistModel>()
        def model = HouseworkTaskModel.reconstruct(
                1L,
                10L,
                100L,
                "タスク名",
                "説明",
                "CAT",
                LocalDate.of(2024, 1, 2),
                1L,                           // 既存の担当者
                TaskStatus.NOT_DONE.code,
                "00",
                null,
                null,
                histories
        )

        when: "同じ担当者IDでchangeAssigneeを呼ぶ"
        model.changeAssignee(1L, "02", 999L)

        then: "担当者・理由区分は更新されるが"
        model.assigneeUserId == 1L
        model.assignReasonType == "02"

        and: "履歴は追加されない"
        histories.isEmpty()
    }

    def "completeはステータスをDONEにしdoneAtを今日の日付に設定する"() {
        given: "NOT_DONE状態のタスク"
        def model = HouseworkTaskModel.reconstruct(
                1L,
                10L,
                100L,
                "タスク名",
                "説明",
                "CAT",
                LocalDate.of(2024, 1, 2),
                1L,
                TaskStatus.NOT_DONE.code,
                "00",
                null,
                null,
                new ArrayList<>()
        )

        when: "completeを呼び出す"
        model.complete()

        then: "ステータスがDONEになる"
        model.status == TaskStatus.DONE.code

        and: "doneAtが「今日」に設定される"
        model.doneAt == LocalDate.now()
    }

    def "skipはステータスをSKIPPEDにし理由とdoneAtを設定する"() {
        given: "NOT_DONE状態のタスク"
        def model = HouseworkTaskModel.reconstruct(
                1L,
                10L,
                100L,
                "タスク名",
                "説明",
                "CAT",
                LocalDate.of(2024, 1, 2),
                1L,
                TaskStatus.NOT_DONE.code,
                "00",
                null,
                null,
                new ArrayList<>()
        )
        String reason = "外出中のため"

        when: "skipを呼び出す"
        model.skip(reason)

        then: "ステータスがSKIPPEDになる"
        model.status == TaskStatus.SKIPPED.code

        and: "スキップ理由とdoneAtが設定される"
        model.skippedReason == reason
        model.doneAt == LocalDate.now()
    }
}
