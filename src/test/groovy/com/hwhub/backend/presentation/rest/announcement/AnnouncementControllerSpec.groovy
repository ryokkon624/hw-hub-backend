package com.hwhub.backend.presentation.rest.announcement

import com.hwhub.backend.application.service.announcement.AnnouncementService
import com.hwhub.backend.domain.model.AnnouncementModel
import spock.lang.Specification

import java.time.LocalDateTime

class AnnouncementControllerSpec extends Specification {

    AnnouncementService announcementService = Mock()
    AnnouncementController controller = new AnnouncementController(announcementService)

    def buildModel(Long id, String severity) {
        AnnouncementModel.reconstruct(
                id,
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                severity, "ALL",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1)
        )
    }

    def "getActiveAnnouncements は現在日時でサービスを呼び レスポンスを返す"() {
        given:
        def model1 = buildModel(1L, "INFO")
        def model2 = buildModel(2L, "WARN")

        when:
        def response = controller.getActiveAnnouncements()

        then:
        1 * announcementService.getActiveAnnouncements(_ as LocalDateTime) >> [model1, model2]
        response.announcements.size() == 2
        response.announcements[0].id == 1L
        response.announcements[0].severity == "INFO"
        response.announcements[1].id == 2L
        response.announcements[1].severity == "WARN"
    }

    def "getActiveAnnouncements はアクティブなアナウンスが0件の場合 空リストを返す"() {
        when:
        def response = controller.getActiveAnnouncements()

        then:
        1 * announcementService.getActiveAnnouncements(_ as LocalDateTime) >> []
        response.announcements.isEmpty()
    }
}
