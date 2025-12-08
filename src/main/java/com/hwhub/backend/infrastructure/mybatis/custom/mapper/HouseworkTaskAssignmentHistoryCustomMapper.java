package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseworkTaskAssignmentHistoryCustomMapper {
  void insertClearedAssigneeHistory(
      @Param("householdId") Long householdId,
      @Param("assignee") Long assignee,
      @Param("userId") Long userId,
      @Param("program") String program);
}
