package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MAnnouncement
import spock.lang.Specification

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
}
