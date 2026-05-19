package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserNotificationSettingCustomMapper {

  Boolean selectEnabled(
      @Param("userId") Long userId, @Param("notificationGroupCode") String notificationGroupCode);

  /**
   * 指定ユーザーの全通知グループ設定を一括取得する。
   *
   * @param userId ユーザーID
   * @return 通知グループコード → enabled フラグ のMap
   */
  List<Map<String, Object>> selectAllEnabled(@Param("userId") Long userId);

  int upsert(
      @Param("userId") Long userId,
      @Param("notificationGroupCode") String notificationGroupCode,
      @Param("enabled") boolean enabled,
      @Param("operatorUserId") Long operatorUserId,
      @Param("program") String program);

  int delete(
      @Param("userId") Long userId, @Param("notificationGroupCode") String notificationGroupCode);
}
