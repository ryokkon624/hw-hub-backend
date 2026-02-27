package com.hwhub.backend.domain.model.notification

import com.hwhub.backend.domain.enums.NotificationLinkType
import spock.lang.Specification

class NotificationLinkSpec extends Specification {

    def "正常にインスタンスが生成できること"() {
        when:
        def link = new NotificationLink(NotificationLinkType.HOUSEHOLD, 1L)

        then:
        link.linkType() == NotificationLinkType.HOUSEHOLD
        link.linkId() == 1L
    }

    def "linkTypeがnullの場合、NullPointerExceptionがスローされること"() {
        when:
        new NotificationLink(null, 1L)

        then:
        def e = thrown(NullPointerException)
        e.message == "linkType is required"
    }

    def "linkTypeがNONEでlinkIdが指定されている場合、IllegalArgumentExceptionがスローされること"() {
        when:
        new NotificationLink(NotificationLinkType.NONE, 1L)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "linkId must be null when linkType is NONE"
    }

    def "noneファクトリメソッドでlinkTypeがNONE、linkIdがnullのインスタンスが生成できること"() {
        when:
        def link = NotificationLink.none()

        then:
        link.linkType() == NotificationLinkType.NONE
        link.linkId() == null
    }

    def "NONE以外のlinkTypeでlinkIdがnullでもインスタンスが生成できること"() {
        when:
        def link = new NotificationLink(NotificationLinkType.SETTINGS, null)

        then:
        link.linkType() == NotificationLinkType.SETTINGS
        link.linkId() == null
    }
}
