package com.hwhub.backend.presentation.rest.housework

import com.hwhub.backend.application.service.HouseworkTemplateService
import com.hwhub.backend.domain.enums.Category
import com.hwhub.backend.domain.enums.RecurrenceType
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateId
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel
import com.hwhub.backend.presentation.rest.housework.dto.HouseworkTemplateResponse
import spock.lang.Specification

class HouseworkTemplateControllerSpec extends Specification {

    HouseworkTemplateService service = Mock()
    HouseworkTemplateController controller

    def setup() {
        controller = new HouseworkTemplateController(service)
    }

    def "getAll: Service から取得したモデルリストが DTO リストに変換されて返ること"() {
        given:
        def model = HouseworkTemplateModel.reconstruct(
                new HouseworkTemplateId(1L), "J", "E", "S",
                "DJ", "DE", "DS", "RJ", "RE", "RS",
                Category.CLEANING, RecurrenceType.WEEKLY, 127, null, null, null
        )
        def modelList = [model]

        when:
        List<HouseworkTemplateResponse> result = controller.getAll()

        then:
        1 * service.findAll() >> modelList
        result.size() == 1
        result[0].houseworkTemplateId() == 1L
        result[0].nameJa() == "J"
        result[0].category() == "CLEAN"
        result[0].recurrenceType() == "1"
        result[0].weeklyDays() == 127
    }

    def "getAll: Service が空リストを返す場合、空リストが返ること"() {
        when:
        def result = controller.getAll()

        then:
        1 * service.findAll() >> []
        result == []
    }
}
