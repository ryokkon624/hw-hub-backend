package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserPasswordReset;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserPasswordResetCustomMapper {

  Optional<LocalDateTime> findLatestRequestedAt(@Param("userId") Long userId);

  Optional<TUserPasswordReset> findUsableByTokenHash(
      @Param("tokenHash") byte[] tokenHash, @Param("now") LocalDateTime now);

  int markUsedIfUnused(
      @Param("id") Long id,
      @Param("usedAt") LocalDateTime usedAt,
      @Param("updateUserId") Long updateUserId,
      @Param("updateProgram") String updateProgram);

  int countRequestedOnDate(
      @Param("userId") Long userId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);
}
