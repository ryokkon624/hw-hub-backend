package com.hwhub.backend.domain.model.notification

import com.hwhub.backend.domain.enums.NotificationLinkType
import com.hwhub.backend.domain.enums.NotificationType
import spock.lang.Specification
import java.time.LocalDateTime

class NotificationModelSpec extends Specification {

    def "reconstructメソッドで正しくインスタンスが再構築できること"() {
        setup:
        def params = ["key": "value"]
        def readAt = LocalDateTime.of(2023, 1, 1, 10, 0)
        def occurredAt = LocalDateTime.of(2023, 1, 1, 9, 0)

        when:
        def model = NotificationModel.reconstruct(
                1L,
                2L,
                "0301", // TASK_ASSIGNED
                3L,
                4L,
                true,
                readAt,
                "title",
                "body",
                params,
                "Household",
                5L,
                occurredAt,
                "group1",
                2
        )

        then:
        model.notificationId.value() == 1L
        model.householdId == 2L
        model.notificationType == NotificationType.TASK_ASSIGNED
        model.actorUserId == 3L
        model.targetUserId == 4L
        model.isRead() == true
        model.readAt == readAt
        model.message.titleKey() == "title"
        model.message.bodyKey() == "body"
        model.message.params() == params
        model.link.linkType() == NotificationLinkType.HOUSEHOLD
        model.link.linkId() == 5L
        model.occurredAt == occurredAt
        model.aggregatedKey == "group1"
        model.aggregatedCount == 2
    }

    def "newUnreadファクトリメソッドで未読の通知が正しく生成できること"() {
        setup:
        def message = NotificationMessage.ofBody("body")
        def link = NotificationLink.none()
        def occurredAt = LocalDateTime.now()

        when:
        def model = NotificationModel.newUnread(
                1L,
                NotificationType.HAVE_BEEN_REMOVED,
                2L,
                3L,
                message,
                link,
                occurredAt
        )

        then:
        model.notificationId == null
        model.householdId == 1L
        model.notificationType == NotificationType.HAVE_BEEN_REMOVED
        model.actorUserId == 2L
        model.targetUserId == 3L
        model.isRead() == false
        model.readAt == null
        model.message == message
        model.link == link
        model.occurredAt == occurredAt
        model.aggregatedKey == null
        model.aggregatedCount == 1
    }

    def "markReadメソッドで既読情報が正しく更新されること"() {
        setup:
        def message = NotificationMessage.ofBody("body")
        def link = NotificationLink.none()
        def occurredAt = LocalDateTime.now()
        def model = NotificationModel.newUnread(1L, NotificationType.TASK_ASSIGNED, 2L, 3L, message, link, occurredAt)
        def readAt = LocalDateTime.now().plusHours(1)

        when:
        model.markRead(readAt)

        then:
        model.isRead() == true
        model.readAt == readAt
    }

    def "markReadメソッドにnullを渡すとNullPointerExceptionが発生すること"() {
        setup:
        def message = NotificationMessage.ofBody("body")
        def link = NotificationLink.none()
        def model = NotificationModel.newUnread(1L, NotificationType.TASK_ASSIGNED, 2L, 3L, message, link, LocalDateTime.now())

        when:
        model.markRead(null)

        then:
        thrown(NullPointerException)
    }

    def "targetUserIdが0以下の場合はIllegalArgumentExceptionが発生すること"() {
        setup:
        def message = NotificationMessage.ofBody("body")
        def link = NotificationLink.none()
        def occurredAt = LocalDateTime.now()

        when:
        NotificationModel.newUnread(1L, NotificationType.TASK_ASSIGNED, 2L, 0L, message, link, occurredAt)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "targetUserId must be positive"
    }

    def "aggregatedCountが0以下の場合はIllegalArgumentExceptionが発生すること"() {
        when:
        NotificationModel.reconstruct(
                1L, 2L, "0301", 3L, 4L, false, null, "title", "body", null, "None", null, LocalDateTime.now(), null, 0
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "aggregatedCount must be >= 1"
    }

    def "isReadがtrueでreadAtがnullの場合はIllegalArgumentExceptionが発生すること"() {
        when:
        NotificationModel.reconstruct(
                1L, 2L, "0301", 3L, 4L, true, null, "title", "body", null, "None", null, LocalDateTime.now(), null, 1
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "readAt is required when isRead = true"
    }

    def "isReadがfalseでreadAtがnullでない場合はIllegalArgumentExceptionが発生すること"() {
        when:
        NotificationModel.reconstruct(
                1L, 2L, "0301", 3L, 4L, false, LocalDateTime.now(), "title", "body", null, "None", null, LocalDateTime.now(), null, 1
        )

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "readAt must be null when isRead = false"
    }
}
