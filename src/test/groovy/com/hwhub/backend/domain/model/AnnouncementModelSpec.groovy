package com.hwhub.backend.domain.model

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class AnnouncementModelSpec extends Specification {

    def validStartAt = LocalDateTime.now().plusDays(1)
    def validEndAt = LocalDateTime.now().plusDays(10)

    def "create: 有効なパラメータでAnnouncementModelを生成できること"() {
        when:
        def model = AnnouncementModel.create(
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                validStartAt, validEndAt
        )

        then:
        model.id == null
        model.titleJa == "タイトル"
        model.titleEn == "Title"
        model.titleEs == "Titulo"
        model.bodyJa == "本文"
        model.bodyEn == "Body"
        model.bodyEs == "Cuerpo"
        model.severity == "INFO"
        model.targetScope == "ALL"
        model.startAt == validStartAt
        model.endAt == validEndAt
    }

    @Unroll
    def "create: titleJaが '#titleJa' の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                titleJa, "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                validStartAt, validEndAt
        )

        then:
        thrown(IllegalArgumentException)

        where:
        titleJa << [null, "", "  "]
    }

    @Unroll
    def "create: titleEnが '#titleEn' の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                "タイトル", titleEn, "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                validStartAt, validEndAt
        )

        then:
        thrown(IllegalArgumentException)

        where:
        titleEn << [null, "", "  "]
    }

    @Unroll
    def "create: titleEsが '#titleEs' の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                "タイトル", "Title", titleEs,
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                validStartAt, validEndAt
        )

        then:
        thrown(IllegalArgumentException)

        where:
        titleEs << [null, "", "  "]
    }

    @Unroll
    def "create: bodyJaが '#bodyJa' の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                "タイトル", "Title", "Titulo",
                bodyJa, "Body", "Cuerpo",
                "INFO", "ALL",
                validStartAt, validEndAt
        )

        then:
        thrown(IllegalArgumentException)

        where:
        bodyJa << [null, "", "  "]
    }

    @Unroll
    def "create: bodyEnが '#bodyEn' の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                "タイトル", "Title", "Titulo",
                "本文", bodyEn, "Cuerpo",
                "INFO", "ALL",
                validStartAt, validEndAt
        )

        then:
        thrown(IllegalArgumentException)

        where:
        bodyEn << [null, "", "  "]
    }

    @Unroll
    def "create: bodyEsが '#bodyEs' の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                "タイトル", "Title", "Titulo",
                "本文", "Body", bodyEs,
                "INFO", "ALL",
                validStartAt, validEndAt
        )

        then:
        thrown(IllegalArgumentException)

        where:
        bodyEs << [null, "", "  "]
    }

    def "create: severityが不正な値の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INVALID", "ALL",
                validStartAt, validEndAt
        )

        then:
        thrown(IllegalArgumentException)
    }

    def "create: targetScopeが不正な値の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "INVALID_SCOPE",
                validStartAt, validEndAt
        )

        then:
        thrown(IllegalArgumentException)
    }

    def "create: startAtがendAt以降の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                validEndAt, validStartAt
        )

        then:
        thrown(IllegalArgumentException)
    }

    def "create: startAtとendAtが同一の場合 IllegalArgumentExceptionをスローすること"() {
        when:
        AnnouncementModel.create(
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "INFO", "ALL",
                validStartAt, validStartAt
        )

        then:
        thrown(IllegalArgumentException)
    }

    def "reconstruct: idがセットされたAnnouncementModelを生成できること"() {
        when:
        def model = AnnouncementModel.reconstruct(
                100L,
                "タイトル", "Title", "Titulo",
                "本文", "Body", "Cuerpo",
                "WARN", "HOME",
                validStartAt, validEndAt
        )

        then:
        model.id == 100L
        model.severity == "WARN"
        model.targetScope == "HOME"
    }
}
