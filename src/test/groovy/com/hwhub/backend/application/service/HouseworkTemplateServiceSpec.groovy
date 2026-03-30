package com.hwhub.backend.application.service

import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateId
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel
import com.hwhub.backend.domain.repository.HouseworkTemplateRepository
import com.hwhub.backend.domain.enums.Category
import com.hwhub.backend.domain.enums.RecurrenceType
import spock.lang.Specification

class HouseworkTemplateServiceSpec extends Specification {

    HouseworkTemplateRepository repository = Mock()
    HouseworkTemplateService service

    def setup() {
        service = new HouseworkTemplateService(repository)
    }

    def "findAll: 期待されるモデルリストがそのまま返ること"() {
        given:
        def model = HouseworkTemplateModel.reconstruct(
                new HouseworkTemplateId(1L), "J", "E", "S",
                null, null, null, null, null, null,
                Category.CLEANING, RecurrenceType.WEEKLY, 127, null, null, null
        )
        def expectedList = [model]

        when:
        def result = service.findAll()

        then:
        1 * repository.findAll() >> expectedList
        result == expectedList
        result.size() == 1
        result[0].nameJa == "J"
    }

    def "findAll: リポジトリが空の場合は空リストを返すこと"() {
        when:
        def result = service.findAll()

        then:
        1 * repository.findAll() >> []
        result == []
    }
}
