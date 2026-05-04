package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskAssignmentHistory;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskAssignmentHistoryExample;
import java.util.List;

public interface THouseworkTaskAssignmentHistoryMapper {
  int deleteByPrimaryKey(Long houseworkTaskAssignmentHistoryId);

  int insert(THouseworkTaskAssignmentHistory row);

  int insertSelective(THouseworkTaskAssignmentHistory row);

  List<THouseworkTaskAssignmentHistory> selectByExample(
      THouseworkTaskAssignmentHistoryExample example);

  THouseworkTaskAssignmentHistory selectByPrimaryKey(Long houseworkTaskAssignmentHistoryId);

  int updateByPrimaryKeySelective(THouseworkTaskAssignmentHistory row);

  int updateByPrimaryKey(THouseworkTaskAssignmentHistory row);
}
