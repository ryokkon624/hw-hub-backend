package com.hwhub.backend.application.service.announcement

import com.hwhub.backend.domain.model.AnnouncementModel
import com.hwhub.backend.domain.repository.AnnouncementRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AdminAnnouncementServiceSpec extends Specification {

    AnnouncementRepository repository = Mock()

    @Subject
    AdminAnnouncementService service

    def now = LocalDateTime.now()
    def start = now.plusDays(1)
    def end = now.plusDays(10)

    def setup() {
        service = new AdminAnnouncementService(repository)
    }

    def buildModel(Long id) {
        AnnouncementModel.reconstruct(
                id,
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                start, end
        )
    }

    def "getAll: リポジトリのfindAllを呼び出し AnnouncementSummaryのリストを返すこと"() {
        given:
        def model1 = buildModel(1L)
        def model2 = buildModel(2L)
        repository.findAll() >> [model1, model2]

        when:
        def result = service.getAll()

        then:
        result.size() == 2
        result[0].id == 1L
        result[1].id == 2L
    }

    def "getAll: アナウンスが0件の場合 空リストを返すこと"() {
        given:
        repository.findAll() >> []

        when:
        def result = service.getAll()

        then:
        result.isEmpty()
    }

    def "getById: 指定IDのアナウンスをAnnouncementSummaryで返すこと"() {
        given:
        def model = buildModel(10L)
        repository.findById(10L) >> Optional.of(model)

        when:
        def result = service.getById(10L)

        then:
        result.id == 10L
        result.titleJa == "タイトル"
    }

    def "getById: 存在しないIDを指定した場合 IllegalArgumentExceptionをスローすること"() {
        given:
        repository.findById(999L) >> Optional.empty()

        when:
        service.getById(999L)

        then:
        thrown(IllegalArgumentException)
    }

    def "create: AnnouncementModelをリポジトリに登録しAnnouncementSummaryを返すこと"() {
        given:
        Long operatorUserId = 100L
        def model = AnnouncementModel.create(
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                start, end
        )
        def savedModel = buildModel(1L)

        when:
        def result = service.create(model, operatorUserId)

        then:
        1 * repository.insert(model, operatorUserId, _) >> savedModel
        result.id == 1L
    }

    def "update: AnnouncementModelをリポジトリで更新すること"() {
        given:
        Long operatorUserId = 100L
        def model = buildModel(5L)

        when:
        service.update(model, operatorUserId)

        then:
        1 * repository.update(model, operatorUserId, _)
    }

    def "delete: 指定IDのアナウンスをリポジトリから削除すること"() {
        given:
        Long operatorUserId = 100L

        when:
        service.delete(7L, operatorUserId)

        then:
        1 * repository.delete(7L, operatorUserId, _)
    }
}
