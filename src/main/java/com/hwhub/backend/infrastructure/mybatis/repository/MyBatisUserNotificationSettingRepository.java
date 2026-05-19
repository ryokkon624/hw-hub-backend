package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.enums.NotificationGroup;
import com.hwhub.backend.domain.repository.UserNotificationSettingRepository;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.UserNotificationSettingCustomMapper;
import java.util.EnumMap;
import java.util.Map;
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
  public Map<NotificationGroup, Boolean> findAllEnabled(Long userId) {
    Map<NotificationGroup, Boolean> result = new EnumMap<>(NotificationGroup.class);
    for (Map<String, Object> row : customMapper.selectAllEnabled(userId)) {
      String code = (String) row.get("notificationGroup");
      Boolean enabled = (Boolean) row.get("enabled");
      if (code == null || enabled == null) continue;
      try {
        NotificationGroup group = NotificationGroup.fromCode(code);
        result.put(group, enabled);
      } catch (IllegalArgumentException ignored) {
        // 未知のコード値はスキップ
      }
    }
    return result;
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
