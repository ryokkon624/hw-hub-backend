package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseholdMember;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseholdMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MHouseholdMemberMapper {
    int deleteByPrimaryKey(@Param("householdId") Long householdId, @Param("userId") Long userId);

    int insert(MHouseholdMember row);

    int insertSelective(MHouseholdMember row);

    List<MHouseholdMember> selectByExample(MHouseholdMemberExample example);

    MHouseholdMember selectByPrimaryKey(@Param("householdId") Long householdId, @Param("userId") Long userId);

    int updateByPrimaryKeySelective(MHouseholdMember row);

    int updateByPrimaryKey(MHouseholdMember row);
}