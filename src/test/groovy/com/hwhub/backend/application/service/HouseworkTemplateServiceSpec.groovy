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

    def "create: リポジトリの insert が呼ばれ、結果が返ること"() {
        given:
        def model = HouseworkTemplateModel.reconstruct(
                null, "J", "E", "S",
                null, null, null, null, null, null,
                Category.CLEANING, RecurrenceType.WEEKLY, 127, null, null, null
        )
        def createdModel = HouseworkTemplateModel.reconstruct(
                new HouseworkTemplateId(10L), "J", "E", "S",
                null, null, null, null, null, null,
                Category.CLEANING, RecurrenceType.WEEKLY, 127, null, null, null
        )
        def operatorUserId = 100L

        when:
        def result = service.create(model, operatorUserId)

        then:
        1 * repository.insert(model, operatorUserId, "OnlAdmHwTp") >> createdModel
        result == createdModel
    }

    def "update: リポジトリの update が呼ばれること"() {
        given:
        def model = HouseworkTemplateModel.reconstruct(
                new HouseworkTemplateId(10L), "J", "E", "S",
                null, null, null, null, null, null,
                Category.CLEANING, RecurrenceType.WEEKLY, 127, null, null, null
        )
        def operatorUserId = 100L

        when:
        service.update(model, operatorUserId)

        then:
        1 * repository.update(model, operatorUserId, "OnlAdmHwTp")
    }

    def "delete: リポジトリの delete が呼ばれること"() {
        given:
        def id = new HouseworkTemplateId(20L)
        def operatorUserId = 100L

        when:
        service.delete(id, operatorUserId)

        then:
        1 * repository.delete(id, operatorUserId, "OnlAdmHwTp")
    }
}
