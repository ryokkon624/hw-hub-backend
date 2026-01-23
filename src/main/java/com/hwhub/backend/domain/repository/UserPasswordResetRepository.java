package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.UserPasswordResetModel;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserPasswordResetRepository {

  Long insert(UserPasswordResetModel model, Long createUserId, String createProgram);

  Optional<LocalDateTime> findLatestRequestedAt(Long userId);

  Optional<UserPasswordResetModel> findUsableByTokenHash(byte[] tokenHash, LocalDateTime now);

  /**
   * used_at を更新して使用済みにする（競合に強くするため used_at IS NULL 条件を含めること）
   *
   * @return 更新件数（1なら成功、0なら競合 or 既に使用済み）
   */
  int markUsedIfUnused(
      Long userPasswordResetId, LocalDateTime usedAt, Long updateUserId, String updateProgram);

  /** 指定日のリセット要求件数を返す（JST基準） */
  int countRequestedOnDate(Long userId, LocalDateTime start, LocalDateTime end);
}
