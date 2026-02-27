package com.hwhub.backend.presentation.rest.notification

import com.hwhub.backend.application.service.notification.NotificationQueryService
import com.hwhub.backend.domain.enums.NotificationLinkType
import com.hwhub.backend.domain.enums.NotificationType
import com.hwhub.backend.domain.model.notification.NotificationLink
import com.hwhub.backend.domain.model.notification.NotificationMessage
import com.hwhub.backend.domain.model.notification.NotificationModel
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

class NotificationControllerSpec extends Specification {

    def queryService = Mock(NotificationQueryService)
    def controller = new NotificationController(queryService)
    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "GET /api/notifications で通知一覧が取得できること"() {
        setup:
        def auth = Mock(Authentication)
        auth.getName() >> "1" // loginUserId = 1

        def message = new NotificationMessage("title", "body", null)
        def model = NotificationModel.reconstruct(
                1L,
                2L,
                "0201",
                3L,
                1L, // targetUserId
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
        // Spring BootのMessageSource解決等はテストしないのでDTO変換が正しく動くかだけ確認

        when:
        def result = mockMvc.perform(get("/api/notifications")
                .param("limit", "10")
                .param("markRead", "false")
                .principal(auth))

        then:
        1 * queryService.getNotifications(1L, 10, false) >> [model]
        result.andExpect(status().isOk())
              .andExpect(jsonPath("\$.items").isArray())
    }

    def "GET /api/notifications/unread-count で未読件数が取得できること"() {
        setup:
        def auth = Mock(Authentication)
        auth.getName() >> "1" // loginUserId = 1

        when:
        def result = mockMvc.perform(get("/api/notifications/unread-count")
                .principal(auth))

        then:
        1 * queryService.getUnreadCount(1L) >> 5L
        result.andExpect(status().isOk())
              .andExpect(jsonPath("\$.unreadCount").value(5))
    }
}
