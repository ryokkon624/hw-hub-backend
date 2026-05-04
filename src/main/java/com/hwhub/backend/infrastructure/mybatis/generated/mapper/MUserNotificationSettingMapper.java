package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserNotificationSetting;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserNotificationSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MUserNotificationSettingMapper {
  int deleteByPrimaryKey(
      @Param("userId") Long userId, @Param("notificationGroup") String notificationGroup);

  int insert(MUserNotificationSetting row);

  int insertSelective(MUserNotificationSetting row);

  List<MUserNotificationSetting> selectByExample(MUserNotificationSettingExample example);

  MUserNotificationSetting selectByPrimaryKey(
      @Param("userId") Long userId, @Param("notificationGroup") String notificationGroup);

  int updateByPrimaryKeySelective(MUserNotificationSetting row);

  int updateByPrimaryKey(MUserNotificationSetting row);
}
