package com.hwhub.backend.infrastructure.mybatis.converter

import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class DateConverterSpec extends Specification{

    def "Date → LocalDate の変換が正しく行われること"() {
        given:
        def date = Date.from(LocalDate.of(2025, 1, 15).atStartOfDay(ZoneId.systemDefault()).toInstant())

        when:
        def result = DateConverter.toLocalDate(date)

        then:
        result == LocalDate.of(2025, 1, 15)
    }

    def "Date → LocalDate は null の場合 null を返すこと"() {
        expect:
        DateConverter.toLocalDate(null) == null
    }

    def "Date → LocalDateTime の変換が正しく行われること"() {
        given:
        def ldt = LocalDateTime.of(2025, 3, 1, 10, 20, 30)
        def date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant())

        when:
        def result = DateConverter.toLocalDateTime(date)

        then:
        result == ldt
    }

    def "Date → LocalDateTime は null の場合 null を返すこと"() {
        expect:
        DateConverter.toLocalDateTime(null) == null
    }

    def "LocalDate → Date の変換が正しく行われること"() {
        given:
        def ld = LocalDate.of(2024, 12, 31)

        when:
        def result = DateConverter.toDate(ld)

        then:
        def converted = result.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        converted == ld
    }

    def "LocalDate → Date は null の場合 null を返すこと"() {
        expect:
        DateConverter.toDate((LocalDate) null) == null
    }

    def "LocalDateTime → Date の変換が正しく行われること"() {
        given:
        def ldt = LocalDateTime.of(2025, 2, 10, 8, 45, 50)

        when:
        def result = DateConverter.toDate(ldt)

        then:
        def converted = result.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        converted == ldt
    }

    def "LocalDateTime → Date は null の場合 null を返すこと"() {
        expect:
        DateConverter.toDate((LocalDateTime) null) == null
    }
}
