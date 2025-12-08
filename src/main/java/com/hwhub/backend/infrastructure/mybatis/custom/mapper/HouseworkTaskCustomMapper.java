package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.HouseworkTask4AssignModel;
import com.hwhub.backend.domain.model.HouseworkTaskModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseworkTaskCustomMapper {

  List<HouseworkTask4AssignModel> selectTasksForAssign(
      @Param("householdId") Long householdId, @Param("status") String status);

  void updateByTaskId4Online(
      @Param("item") HouseworkTaskModel item,
      @Param("updateUserId") Long updateUserId,
      @Param("updateProgram") String updateProgram);

  void clearAssignee(
      @Param("householdId") Long householdId,
      @Param("assigneeUserId") Long assigneeUserId,
      @Param("loginUserId") Long loginUserId,
      @Param("program") String program);
}
