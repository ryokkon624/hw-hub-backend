package com.hwhub.backend.presentation.rest.household

import com.hwhub.backend.application.service.HouseholdService
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.presentation.rest.household.dto.CreateHouseholdRequest
import com.hwhub.backend.presentation.rest.household.dto.HouseholdDto
import com.hwhub.backend.presentation.rest.household.dto.TransferOwnerRequest
import spock.lang.Specification

class HouseholdControllerSpec extends Specification {

    HouseholdService householdService = Mock()
    HouseholdController controller = new HouseholdController(householdService)

    def "create は世帯を作成し HouseholdDto を返す"() {
        given:
        def request = new CreateHouseholdRequest(name: "New Home")
        Long userId = 100L

        def model = HouseholdModel.reconstruct(1L, "New Home", 100L)

        when:
        HouseholdDto result = controller.create(request, userId)

        then:
        1 * householdService.createHousehold(100L, "New Home") >> model

        and:
        result.householdId == 1L
        result.name == "New Home"
    }

    def "delete は世帯を削除し 204 を返す"() {
        given:
        Long householdId = 1L
        Long userId = 100L

        when:
        def response = controller.delete(householdId, userId)

        then:
        1 * householdService.deleteHousehold(householdId, 100L)

        and:
        // Controller の delete メソッドは void なので戻り値はないが、
        // @ResponseStatus(HttpStatus.NO_CONTENT) がついている。
        // 単体テストで controller.delete() を直呼びした場合、戻り値は null になる。
        response == null
    }

    def "transferOwner はオーナー譲渡をサービスに依頼する"() {
        given:
        Long householdId = 1L
        def request = new TransferOwnerRequest(newOwnerUserId: 200L)
        Long userId = 100L

        when:
        controller.transferOwner(householdId, request, userId)

        then:
        1 * householdService.transferOwnership(householdId, 100L, 200L)
    }
}
