package com.hwhub.backend.presentation.rest.admin

import com.hwhub.backend.application.service.announcement.AdminAnnouncementService
import com.hwhub.backend.application.service.announcement.AnnouncementSummary
import com.hwhub.backend.domain.model.AnnouncementModel
import com.hwhub.backend.presentation.rest.admin.announcement.AdminAnnouncementController
import com.hwhub.backend.presentation.rest.admin.announcement.dto.AdminAnnouncementRequest
import spock.lang.Specification

import java.time.LocalDateTime

class AdminAnnouncementControllerSpec extends Specification {

    AdminAnnouncementService service = Mock()
    AdminAnnouncementController controller

    def now = LocalDateTime.now()
    def start = now.plusDays(1)
    def end = now.plusDays(10)

    def setup() {
        controller = new AdminAnnouncementController(service)
    }

    def buildSummary(Long id) {
        new AnnouncementSummary(id,
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                start, end)
    }

    def "getAll: サービスのgetAllを呼び出しレスポンスリストを返すこと"() {
        given:
        service.getAll() >> [buildSummary(1L), buildSummary(2L)]

        when:
        def result = controller.getAll()

        then:
        result.size() == 2
        result[0].id == 1L
        result[1].id == 2L
    }

    def "getById: サービスのgetByIdを呼び出しレスポンスを返すこと"() {
        given:
        service.getById(10L) >> buildSummary(10L)

        when:
        def result = controller.getById(10L)

        then:
        result.id == 10L
        result.titleJa == "タイトル"
    }

    def "create: リクエストからモデルを生成しサービスを呼び出すこと"() {
        given:
        Long operatorUserId = 100L
        def request = new AdminAnnouncementRequest(
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                start, end
        )
        service.create(_ as AnnouncementModel, operatorUserId) >> buildSummary(1L)

        when:
        def result = controller.create(request, operatorUserId)

        then:
        result.id == 1L
    }

    def "update: リクエストからモデルを生成しサービスを呼び出すこと"() {
        given:
        Long operatorUserId = 100L
        def request = new AdminAnnouncementRequest(
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                start, end
        )

        when:
        controller.update(5L, request, operatorUserId)

        then:
        1 * service.update({ it.id == 5L }, operatorUserId)
    }

    def "delete: 指定IDでサービスを呼び出すこと"() {
        given:
        Long operatorUserId = 100L

        when:
        controller.delete(7L, operatorUserId)

        then:
        1 * service.delete(7L, operatorUserId)
    }
}
