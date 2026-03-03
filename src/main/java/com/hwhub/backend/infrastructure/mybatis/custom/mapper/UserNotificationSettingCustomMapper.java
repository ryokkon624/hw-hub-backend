package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserNotificationSettingCustomMapper {

  Boolean selectEnabled(
      @Param("userId") Long userId, @Param("notificationGroupCode") String notificationGroupCode);

  int upsert(
      @Param("userId") Long userId,
      @Param("notificationGroupCode") String notificationGroupCode,
      @Param("enabled") boolean enabled,
      @Param("operatorUserId") Long operatorUserId,
      @Param("program") String program);

  int delete(
      @Param("userId") Long userId, @Param("notificationGroupCode") String notificationGroupCode);
}
