package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotification;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotificationCustomMapper {
  List<TNotification> findLatestByTargetUser(
      @Param("targetUserId") long targetUserId, @Param("limit") int limit);

  List<TNotification> findUnreadByTargetUser(
      @Param("targetUserId") long targetUserId, @Param("limit") int limit);

  int markAllAsRead(
      @Param("targetUserId") long targetUserId,
      @Param("readAt") LocalDateTime readAt,
      @Param("updateUserId") long updateUserId,
      @Param("updateProgram") String updateProgram);

  int markAsReadUpTo(
      @Param("targetUserId") long targetUserId,
      @Param("occurredAt") LocalDateTime occurredAt,
      @Param("readAt") LocalDateTime readAt,
      @Param("updateUserId") long updateUserId,
      @Param("updateProgram") String updateProgram);

  long countUnreadByTargetUser(@Param("targetUserId") long targetUserId);

  int bulkInsert(@Param("list") List<TNotification> list);
}
