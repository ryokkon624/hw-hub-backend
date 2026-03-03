package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.enums.NotificationGroup;
import com.hwhub.backend.domain.repository.UserNotificationSettingRepository;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.UserNotificationSettingCustomMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisUserNotificationSettingRepository implements UserNotificationSettingRepository {

  private final UserNotificationSettingCustomMapper customMapper;

  @Override
  public Optional<Boolean> findEnabled(Long userId, NotificationGroup group) {
    return Optional.ofNullable(customMapper.selectEnabled(userId, group.getCode()));
  }

  @Override
  public int upsert(
      Long userId, NotificationGroup group, boolean enabled, Long operatorUserId, String program) {
    return customMapper.upsert(userId, group.getCode(), enabled, operatorUserId, program);
  }

  @Override
  public int delete(Long userId, NotificationGroup group) {
    return customMapper.delete(userId, group.getCode());
  }
}
