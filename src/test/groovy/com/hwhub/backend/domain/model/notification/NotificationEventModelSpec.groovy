package com.hwhub.backend.domain.model.notification

import com.hwhub.backend.domain.enums.EventStatus
import com.hwhub.backend.domain.enums.NotificationType
import spock.lang.Specification
import java.time.LocalDate
import java.time.LocalDateTime

class NotificationEventModelSpec extends Specification {

    def "reconstructメソッドで正しくインスタンスが再構築できること"() {
        setup:
        def aggregationDate = LocalDate.of(2023, 1, 1)
        def occurredAt = LocalDateTime.of(2023, 1, 1, 10, 0)
        def processingStartedAt = LocalDate.of(2023, 1, 2)
        def processedAt = LocalDate.of(2023, 1, 3)

        when:
        def model = NotificationEventModel.reconstruct(
                1L,
                2L,
                "0301", // TASK_ASSIGNED
                3L,
                4L,
                5L,
                aggregationDate,
                occurredAt,
                "1", // PROCESSING
                "batch-key",
                processingStartedAt,
                processedAt
        )

        then:
        model.notificationEventId.value() == 1L
        model.householdId == 2L
        model.notificationType == NotificationType.TASK_ASSIGNED
        model.actorUserId == 3L
        model.targetUserId == 4L
        model.entityId == 5L
        model.aggregationDate == aggregationDate
        model.occurredAt == occurredAt
        model.eventStatus == EventStatus.PROCESSING
        model.processingKey == "batch-key"
        model.processingStartedAt == processingStartedAt
        model.processedAt == processedAt
    }

    def "newUnprocessedファクトリメソッドで未処理のイベントが正しく生成できること"() {
        setup:
        def aggregationDate = LocalDate.now()
        def occurredAt = LocalDateTime.now()

        when:
        def model = NotificationEventModel.newUnprocessed(
                1L,
                NotificationType.BE_DUMPED_TASK,
                2L,
                3L,
                4L,
                aggregationDate,
                occurredAt
        )

        then:
        model.notificationEventId == null
        model.householdId == 1L
        model.notificationType == NotificationType.BE_DUMPED_TASK
        model.actorUserId == 2L
        model.targetUserId == 3L
        model.entityId == 4L
        model.aggregationDate == aggregationDate
        model.occurredAt == occurredAt
        model.eventStatus == EventStatus.PENDING
        model.processingKey == null
        model.processingStartedAt == null
        model.processedAt == null
    }
}
