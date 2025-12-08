package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.HouseworkModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseworkCustomMapper {

  void clearDefaultAssignee(
      @Param("householdId") Long householdId,
      @Param("userId") Long userId,
      @Param("loginUserId") Long loginUserId,
      @Param("program") String program);

  void update(
      @Param("item") HouseworkModel item,
      @Param("userId") Long userId,
      @Param("program") String program);
}
