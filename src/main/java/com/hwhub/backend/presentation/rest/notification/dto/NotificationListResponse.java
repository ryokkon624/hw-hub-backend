package com.hwhub.backend.presentation.rest.notification.dto;

import com.hwhub.backend.domain.model.notification.NotificationModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record NotificationListResponse(List<Item> items) {

  public static NotificationListResponse from(List<NotificationModel> list) {
    return new NotificationListResponse(list.stream().map(Item::from).collect(Collectors.toList()));
  }

  public record Item(
      long notificationId,
      boolean isRead,
      LocalDateTime occurredAt,
      String titleKey,
      String bodyKey,
      Map<String, Object> params,
      String linkType,
      Long linkId,
      int aggregatedCount) {

    static Item from(NotificationModel n) {
      return new Item(
          n.getNotificationId().value(),
          n.isRead(),
          n.getOccurredAt(),
          n.getMessage().titleKey(),
          n.getMessage().bodyKey(),
          n.getMessage().params(),
          n.getLink().linkType().getCode(),
          n.getLink().linkId(),
          n.getAggregatedCount());
    }
  }
}
