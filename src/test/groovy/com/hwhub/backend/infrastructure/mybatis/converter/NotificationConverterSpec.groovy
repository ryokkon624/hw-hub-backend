package com.hwhub.backend.infrastructure.mybatis.converter

import tools.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.JsonProcessingException
import com.hwhub.backend.domain.enums.NotificationLinkType
import com.hwhub.backend.domain.enums.NotificationType
import com.hwhub.backend.domain.model.notification.NotificationLink
import com.hwhub.backend.domain.model.notification.NotificationMessage
import com.hwhub.backend.domain.model.notification.NotificationModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotification
import spock.lang.Specification
import java.time.LocalDateTime
import java.time.ZoneId

class NotificationConverterSpec extends Specification {

    def objectMapper = new ObjectMapper()
    def converter = new NotificationConverter(objectMapper)

    def "toEntityでnullを渡すとnullが返ること"() {
        expect:
        converter.toEntity(null) == null
    }

    def "toEntityで全項目が設定されたモデルをEntityに変換できること"() {
        setup:
        def occurredAt = LocalDateTime.of(2023, 1, 1, 10, 0)
        def readAt = LocalDateTime.of(2023, 1, 1, 11, 0)
        def params = ["key": "value"]
        def model = NotificationModel.reconstruct(
                1L,
                2L,
                "0301",
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

        when:
        def entity = converter.toEntity(model)

        then:
        entity.notificationId == 1L
        entity.householdId == 2L
        entity.notificationType == "0301"
        entity.actorUserId == 3L
        entity.targetUserId == 4L
        entity.isRead == true
        entity.readAt != null // DateConverterの挙動に依存するためnullでないことのみ確認（詳細はDateConverterの責務）
        entity.titleKey == "title"
        entity.bodyKey == "body"
        entity.paramsJson == '{"key":"value"}'
        entity.linkType == "Household"
        entity.linkId == 5L
        entity.occurredAt != null
        entity.aggregatedKey == "group1"
        entity.aggregatedCount == 2
    }

    def "toEntityでIdがnullのモデルを変換できること"() {
        setup:
        def occurredAt = LocalDateTime.now()
        def message = NotificationMessage.ofBody("body")
        def model = NotificationModel.newUnread(
                2L,
                NotificationType.HAVE_BEEN_REMOVED,
                3L,
                4L,
                message,
                NotificationLink.none(),
                occurredAt
        )

        when:
        def entity = converter.toEntity(model)

        then:
        entity.notificationId == null
        entity.paramsJson == null
        entity.linkId == null
    }

    def "toEntityでJSONシリアライズに失敗した場合はIllegalStateExceptionがスローされること"() {
        setup:
        def failingObjectMapper = Mock(ObjectMapper)
        failingObjectMapper.writeValueAsString(_) >> { throw new JsonProcessingException("error") {} }
        def failingConverter = new NotificationConverter(failingObjectMapper)

        def message = new NotificationMessage("title", "body", ["key": "value"])
        def model = NotificationModel.newUnread(2L, NotificationType.HAVE_BEEN_REMOVED, 3L, 4L, message, NotificationLink.none(), LocalDateTime.now())

        when:
        failingConverter.toEntity(model)

        then:
        def e = thrown(IllegalStateException)
        e.message == "Failed to serialize params_json"
    }

    def "toModelでnullを渡すとnullが返ること"() {
        expect:
        converter.toModel(null) == null
    }

    def "toModelで全項目が設定されたEntityをModelに変換できること"() {
        setup:
        def entity = new TNotification()
        entity.notificationId = 1L
        entity.householdId = 2L
        entity.notificationType = "0301"
        entity.actorUserId = 3L
        entity.targetUserId = 4L
        entity.isRead = true
        entity.readAt = Date.from(LocalDateTime.of(2023, 1, 1, 11, 0).atZone(ZoneId.systemDefault()).toInstant())
        entity.titleKey = "title"
        entity.bodyKey = "body"
        entity.paramsJson = '{"key":"value"}'
        entity.linkType = "Household"
        entity.linkId = 5L
        entity.occurredAt = Date.from(LocalDateTime.of(2023, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant())
        entity.aggregatedKey = "group1"
        entity.aggregatedCount = 2

        when:
        def model = converter.toModel(entity)

        then:
        model.notificationId.value() == 1L
        model.householdId == 2L
        model.notificationType == NotificationType.TASK_ASSIGNED
        model.actorUserId == 3L
        model.targetUserId == 4L
        model.isRead() == true
        model.readAt != null
        model.message.titleKey() == "title"
        model.message.bodyKey() == "body"
        model.message.params() == ["key": "value"]
        model.link.linkType() == NotificationLinkType.HOUSEHOLD
        model.link.linkId() == 5L
        model.occurredAt != null
        model.aggregatedKey == "group1"
        model.aggregatedCount == 2
    }

    def "toModelでJSONデシリアライズに失敗した場合はparamsはnullになること"() {
        setup:
        def entity = new TNotification()
        entity.notificationId = 1L
        entity.householdId = 2L
        entity.notificationType = "0301"
        entity.targetUserId = 4L
        entity.isRead = false
        entity.bodyKey = "body"
        entity.paramsJson = '{invalid json}' // 不正なJSON
        entity.linkType = "None"
        entity.occurredAt = new Date()
        entity.aggregatedCount = 1

        when:
        def model = converter.toModel(entity)

        then:
        model.message.params() == null
    }

    def "toModelでJSONがnullや空文字の場合はparamsはnullになること"() {
        setup:
        def entity = new TNotification()
        entity.notificationId = 1L
        entity.householdId = 2L
        entity.notificationType = "0301"
        entity.targetUserId = 4L
        entity.isRead = false
        entity.bodyKey = "body"
        entity.paramsJson = jsonStr
        entity.linkType = "None"
        entity.occurredAt = new Date()
        entity.aggregatedCount = 1

        when:
        def model = converter.toModel(entity)

        then:
        model.message.params() == null

        where:
        jsonStr | _
        null    | _
        ""      | _
        "   "   | _
    }
}
