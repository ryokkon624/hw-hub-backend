package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseholdCustomMapper {
  int updateHouseholdName(
      @Param("householdId") Long householdId,
      @Param("name") String name,
      @Param("updateUserId") Long updateUserId,
      @Param("updateProgram") String updateProgram);
}
