package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.UserEmailVerificationModel;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserEmailVerificationRepository {

  Long insert(UserEmailVerificationModel model, Long createUserId, String createProgram);

  Optional<UserEmailVerificationModel> findUsableByTokenHash(byte[] tokenHash, LocalDateTime now);

  void markUsed(
      Long userEmailVerificationId, LocalDateTime usedAt, Long updateUserId, String updateProgram);

  Optional<LocalDateTime> findLatestRequestedAt(Long userId);

  int countRequestedSince(Long userId, LocalDateTime since);
}
