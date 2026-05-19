package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.enums.NotificationGroup;
import java.util.Map;
import java.util.Optional;

public interface UserNotificationSettingRepository {

  /** 設定があれば enabled を返す。なければ empty */
  Optional<Boolean> findEnabled(Long userId, NotificationGroup group);

  /**
   * 指定ユーザーの全通知グループ設定を一括取得する（N+1解消用）。
   *
   * @param userId ユーザーID
   * @return 通知グループ → enabled フラグ のMap（設定なしのグループは含まない）
   */
  Map<NotificationGroup, Boolean> findAllEnabled(Long userId);

  /** 差分として upsert（存在すれば更新、なければ追加） */
  int upsert(
      Long userId, NotificationGroup group, boolean enabled, Long operatorUserId, String program);

  /** 差分を消す（= デフォルトに戻す） */
  int delete(Long userId, NotificationGroup group);
}
