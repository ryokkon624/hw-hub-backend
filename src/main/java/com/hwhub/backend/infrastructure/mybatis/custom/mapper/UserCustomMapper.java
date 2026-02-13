package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserCustomMapper {
  Optional<LocalDateTime> findPasswordChangedAt(@Param("userId") Long userId);

  int updateAuthProvider(
      @Param("userId") Long userId,
      @Param("authProvider") String authProvider,
      @Param("authProviderId") String authProviderId,
      @Param("updateUserId") Long updateUserId,
      @Param("programTypeCode") String programTypeCode);

  int linkGoogleAccount(
      @Param("userId") Long userId,
      @Param("authProvider") String authProvider,
      @Param("authProviderId") String authProviderId,
      @Param("email") String email,
      @Param("displayName") String displayName,
      @Param("program") String program);
}
