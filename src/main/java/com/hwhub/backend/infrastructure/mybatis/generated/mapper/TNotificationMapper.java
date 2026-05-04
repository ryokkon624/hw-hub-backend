package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotification;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotificationExample;
import java.util.List;

public interface TNotificationMapper {
    int deleteByPrimaryKey(Long notificationId);

    int insert(TNotification row);

    int insertSelective(TNotification row);

    List<TNotification> selectByExampleWithBLOBs(TNotificationExample example);

    List<TNotification> selectByExample(TNotificationExample example);

    TNotification selectByPrimaryKey(Long notificationId);

    int updateByPrimaryKeySelective(TNotification row);

    int updateByPrimaryKeyWithBLOBs(TNotification row);

    int updateByPrimaryKey(TNotification row);
}