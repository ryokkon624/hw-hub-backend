package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.notification.NotificationModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotification;
import java.util.Map;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Component
public class NotificationConverter {

  private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};
  private final ObjectMapper objectMapper;

  public NotificationConverter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public TNotification toEntity(NotificationModel model) {
    if (model == null) {
      return null;
    }
    TNotification entity = new TNotification();
    if (model.getNotificationId() != null) {
      entity.setNotificationId(model.getNotificationId().value());
    }
    entity.setHouseholdId(model.getHouseholdId());
    entity.setNotificationType(model.getNotificationType().getCode());
    entity.setActorUserId(model.getActorUserId());
    entity.setTargetUserId(model.getTargetUserId());
    entity.setIsRead(model.isRead());
    entity.setReadAt(DateConverter.toDate(model.getReadAt()));
    entity.setTitleKey(model.getMessage().titleKey());
    entity.setBodyKey(model.getMessage().bodyKey());
    entity.setParamsJson(writeJsonSafely(model.getMessage().params()));
    entity.setLinkType(model.getLink().linkType().getCode());
    entity.setLinkId(model.getLink().linkId());
    entity.setOccurredAt(DateConverter.toDate(model.getOccurredAt()));
    entity.setAggregatedKey(model.getAggregatedKey());
    entity.setAggregatedCount(model.getAggregatedCount());
    return entity;
  }

  public NotificationModel toModel(TNotification entity) {
    if (entity == null) {
      return null;
    }
    return NotificationModel.reconstruct(
        entity.getNotificationId(),
        entity.getHouseholdId(),
        entity.getNotificationType(),
        entity.getActorUserId(),
        entity.getTargetUserId(),
        entity.getIsRead(),
        DateConverter.toLocalDateTime(entity.getReadAt()),
        entity.getTitleKey(),
        entity.getBodyKey(),
        readMapSafely(entity.getParamsJson()),
        entity.getLinkType(),
        entity.getLinkId(),
        DateConverter.toLocalDateTime(entity.getOccurredAt()),
        entity.getAggregatedKey(),
        entity.getAggregatedCount());
  }

  private Map<String, Object> readMapSafely(String json) {
    if (json == null || json.isBlank()) return null;
    try {
      return objectMapper.readValue(json, MAP_TYPE);
    } catch (Exception ex) {
      return null;
    }
  }

  private String writeJsonSafely(Map<String, Object> map) {
    if (map == null || map.isEmpty()) return null;
    try {
      return objectMapper.writeValueAsString(map);
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to serialize params_json", ex);
    }
  }
}
