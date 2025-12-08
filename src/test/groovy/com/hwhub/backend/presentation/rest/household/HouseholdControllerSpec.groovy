package com.hwhub.backend.presentation.rest.household

import com.hwhub.backend.application.service.HouseholdService
import com.hwhub.backend.domain.model.HouseholdModel
import com.hwhub.backend.presentation.rest.household.dto.CreateHouseholdRequest
import org.springframework.security.core.Authentication
import spock.lang.Specification

class HouseholdControllerSpec extends Specification {

    HouseholdService householdService = Mock()
    HouseholdController controller = new HouseholdController(householdService)

    def "create は Authentication から userId を取得して HouseholdService.createHousehold を呼び HouseholdDto を返す"() {
        given:
        def request = new CreateHouseholdRequest()
        request.setName("My Household")

        Authentication auth = Mock()
        auth.getName() >> "10"   // principal に userId が入っている想定

        def model = HouseholdModel.reconstruct(1L, "My Household", 10L)

        when:
        def response = controller.create(request, auth)

        then:
        1 * householdService.createHousehold(10L, "My Household") >> model

        and:
        response != null
        // HouseholdDto のフィールド名に合わせてここは調整してください
        response.householdId == 1L
        response.name == "My Household"
    }
}
