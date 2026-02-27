package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.notification.NotificationId;
import com.hwhub.backend.domain.model.notification.NotificationModel;
import com.hwhub.backend.domain.repository.NotificationRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.NotificationConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.NotificationCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TNotification;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.TNotificationMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisNotificationRepository implements NotificationRepository {

  private final TNotificationMapper mapper;
  private final NotificationCustomMapper customMapper;
  private final NotificationConverter converter;

  @Override
  public long countUnreadByTargetUser(Long targetUserId) {
    return customMapper.countUnreadByTargetUser(targetUserId);
  }

  @Override
  public Optional<NotificationModel> findById(NotificationId notificationId) {
    TNotification entity = mapper.selectByPrimaryKey(notificationId.value());
    return Optional.ofNullable(converter.toModel(entity));
  }

  @Override
  public List<NotificationModel> findLatestByTargetUser(Long targetUserId, int limit) {
    return customMapper.findLatestByTargetUser(targetUserId, limit).stream()
        .map(converter::toModel)
        .toList();
  }

  @Override
  public List<NotificationModel> findUnreadByTargetUser(Long targetUserId, int limit) {
    return customMapper.findUnreadByTargetUser(targetUserId, limit).stream()
        .map(converter::toModel)
        .toList();
  }

  @Override
  public NotificationId insert(NotificationModel model, String program) {
    Long userId = model.getActorUserId();

    TNotification entity = converter.toEntity(model);
    entity.setCreateUserId(userId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);
    mapper.insertSelective(entity);

    if (entity.getNotificationId() == null) {
      throw new IllegalStateException("notification_id was not generated");
    }

    return new NotificationId(entity.getNotificationId());
  }

  @Override
  public void markAllAsRead(
      Long targetUserId, LocalDateTime readAt, Long updateUserId, String program) {
    customMapper.markAllAsRead(targetUserId, readAt, updateUserId, program);
  }

  @Override
  public void markAsReadUpTo(
      Long targetUserId,
      LocalDateTime occurredAt,
      LocalDateTime readAt,
      Long updateUserId,
      String program) {
    customMapper.markAsReadUpTo(targetUserId, occurredAt, readAt, updateUserId, program);
  }

  @Override
  public int bulkInsert(List<NotificationModel> list, String program) {
    List<TNotification> entities = list.stream().map(converter::toEntity).toList();
    entities.forEach(
        entity -> {
          entity.setCreateUserId(entity.getActorUserId());
          entity.setCreateProgram(program);
          entity.setUpdateUserId(entity.getActorUserId());
          entity.setUpdateProgram(program);
        });

    return customMapper.bulkInsert(entities);
  }
}
