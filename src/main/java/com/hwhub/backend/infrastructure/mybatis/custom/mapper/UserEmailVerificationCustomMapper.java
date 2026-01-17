package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.UserEmailVerificationModel;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserEmailVerificationCustomMapper {

  Optional<UserEmailVerificationModel> findUsableByTokenHash(
      @Param("tokenHash") byte[] tokenHash, @Param("now") LocalDateTime now);

  int markUsed(
      @Param("id") Long userEmailVerificationId,
      @Param("usedAt") LocalDateTime usedAt,
      @Param("updateUserId") Long updateUserId,
      @Param("updateProgram") String updateProgram);

  Optional<LocalDateTime> findLatestRequestedAt(@Param("userId") Long userId);

  int countRequestedSince(@Param("userId") Long userId, @Param("since") LocalDateTime since);
}
