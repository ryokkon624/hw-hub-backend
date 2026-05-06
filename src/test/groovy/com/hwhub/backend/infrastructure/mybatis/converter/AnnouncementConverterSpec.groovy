package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.AnnouncementModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MAnnouncement
import spock.lang.Specification

import java.time.LocalDateTime

class AnnouncementConverterSpec extends Specification {

    def "toModel は entity が null の場合 null を返す"() {
        expect:
        AnnouncementConverter.toModel(null) == null
    }

    def "toModel は全フィールドを正しく変換する"() {
        given:
        def entity = new MAnnouncement()
        entity.setAnnouncementId(1L)
        entity.setTitleJa("タイトル（日本語）")
        entity.setTitleEn("Title (English)")
        entity.setTitleEs("Título (Español)")
        entity.setBodyJa("本文（日本語）")
        entity.setBodyEn("Body (English)")
        entity.setBodyEs("Cuerpo (Español)")
        entity.setSeverity("INFO")
        entity.setTargetScope("ALL")
        entity.setStartAt(new Date(1000L))
        entity.setEndAt(new Date(2000L))

        when:
        def model = AnnouncementConverter.toModel(entity)

        then:
        model != null
        with(model) {
            id == 1L
            titleJa == "タイトル（日本語）"
            titleEn == "Title (English)"
            titleEs == "Título (Español)"
            bodyJa == "本文（日本語）"
            bodyEn == "Body (English)"
            bodyEs == "Cuerpo (Español)"
            severity == "INFO"
            targetScope == "ALL"
            startAt != null
            endAt != null
        }
    }

    // ---- toEntity ----

    def "toEntity は model が null の場合に NullPointerException を投げる"() {
        when:
        AnnouncementConverter.toEntity(null)

        then:
        thrown(NullPointerException)
    }

    def "toEntity は全フィールドを正しくエンティティに変換する"() {
        given:
        def startAt = LocalDateTime.of(2030, 1, 1, 0, 0, 0)
        def endAt   = LocalDateTime.of(2030, 12, 31, 23, 59, 59)
        def model = AnnouncementModel.reconstruct(
                10L,
                "タイトル（日本語）",
                "Title (English)",
                "Título (Español)",
                "本文（日本語）",
                "Body (English)",
                "Cuerpo (Español)",
                "INFO",
                "ALL",
                startAt,
                endAt
        )

        when:
        def entity = AnnouncementConverter.toEntity(model)

        then:
        entity != null
        entity.getAnnouncementId() == 10L
        entity.getTitleJa() == "タイトル（日本語）"
        entity.getTitleEn() == "Title (English)"
        entity.getTitleEs() == "Título (Español)"
        entity.getBodyJa() == "本文（日本語）"
        entity.getBodyEn() == "Body (English)"
        entity.getBodyEs() == "Cuerpo (Español)"
        entity.getSeverity() == "INFO"
        entity.getTargetScope() == "ALL"
        entity.getStartAt() != null
        entity.getEndAt() != null
    }
}
