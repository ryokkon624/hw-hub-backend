package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.enums.TaskStatus
import com.hwhub.backend.domain.model.HouseworkTask4AssignModel
import com.hwhub.backend.domain.model.HouseworkTaskModel
import com.hwhub.backend.domain.repository.HouseworkTaskRepository
import org.springframework.security.access.AccessDeniedException
import com.hwhub.backend.application.service.notification.NotificationPublisher
import spock.lang.Specification

class HouseworkTaskServiceSpec extends Specification{

    HouseworkTaskRepository taskRepository = Mock()
    HouseholdAuthorizationService authService = Mock()
    NotificationPublisher notificationPublisher = Mock()

    HouseworkTaskService service = new HouseworkTaskService(taskRepository, authService, notificationPublisher)

    // ==================================
    // findForAssign
    // ==================================

    def "findForAssignはアクセス権がない場合AccessDeniedExceptionを投げる"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        String status = TaskStatus.NOT_DONE.code  // 仮：実際のコード値に合わせてください

        when:
        service.findForAssign(householdId, status, userId)

        then:
        1 * authService.canAccessHousehold(householdId, userId) >> false
        0 * taskRepository.findForAssign(_, _, _)
        thrown(AccessDeniedException)
    }

    def "findForAssignはアクセス権がある場合タスク一覧を返す"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        String status = TaskStatus.NOT_DONE.code  // 仮

        def tasks = [Mock(HouseworkTask4AssignModel)]

        when:
        def result = service.findForAssign(householdId, status, userId)

        then:
        1 * authService.canAccessHousehold(householdId, userId) >> true
        1 * taskRepository.findForAssign(householdId, status, _ as java.time.LocalDate) >> tasks
        result == tasks
    }

    // ==================================
    // updateAssignee
    // ==================================

    def "updateAssigneeはタスクが存在しない場合IllegalArgumentExceptionを投げる"() {
        given:
        Long taskId = 100L

        when:
        service.updateAssignee(taskId, 20L, "1", 99L)

        then:
        1 * taskRepository.findById(taskId) >> null
        thrown(IllegalArgumentException)
    }

    def "updateAssigneeはログインユーザが世帯メンバーでない場合AccessDeniedExceptionを投げる"() {
        given:
        Long taskId = 101L
        Long householdId = 1L
        Long loginUserId = 99L
        Long assigneeUserId = 20L

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateAssignee(taskId, assigneeUserId, "1", loginUserId)

        then:
        1 * taskRepository.findById(taskId) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> false
        0 * authService.canAccessHousehold(householdId, assigneeUserId)
        0 * model.changeAssignee(_, _, _)
        0 * taskRepository.update(_, _, _)
        thrown(AccessDeniedException)
    }

    def "updateAssigneeは担当者が世帯メンバーでない場合AccessDeniedExceptionを投げる"() {
        given:
        Long taskId = 102L
        Long householdId = 1L
        Long loginUserId = 99L
        Long assigneeUserId = 20L

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateAssignee(taskId, assigneeUserId, "1", loginUserId)

        then:
        1 * taskRepository.findById(taskId) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> true
        1 * authService.canAccessHousehold(householdId, assigneeUserId) >> false
        0 * model.changeAssignee(_, _, _)
        0 * taskRepository.update(_, _, _)
        thrown(AccessDeniedException)
    }

    def "updateAssigneeは担当者がnullでもログインユーザが世帯メンバーなら担当変更して更新する"() {
        given:
        Long taskId = 103L
        Long householdId = 1L
        Long loginUserId = 99L
        Long assigneeUserId = null
        String reasonType = "1"

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateAssignee(taskId, assigneeUserId, reasonType, loginUserId)

        then:
        1 * taskRepository.findById(taskId) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> true
        // assigneeUserId が null なのでここは呼ばれない
        0 * authService.canAccessHousehold(householdId, _ as Long)

        1 * model.changeAssignee(null, reasonType, loginUserId)
        1 * taskRepository.update(model, loginUserId, ProgramType.ONL_HWRTSK.code)
        1 * notificationPublisher.publishTaskAssignedEvent(model, _, loginUserId, ProgramType.ONL_HWRTSK.code)
    }

    def "updateAssigneeは担当者が世帯メンバーのとき担当変更して更新する"() {
        given:
        Long taskId = 104L
        Long householdId = 1L
        Long loginUserId = 99L
        Long assigneeUserId = 20L
        String reasonType = "1"

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateAssignee(taskId, assigneeUserId, reasonType, loginUserId)

        then:
        1 * taskRepository.findById(taskId) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> true
        1 * authService.canAccessHousehold(householdId, assigneeUserId) >> true

        1 * model.changeAssignee(assigneeUserId, reasonType, loginUserId)
        1 * taskRepository.update(model, loginUserId, ProgramType.ONL_HWRTSK.code)
        1 * notificationPublisher.publishTaskAssignedEvent(model, _, loginUserId, ProgramType.ONL_HWRTSK.code)
    }

    // ==================================
    // updateStatus
    // ==================================

    def "updateStatusはタスクが存在しない場合IllegalArgumentExceptionを投げる"() {
        given:
        Long taskId = 200L

        when:
        service.updateStatus(taskId, TaskStatus.DONE.code, null, 99L)

        then:
        1 * taskRepository.findById(taskId) >> null
        thrown(IllegalArgumentException)
    }

    def "updateStatusはログインユーザが世帯メンバーでない場合AccessDeniedExceptionを投げる"() {
        given:
        Long taskId = 201L
        Long householdId = 1L
        Long loginUserId = 99L

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateStatus(taskId, TaskStatus.DONE.code, null, loginUserId)

        then:
        1 * taskRepository.findById(taskId) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> false
        0 * model.complete()
        0 * model.skip(_)
        0 * taskRepository.update(_, _, _)
        thrown(AccessDeniedException)
    }

    def "updateStatusはDONEのときcompleteを呼び出し更新する"() {
        given:
        Long taskId = 202L
        Long householdId = 1L
        Long loginUserId = 99L

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateStatus(taskId, TaskStatus.DONE.code, null, loginUserId)

        then:
        1 * taskRepository.findById(taskId) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> true

        1 * model.complete()
        0 * model.skip(_)

        1 * taskRepository.update(model, loginUserId, ProgramType.ONL_HWRTSK.code)
    }

    def "updateStatusはSKIPPEDのときskipを呼び出し更新する"() {
        given:
        Long taskId = 203L
        Long householdId = 1L
        Long loginUserId = 99L
        String reason = "雨でできなかった"

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateStatus(taskId, TaskStatus.SKIPPED.code, reason, loginUserId)

        then:
        1 * taskRepository.findById(taskId) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> true

        0 * model.complete()
        1 * model.skip(reason)

        1 * taskRepository.update(model, loginUserId, ProgramType.ONL_HWRTSK.code)
    }

    def "updateStatusはNOT_DONEなど未対応ステータスのときIllegalArgumentExceptionを投げる（default分岐）"() {
        given:
        Long taskId = 204L
        Long householdId = 1L
        Long loginUserId = 99L

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.updateStatus(taskId, TaskStatus.NOT_DONE.code, null, loginUserId)

        then:
        1 * taskRepository.findById(taskId) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> true

        0 * model.complete()
        0 * model.skip(_)
        0 * taskRepository.update(_, _, _)

        def ex = thrown(IllegalArgumentException)
        ex.message.contains("Unprocessable status")
    }
    // ==================================
    // bulkUpdateStatus
    // ==================================

    def "bulkUpdateStatusはtaskIdsが空の場合何もせず即リターンする"() {
        when:
        service.bulkUpdateStatus([], TaskStatus.SKIPPED.code, "bulk update", 99L)

        then:
        0 * taskRepository.findById(_)
        0 * authService.canAccessHousehold(_, _)
        0 * taskRepository.bulkUpdateStatus(_, _, _, _, _, _)
    }

    def "bulkUpdateStatusは先頭タスクが存在しない場合IllegalArgumentExceptionを投げる"() {
        given:
        def taskIds = [300L, 301L]

        when:
        service.bulkUpdateStatus(taskIds, TaskStatus.SKIPPED.code, "bulk update", 99L)

        then:
        1 * taskRepository.findById(300L) >> null
        thrown(IllegalArgumentException)
    }

    def "bulkUpdateStatusはログインユーザが世帯メンバーでない場合AccessDeniedExceptionを投げる"() {
        given:
        def taskIds = [300L, 301L]
        Long householdId = 1L
        Long loginUserId = 99L

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.bulkUpdateStatus(taskIds, TaskStatus.SKIPPED.code, "bulk update", loginUserId)

        then:
        1 * taskRepository.findById(300L) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> false
        0 * taskRepository.bulkUpdateStatus(_, _, _, _, _, _)
        thrown(AccessDeniedException)
    }

    def "bulkUpdateStatusは他世帯のタスクIDが混在する場合AccessDeniedExceptionを投げる"() {
        given:
        def taskIds = [300L, 301L, 302L]
        Long householdId = 1L
        Long loginUserId = 99L

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.bulkUpdateStatus(taskIds, TaskStatus.SKIPPED.code, "bulk update", loginUserId)

        then:
        1 * taskRepository.findById(300L) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> true
        1 * taskRepository.countByIdsAndHouseholdId(taskIds, householdId) >> 2L
        0 * taskRepository.bulkUpdateStatus(_, _, _, _, _, _)
        thrown(AccessDeniedException)
    }

    def "bulkUpdateStatusはSKIPPEDのとき一括更新を呼び出す（doneAtがnullでない）"() {
        given:
        def taskIds = [300L, 301L, 302L]
        Long householdId = 1L
        Long loginUserId = 99L
        String reason = "bulk update"

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.bulkUpdateStatus(taskIds, TaskStatus.SKIPPED.code, reason, loginUserId)

        then:
        1 * taskRepository.findById(300L) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> true
        1 * taskRepository.countByIdsAndHouseholdId(taskIds, householdId) >> 3L
        1 * taskRepository.bulkUpdateStatus(taskIds, TaskStatus.SKIPPED.code, reason, { it != null }, loginUserId, ProgramType.ONL_HWRTSK.code)
    }

    def "bulkUpdateStatusはDONEのとき一括更新を呼び出す（doneAtがnullでない）"() {
        given:
        def taskIds = [400L, 401L]
        Long householdId = 1L
        Long loginUserId = 99L

        def model = Mock(HouseworkTaskModel) {
            getHouseholdId() >> householdId
        }

        when:
        service.bulkUpdateStatus(taskIds, TaskStatus.DONE.code, null, loginUserId)

        then:
        1 * taskRepository.findById(400L) >> model
        1 * authService.canAccessHousehold(householdId, loginUserId) >> true
        1 * taskRepository.countByIdsAndHouseholdId(taskIds, householdId) >> 2L
        1 * taskRepository.bulkUpdateStatus(taskIds, TaskStatus.DONE.code, null, { it != null }, loginUserId, ProgramType.ONL_HWRTSK.code)
    }

}
