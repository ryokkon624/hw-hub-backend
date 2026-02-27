package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.enums.EventStatus
import com.hwhub.backend.domain.enums.NotificationType
import com.hwhub.backend.domain.model.notification.NotificationEventModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotificationEvent
import spock.lang.Specification
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class NotificationEventConverterSpec extends Specification {

    def "toEntityでnullを渡すとnullが返ること"() {
        expect:
        NotificationEventConverter.toEntity(null) == null
    }

    def "toEntityで全項目が設定されたモデルをEntityに変換できること"() {
        setup:
        def aggregationDate = LocalDate.of(2023, 1, 1)
        def occurredAt = LocalDateTime.of(2023, 1, 1, 10, 0)
        def processingStartedAt = LocalDate.of(2023, 1, 2)
        def processedAt = LocalDate.of(2023, 1, 3)

        def model = NotificationEventModel.reconstruct(
                1L,
                2L,
                "0301",
                3L,
                4L,
                5L,
                aggregationDate,
                occurredAt,
                "1",
                "key",
                processingStartedAt,
                processedAt
        )

        when:
        def entity = NotificationEventConverter.toEntity(model)

        then:
        entity.notificationEventId == 1L
        entity.householdId == 2L
        entity.notificationType == "0301"
        entity.actorUserId == 3L
        entity.targetUserId == 4L
        entity.entityId == 5L
        entity.aggregationDate != null
        entity.occurredAt != null
        entity.eventStatus == "1"
        entity.processingKey == "key"
        entity.processingStartedAt != null
        entity.processedAt != null
    }

    def "toEntityでIdがnullのモデルを変換できること"() {
        setup:
        def aggregationDate = LocalDate.now()
        def occurredAt = LocalDateTime.now()

        def model = NotificationEventModel.newUnprocessed(
                2L,
                NotificationType.BE_DUMPED_TASK,
                3L,
                4L,
                5L,
                aggregationDate,
                occurredAt
        )

        when:
        def entity = NotificationEventConverter.toEntity(model)

        then:
        entity.notificationEventId == null
        entity.eventStatus == "0" // PENDING
        entity.processingKey == null
        entity.processingStartedAt == null
        entity.processedAt == null
    }

    def "toModelでnullを渡すとnullが返ること"() {
        expect:
        NotificationEventConverter.toModel(null) == null
    }

    def "toModelで全項目が設定されたEntityをModelに変換できること"() {
        setup:
        def entity = new TNotificationEvent()
        entity.notificationEventId = 1L
        entity.householdId = 2L
        entity.notificationType = "0301"
        entity.actorUserId = 3L
        entity.targetUserId = 4L
        entity.entityId = 5L
        entity.aggregationDate = Date.from(LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())
        entity.occurredAt = Date.from(LocalDateTime.of(2023, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant())
        entity.eventStatus = "1"
        entity.processingKey = "key"
        entity.processingStartedAt = Date.from(LocalDate.of(2023, 1, 2).atStartOfDay(ZoneId.systemDefault()).toInstant())
        entity.processedAt = Date.from(LocalDate.of(2023, 1, 3).atStartOfDay(ZoneId.systemDefault()).toInstant())

        when:
        def model = NotificationEventConverter.toModel(entity)

        then:
        model.notificationEventId.value() == 1L
        model.householdId == 2L
        model.notificationType == NotificationType.TASK_ASSIGNED
        model.actorUserId == 3L
        model.targetUserId == 4L
        model.entityId == 5L
        model.aggregationDate != null
        model.occurredAt != null
        model.eventStatus == EventStatus.PROCESSING
        model.processingKey == "key"
        model.processingStartedAt != null
        model.processedAt != null
    }
}
