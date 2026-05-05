package com.hwhub.backend.application.service

import com.hwhub.backend.domain.model.AnnouncementModel
import com.hwhub.backend.domain.repository.AnnouncementRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AnnouncementServiceSpec extends Specification {

    @Subject
    AnnouncementService announcementService

    AnnouncementRepository announcementRepository = Mock()

    def setup() {
        announcementService = new AnnouncementService(announcementRepository)
    }

    def "getActiveAnnouncements は現在日時を渡してリポジトリを呼び出し結果を返す"() {
        given:
        def now = LocalDateTime.now()
        def model1 = Mock(AnnouncementModel)
        def model2 = Mock(AnnouncementModel)
        model1.id >> 1L
        model1.titleJa >> "タイトル1"
        model1.titleEn >> "Title1"
        model1.titleEs >> "Titulo1"
        model1.bodyJa >> "本文1"
        model1.bodyEn >> "Body1"
        model1.bodyEs >> "Cuerpo1"
        model1.severity >> "INFO"
        model1.targetScope >> "ALL"
        model1.startAt >> now.minusDays(1)
        model1.endAt >> now.plusDays(1)
        model2.id >> 2L
        model2.titleJa >> "タイトル2"
        model2.titleEn >> "Title2"
        model2.titleEs >> "Titulo2"
        model2.bodyJa >> "本文2"
        model2.bodyEn >> "Body2"
        model2.bodyEs >> "Cuerpo2"
        model2.severity >> "WARN"
        model2.targetScope >> "HOME"
        model2.startAt >> now.minusDays(2)
        model2.endAt >> now.plusDays(2)

        when:
        def results = announcementService.getActiveAnnouncements(now)

        then:
        1 * announcementRepository.findActiveAt(now) >> [model1, model2]
        results.size() == 2
    }

    def "getActiveAnnouncements はアクティブなアナウンスが0件の場合 空リストを返す"() {
        given:
        def now = LocalDateTime.now()

        when:
        def results = announcementService.getActiveAnnouncements(now)

        then:
        1 * announcementRepository.findActiveAt(now) >> []
        results.isEmpty()
    }
}
