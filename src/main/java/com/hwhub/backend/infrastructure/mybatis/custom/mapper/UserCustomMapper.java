package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserCustomMapper {
  Optional<LocalDateTime> findPasswordChangedAt(@Param("userId") Long userId);
}
