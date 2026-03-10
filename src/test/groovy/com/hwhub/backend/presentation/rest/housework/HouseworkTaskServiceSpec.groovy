package com.hwhub.backend.presentation.rest.housework

import com.hwhub.backend.application.service.HouseworkTaskService
import com.hwhub.backend.domain.model.HouseworkTask4AssignModel
import com.hwhub.backend.presentation.rest.housework.dto.BulkUpdateStatusRequest
import com.hwhub.backend.presentation.rest.housework.dto.HouseworkTaskResponse
import com.hwhub.backend.presentation.rest.housework.dto.UpdateAssigneeRequest
import com.hwhub.backend.presentation.rest.housework.dto.UpdateStatusRequest
import org.springframework.security.core.Authentication
import spock.lang.Specification

class HouseworkTaskControllerSpec extends Specification {

    HouseworkTaskService houseworkTaskService = Mock()

    HouseworkTaskController controller =
            new HouseworkTaskController(houseworkTaskService)

    // -------------------------------------------------
    // getTasks
    // -------------------------------------------------
    def "getTasks は認証ユーザIDを使って HouseworkTaskService.findForAssign を呼び DTO リストを返す"() {
        given:
        Long householdId = 1L
        String status = "0"
        Authentication auth = Mock()
        auth.getName() >> "10"   // loginUserId = 10

        HouseworkTask4AssignModel model = Stub() {
            getHouseworkTaskId() >> 100L
            getHouseholdId() >> householdId
            getHouseworkId() >> 200L
            getName() >> "皿洗い"
            getDescription() >> "夕食後"
            getCategory() >> "KITCHEN"
            getTargetDate() >> java.time.LocalDate.now()
            getAssigneeUserId() >> 20L
            getStatus() >> status
            getAssignReasonType() >> "0"
            getDoneAt() >> null
            getSkippedReason() >> null
            getAssigneeNickname() >> "たろう"
        }

        when:
        def result = controller.getTasks(householdId, status, auth)

        then:
        1 * houseworkTaskService.findForAssign(householdId, status, 10L) >> [model]

        and:
        result != null
        result.size() == 1
        result[0] instanceof HouseworkTaskResponse
        result[0].houseworkTaskId() == 100L
        result[0].assigneeNickname() == "たろう"
    }

    // -------------------------------------------------
    // updateAssignee
    // -------------------------------------------------
    def "updateAssignee はログインユーザIDを使って HouseworkTaskService.updateAssignee を呼ぶ"() {
        given:
        Long taskId = 123L
        Authentication auth = Mock()
        auth.getName() >> "10"

        // ★ ここを Mock → 実インスタンスに変更
        // record だと仮定: public record UpdateAssigneeRequest(Long assigneeUserId, String assignReasonType) {}
        def request = new UpdateAssigneeRequest(20L, "1")

        when:
        controller.updateAssignee(taskId, request, auth)

        then:
        1 * houseworkTaskService.updateAssignee(taskId, 20L, "1", 10L)
    }

    // -------------------------------------------------
    // updateStatus
    // -------------------------------------------------
    def "updateStatus はログインユーザIDを使って HouseworkTaskService.updateStatus を呼ぶ"() {
        given:
        Long taskId = 456L
        Authentication auth = Mock()
        auth.getName() >> "10"

        // ★ ここも Mock → 実インスタンス
        // record だと仮定: public record UpdateStatusRequest(String status, String skippedReason) {}
        def request = new UpdateStatusRequest("1", "体調不良")

        when:
        controller.updateStatus(taskId, request, auth)

        then:
        1 * houseworkTaskService.updateStatus(taskId, "1", "体調不良", 10L)
    }

    // -------------------------------------------------
    // bulkUpdateStatus
    // -------------------------------------------------
    def "bulkUpdateStatus はログインユーザIDを使って HouseworkTaskService.bulkUpdateStatus を呼ぶ"() {
        given:
        Authentication auth = Mock()
        auth.getName() >> "10"

        def request = new BulkUpdateStatusRequest([100L, 101L, 102L], "2", "bulk update")

        when:
        controller.bulkUpdateStatus(request, auth)

        then:
        1 * houseworkTaskService.bulkUpdateStatus([100L, 101L, 102L], "2", "bulk update", 10L)
    }
}
