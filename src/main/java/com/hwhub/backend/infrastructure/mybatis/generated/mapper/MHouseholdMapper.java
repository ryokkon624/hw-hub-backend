package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHousehold;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseholdExample;
import java.util.List;

public interface MHouseholdMapper {
    int deleteByPrimaryKey(Long householdId);

    int insert(MHousehold row);

    int insertSelective(MHousehold row);

    List<MHousehold> selectByExample(MHouseholdExample example);

    MHousehold selectByPrimaryKey(Long householdId);

    int updateByPrimaryKeySelective(MHousehold row);

    int updateByPrimaryKey(MHousehold row);
}