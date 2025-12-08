package com.hwhub.backend.domain.model

import spock.lang.Specification

import java.time.LocalDateTime

class HouseworkTaskAssignmentHistModelSpec extends Specification {

    def "createはIDがnullのインスタンスを生成する"() {
        given: "入力値を用意する"
        Long houseworkTaskId = 10L
        Long householdId = 20L
        Long fromAssigneeUserId = 1L
        Long toAssigneeUserId = 2L
        Long operatedUserId = 3L
        String assignReasonType = "01"
        String note = "メモ"
        LocalDateTime changedAt = LocalDateTime.of(2024, 1, 2, 3, 4, 5)

        when: "createでインスタンスを生成する"
        def model = HouseworkTaskAssignmentHistModel.create(
                houseworkTaskId,
                householdId,
                fromAssigneeUserId,
                toAssigneeUserId,
                operatedUserId,
                assignReasonType,
                note,
                changedAt
        )

        then: "ID以外の値がそのまま設定され、IDはnullになる"
        model.houseworkTaskAssignmentHistoryId == null
        model.houseworkTaskId == houseworkTaskId
        model.householdId == householdId
        model.fromAssigneeUserId == fromAssigneeUserId
        model.toAssigneeUserId == toAssigneeUserId
        model.operatedUserId == operatedUserId
        model.assignReasonType == assignReasonType
        model.note == note
        model.changedAt == changedAt
    }

    def "reconstructは全てのプロパティに値を設定してインスタンスを生成する"() {
        given: "入力値を用意する"
        Long historyId = 99L
        Long houseworkTaskId = 10L
        Long householdId = 20L
        Long fromAssigneeUserId = 1L
        Long toAssigneeUserId = 2L
        Long operatedUserId = 3L
        String assignReasonType = "02"
        String note = "再構築メモ"
        LocalDateTime changedAt = LocalDateTime.of(2025, 5, 6, 7, 8, 9)

        when: "reconstructでインスタンスを生成する"
        def model = HouseworkTaskAssignmentHistModel.reconstruct(
                historyId,
                houseworkTaskId,
                householdId,
                fromAssigneeUserId,
                toAssigneeUserId,
                operatedUserId,
                assignReasonType,
                note,
                changedAt
        )

        then: "全てのプロパティが引数通りに設定される"
        model.houseworkTaskAssignmentHistoryId == historyId
        model.houseworkTaskId == houseworkTaskId
        model.householdId == householdId
        model.fromAssigneeUserId == fromAssigneeUserId
        model.toAssigneeUserId == toAssigneeUserId
        model.operatedUserId == operatedUserId
        model.assignReasonType == assignReasonType
        model.note == note
        model.changedAt == changedAt
    }
}
