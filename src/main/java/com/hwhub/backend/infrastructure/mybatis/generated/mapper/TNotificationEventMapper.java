package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotificationEvent;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotificationEventExample;
import java.util.List;

public interface TNotificationEventMapper {
  int deleteByPrimaryKey(Long notificationEventId);

  int insert(TNotificationEvent row);

  int insertSelective(TNotificationEvent row);

  List<TNotificationEvent> selectByExample(TNotificationEventExample example);

  TNotificationEvent selectByPrimaryKey(Long notificationEventId);

  int updateByPrimaryKeySelective(TNotificationEvent row);

  int updateByPrimaryKey(TNotificationEvent row);
}
