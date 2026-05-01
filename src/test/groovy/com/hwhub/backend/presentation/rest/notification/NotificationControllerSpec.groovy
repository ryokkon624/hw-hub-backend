package com.hwhub.backend.presentation.rest.notification

import com.hwhub.backend.application.service.notification.NotificationQueryService
import com.hwhub.backend.domain.model.notification.NotificationModel
import spock.lang.Specification

import java.time.LocalDateTime

class NotificationControllerSpec extends Specification {

    def queryService = Mock(NotificationQueryService)
    def controller = new NotificationController(queryService)

    def "getNotifications は loginUserId, limit, markRead で service.getNotifications を呼び NotificationListResponse を返す"() {
        given:
        Long loginUserId = 1L
        int limit = 10
        boolean markRead = false

        def model = NotificationModel.reconstruct(
                1L,
                2L,
                "0201",
                3L,
                1L,
                false,
                null,
                "title",
                "body",
                null,
                "None",
                null,
                LocalDateTime.now(),
                null,
                1
        )

        when:
        def result = controller.getNotifications(limit, markRead, loginUserId)

        then:
        1 * queryService.getNotifications(1L, 10, false) >> [model]
        result != null
        result.items() != null
        result.items().size() == 1
    }

    def "getUnreadCount は loginUserId で service.getUnreadCount を呼び件数を返す"() {
        given:
        Long loginUserId = 1L

        when:
        def result = controller.getUnreadCount(loginUserId)

        then:
        1 * queryService.getUnreadCount(1L) >> 5L
        result["unreadCount"] == 5L
    }
}
