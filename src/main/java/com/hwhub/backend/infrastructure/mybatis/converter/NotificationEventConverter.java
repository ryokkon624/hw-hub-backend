package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.notification.NotificationEventModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotificationEvent;

public class NotificationEventConverter {

  private NotificationEventConverter() {}
  ;

  public static TNotificationEvent toEntity(NotificationEventModel model) {
    if (model == null) {
      return null;
    }
    TNotificationEvent entity = new TNotificationEvent();
    if (model.getNotificationEventId() != null) {
      entity.setNotificationEventId(model.getNotificationEventId().value());
    }
    entity.setHouseholdId(model.getHouseholdId());
    entity.setNotificationType(model.getNotificationType().getCode());
    entity.setActorUserId(model.getActorUserId());
    entity.setTargetUserId(model.getTargetUserId());
    entity.setEntityId(model.getEntityId());
    entity.setAggregationDate(DateConverter.toDate(model.getAggregationDate()));
    entity.setOccurredAt(DateConverter.toDate(model.getOccurredAt()));
    entity.setEventStatus(model.getEventStatus().getCode());
    entity.setProcessingKey(model.getProcessingKey());
    entity.setProcessingStartedAt(DateConverter.toDate(model.getProcessingStartedAt()));
    entity.setProcessedAt(DateConverter.toDate(model.getProcessedAt()));
    return entity;
  }

  public static NotificationEventModel toModel(TNotificationEvent entity) {
    if (entity == null) {
      return null;
    }
    return NotificationEventModel.reconstruct(
        entity.getNotificationEventId(),
        entity.getHouseholdId(),
        entity.getNotificationType(),
        entity.getActorUserId(),
        entity.getTargetUserId(),
        entity.getEntityId(),
        DateConverter.toLocalDate(entity.getAggregationDate()),
        DateConverter.toLocalDateTime(entity.getOccurredAt()),
        entity.getEventStatus(),
        entity.getProcessingKey(),
        DateConverter.toLocalDate(entity.getProcessingStartedAt()),
        DateConverter.toLocalDate(entity.getProcessedAt()));
  }
}
