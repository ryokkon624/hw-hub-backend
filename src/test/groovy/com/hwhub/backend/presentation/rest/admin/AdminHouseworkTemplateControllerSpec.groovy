package com.hwhub.backend.presentation.rest.admin

import com.hwhub.backend.application.service.HouseworkTemplateService
import com.hwhub.backend.domain.enums.Category
import com.hwhub.backend.domain.enums.RecurrenceType
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateId
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel
import com.hwhub.backend.presentation.rest.admin.dto.AdminHouseworkTemplateRequest
import org.springframework.security.core.Authentication
import spock.lang.Specification

class AdminHouseworkTemplateControllerSpec extends Specification {

    HouseworkTemplateService service = Mock()
    AdminHouseworkTemplateController controller
    Authentication authentication = Mock()

    def setup() {
        controller = new AdminHouseworkTemplateController(service)
        authentication.getName() >> "100"
    }

    def "getAll: 全件取得してレスポンスDTOリストに変換すること"() {
        given:
        def model = HouseworkTemplateModel.reconstruct(
                new HouseworkTemplateId(1L), "J", "E", "S",
                null, null, null, null, null, null,
                Category.CLEANING, RecurrenceType.WEEKLY, 127, null, null, null
        )
        service.findAll() >> [model]

        when:
        def result = controller.getAll()

        then:
        result.size() == 1
        result[0].houseworkTemplateId == 1L
        result[0].nameJa == "J"
    }

    def "create: リクエストからモデルを生成し、サービスを呼び出すこと"() {
        given:
        def request = new AdminHouseworkTemplateRequest(
                "J", "E", "S",
                null, null, null, null, null, null,
                "CLEAN", "1", 127, null, null, null
        )
        def createdModel = HouseworkTemplateModel.reconstruct(
                new HouseworkTemplateId(10L), "J", "E", "S",
                null, null, null, null, null, null,
                Category.CLEANING, RecurrenceType.WEEKLY, 127, null, null, null
        )

        when:
        def result = controller.create(request, authentication)

        then:
        1 * service.create(_ as HouseworkTemplateModel, 100L) >> createdModel
        result.houseworkTemplateId == 10L
    }

    def "update: パスパラメータのIDを含めてモデルを生成し、サービスを呼び出すこと"() {
        given:
        def request = new AdminHouseworkTemplateRequest(
                "J", "E", "S",
                null, null, null, null, null, null,
                "CLEAN", "1", 127, null, null, null
        )

        when:
        controller.update(20L, request, authentication)

        then:
        1 * service.update({ it.houseworkTemplateId.value() == 20L }, 100L)
    }

    def "delete: IDをラップしてサービスを呼び出すこと"() {
        when:
        controller.delete(30L, authentication)

        then:
        1 * service.delete(new HouseworkTemplateId(30L), 100L)
    }
}
