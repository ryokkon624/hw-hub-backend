package com.hwhub.backend.presentation.rest.announcement

import com.hwhub.backend.application.service.announcement.AnnouncementService
import com.hwhub.backend.application.service.announcement.AnnouncementService.AnnouncementSummary
import spock.lang.Specification

import java.time.LocalDateTime

class AnnouncementControllerSpec extends Specification {

    AnnouncementService announcementService = Mock()
    AnnouncementController controller = new AnnouncementController(announcementService)

    def "getActiveAnnouncements は現在日時でサービスを呼び レスポンスを返す"() {
        given:
        def summary1 = new AnnouncementSummary(
                1L,
                "タイトル1", "Title1", "Titulo1",
                "本文1", "Body1", "Cuerpo1",
                "INFO", "ALL",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1)
        )
        def summary2 = new AnnouncementSummary(
                2L,
                "タイトル2", "Title2", "Titulo2",
                "本文2", "Body2", "Cuerpo2",
                "WARN", "HOME",
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().plusDays(2)
        )

        when:
        def response = controller.getActiveAnnouncements()

        then:
        1 * announcementService.getActiveAnnouncements(_ as LocalDateTime) >> [summary1, summary2]
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
