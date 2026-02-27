package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.notification.NotificationEventId;
import com.hwhub.backend.domain.model.notification.NotificationEventModel;
import com.hwhub.backend.domain.repository.NotificationEventRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.NotificationEventConverter;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotificationEvent;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.TNotificationEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisNotificationEventRepository implements NotificationEventRepository {

  private final TNotificationEventMapper mapper;

  @Override
  public NotificationEventId insert(
      NotificationEventModel model, Long loginUserId, String program) {

    TNotificationEvent entity = NotificationEventConverter.toEntity(model);
    entity.setCreateUserId(loginUserId);
    entity.setUpdateUserId(loginUserId);
    entity.setCreateProgram(program);
    entity.setUpdateProgram(program);

    mapper.insertSelective(entity);

    if (entity.getNotificationEventId() == null) {
      throw new IllegalStateException("notification_event_id was not generated");
    }

    return new NotificationEventId(entity.getNotificationEventId());
  }
}
