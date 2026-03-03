package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.enums.NotificationGroup;
import java.util.Optional;

public interface UserNotificationSettingRepository {

  /** 設定があれば enabled を返す。なければ empty */
  Optional<Boolean> findEnabled(Long userId, NotificationGroup group);

  /** 差分として upsert（存在すれば更新、なければ追加） */
  int upsert(
      Long userId, NotificationGroup group, boolean enabled, Long operatorUserId, String program);

  /** 差分を消す（= デフォルトに戻す） */
  int delete(Long userId, NotificationGroup group);
}
