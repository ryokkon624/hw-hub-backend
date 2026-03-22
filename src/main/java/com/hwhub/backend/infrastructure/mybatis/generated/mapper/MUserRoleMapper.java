package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserRole;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserRoleExample;
import java.util.List;

public interface MUserRoleMapper {
  int deleteByPrimaryKey(Long userRoleId);

  int insert(MUserRole row);

  int insertSelective(MUserRole row);

  List<MUserRole> selectByExample(MUserRoleExample example);

  MUserRole selectByPrimaryKey(Long userRoleId);

  int updateByPrimaryKeySelective(MUserRole row);

  int updateByPrimaryKey(MUserRole row);
}
