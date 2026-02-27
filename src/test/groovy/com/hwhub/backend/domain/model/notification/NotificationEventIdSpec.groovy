package com.hwhub.backend.domain.model.notification

import spock.lang.Specification

class NotificationEventIdSpec extends Specification {

    def "正の値を指定した場合、インスタンスが生成できること"() {
        when:
        def id = new NotificationEventId(1L)

        then:
        id.value() == 1L
    }

    def "0以下の値を指定した場合、IllegalArgumentExceptionがスローされること"() {
        when:
        new NotificationEventId(0L)

        then:
        def e1 = thrown(IllegalArgumentException)
        e1.message == "notificationEventId must be positive"

        when:
        new NotificationEventId(-1L)

        then:
        def e2 = thrown(IllegalArgumentException)
        e2.message == "notificationEventId must be positive"
    }
}
