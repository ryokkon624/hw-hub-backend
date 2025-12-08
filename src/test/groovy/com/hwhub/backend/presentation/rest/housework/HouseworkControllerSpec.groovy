package com.hwhub.backend.presentation.rest.housework

import com.hwhub.backend.application.service.HouseholdAuthorizationService
import com.hwhub.backend.application.service.HouseworkService
import com.hwhub.backend.domain.model.HouseworkModel
import com.hwhub.backend.presentation.rest.housework.dto.HouseworkDto
import com.hwhub.backend.presentation.rest.housework.dto.HouseworkSaveRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import spock.lang.Specification

import java.time.LocalDate

class HouseworkControllerSpec extends Specification {

    HouseworkService houseworkService = Mock()
    HouseholdAuthorizationService householdAuthorizationService = Mock()

    HouseworkController controller =
            new HouseworkController(houseworkService, householdAuthorizationService)

    // -------------------------------------------------
    // list
    // -------------------------------------------------
    def "list は認可チェックを行い HouseworkService.listByHousehold の結果を DTO に変換して返す"() {
        given:
        Long householdId = 1L
        Authentication auth = Mock()
        auth.getName() >> "10" // loginUserId=10

        def model = HouseworkModel.reconstruct(
                100L,
                householdId,
                "掃除",
                "部屋の掃除",
                "CLEAN",
                "1",
                0b0101010,
                null,
                null,
                null,
                LocalDate.now(),
                null,
                null
        )

        when:
        List<HouseworkDto> result = controller.list(householdId, auth)

        then:
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, 10L)
        1 * houseworkService.listByHousehold(householdId) >> [model]

        and:
        result != null
        result.size() == 1
    }

    // -------------------------------------------------
    // getOne
    // -------------------------------------------------
    def "getOne は HouseworkService.findById で取得し世帯認可チェック後 DTO を返す"() {
        given:
        Long houseworkId = 100L
        Long householdId = 1L
        Authentication auth = Mock()
        auth.getName() >> "10"

        def model = HouseworkModel.reconstruct(
                houseworkId,
                householdId,
                "洗濯",
                "毎日の洗濯",
                "CLEAN",
                "1",
                0b0000010,
                null,
                null,
                null,
                LocalDate.now(),
                null,
                null
        )

        when:
        HouseworkDto dto = controller.getOne(houseworkId, auth)

        then:
        1 * houseworkService.findById(houseworkId) >> model
        1 * householdAuthorizationService.assertUserBelongsToHousehold(householdId, 10L)

        and:
        dto != null
    }

    // -------------------------------------------------
    // createHousework
    // -------------------------------------------------
    def "createHousework は request.toModelForCreate で Model を作り HouseworkService.createHousework を呼んで 201 を返す"() {
        given:
        Authentication auth = Mock()
        auth.getName() >> "10"

        // request は Mock にして toModelForCreate の戻りだけ制御する
        HouseworkSaveRequest request = Mock()

        def inputModel = HouseworkModel.create(
                1L,
                "皿洗い",
                "食後にやる",
                "KITCHEN",
                "1",
                0b0001110,
                null,
                null,
                null,
                LocalDate.now(),
                null,
                null
        )

        def createdModel = HouseworkModel.reconstruct(
                200L,
                inputModel.householdId,
                inputModel.name,
                inputModel.description,
                inputModel.category,
                inputModel.recurrenceType,
                inputModel.weeklyDays,
                inputModel.dayOfMonth,
                inputModel.nthWeek,
                inputModel.weekday,
                inputModel.startDate,
                inputModel.endDate,
                inputModel.defaultAssigneeUserId
        )

        when:
        HouseworkDto dto = controller.createHousework(request, auth)

        then:
        1 * request.toModelForCreate() >> inputModel
        1 * houseworkService.createHousework(inputModel, 10L) >> createdModel

        and:
        dto != null
        // ステータスコードは @ResponseStatus(HttpStatus.CREATED) なのでコントローラの戻り値自体には出てこない
    }

    // -------------------------------------------------
    // updateHousework
    // -------------------------------------------------
    def "updateHousework は認可チェック後 HouseworkService.updateHousework を呼び DTO を返す"() {
        given:
        Long houseworkId = 300L
        Authentication auth = Mock()
        auth.getName() >> "10"

        HouseworkSaveRequest request = Mock()

        // householdId は認可チェックで使われる
        request.getHouseholdId() >> 1L

        def inputModel = HouseworkModel.create(
                1L,
                "ゴミ出し",
                "月水金",
                "GARBAGE",
                "1",
                0b0101010,
                null,
                null,
                null,
                LocalDate.now(),
                null,
                null
        )

        def updatedModel = HouseworkModel.reconstruct(
                houseworkId,
                inputModel.householdId,
                inputModel.name,
                inputModel.description,
                inputModel.category,
                inputModel.recurrenceType,
                inputModel.weeklyDays,
                inputModel.dayOfMonth,
                inputModel.nthWeek,
                inputModel.weekday,
                inputModel.startDate,
                inputModel.endDate,
                inputModel.defaultAssigneeUserId
        )

        when:
        HouseworkDto dto = controller.updateHousework(houseworkId, request, auth)

        then:
        1 * householdAuthorizationService.assertUserBelongsToHousehold(1L, 10L)
        1 * request.toModelForCreate() >> inputModel
        1 * houseworkService.updateHousework(houseworkId, inputModel, 10L) >> updatedModel

        and:
        dto != null
    }

    // -------------------------------------------------
    // deleteHousework
    // -------------------------------------------------
    def "deleteHousework は HouseworkService.deleteHousework を呼び 204 を返す"() {
        given:
        Long houseworkId = 400L

        when:
        controller.deleteHousework(houseworkId)

        then:
        1 * houseworkService.deleteHousework(houseworkId)
        // @ResponseStatus(NO_CONTENT) なので戻り値は void
    }
}
