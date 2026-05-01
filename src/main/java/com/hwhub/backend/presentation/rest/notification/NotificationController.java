package com.hwhub.backend.presentation.rest.notification;

import com.hwhub.backend.application.service.notification.NotificationQueryService;
import com.hwhub.backend.domain.model.notification.NotificationModel;
import com.hwhub.backend.presentation.rest.notification.dto.NotificationListResponse;
import com.hwhub.backend.security.CurrentUserId;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

  private final NotificationQueryService queryService;

  @GetMapping
  public NotificationListResponse getNotifications(
      @RequestParam(value = "limit", defaultValue = "20") int limit,
      @RequestParam(value = "markRead", defaultValue = "true") boolean markRead,
      @CurrentUserId Long loginUserId) {
    List<NotificationModel> list = queryService.getNotifications(loginUserId, limit, markRead);
    return NotificationListResponse.from(list);
  }

  @GetMapping("/unread-count")
  public Map<String, Object> getUnreadCount(@CurrentUserId Long loginUserId) {
    return Map.of("unreadCount", queryService.getUnreadCount(loginUserId));
  }
}
