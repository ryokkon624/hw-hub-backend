package com.hwhub.backend.application.service.notification

import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.model.notification.NotificationModel
import com.hwhub.backend.domain.repository.NotificationRepository
import spock.lang.Specification
import java.time.LocalDateTime

class NotificationQueryServiceSpec extends Specification {

    def notificationRepository = Mock(NotificationRepository)
    def service = new NotificationQueryService(notificationRepository)

    def "getNotificationsで通知一覧が取得でき、markReadがtrueでリストが空でない場合は既読化処理が呼ばれること"() {
        setup:
        def targetUserId = 1L
        def limit = 20
        def markRead = true
        def notification = Mock(NotificationModel)

        when:
        def result = service.getNotifications(targetUserId, limit, markRead)

        then:
        1 * notificationRepository.findLatestByTargetUser(targetUserId, limit) >> [notification]
        1 * notificationRepository.markAllAsRead(targetUserId, _ as LocalDateTime, targetUserId, ProgramType.ONL_NTF_QRY.getCode())
        result == [notification]
    }

    def "getNotificationsでmarkReadがfalseの場合は既読化処理が呼ばれないこと"() {
        setup:
        def notification = Mock(NotificationModel)

        when:
        def result = service.getNotifications(1L, 20, false)

        then:
        1 * notificationRepository.findLatestByTargetUser(1L, 20) >> [notification]
        0 * notificationRepository.markAllAsRead(*_)
        result == [notification]
    }

    def "getNotificationsで取得結果が空の場合は既読化処理が呼ばれないこと"() {
        when:
        def result = service.getNotifications(1L, 20, true)

        then:
        1 * notificationRepository.findLatestByTargetUser(1L, 20) >> []
        0 * notificationRepository.markAllAsRead(*_)
        result == []
    }

    def "getUnreadCountで未読件数が取得できること"() {
        when:
        def result = service.getUnreadCount(1L)

        then:
        1 * notificationRepository.countUnreadByTargetUser(1L) >> 5L
        result == 5L
    }
}
