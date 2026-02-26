package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotificationEvent;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotificationEventCustomMapper {
  List<TNotificationEvent> findUnprocessedByType(
      @Param("eventType") String eventType, @Param("limit") int limit);

  List<TNotificationEvent> findAllUnprocessed(@Param("limit") int limit);

  int markProcessed(
      @Param("notificationEventIds") List<Long> notificationEventIds,
      @Param("processedAt") LocalDateTime processedAt,
      @Param("updateUserId") long updateUserId,
      @Param("updateProgram") String updateProgram);
}
