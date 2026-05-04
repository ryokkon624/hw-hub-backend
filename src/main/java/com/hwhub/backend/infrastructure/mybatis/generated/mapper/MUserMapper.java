package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUser;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserExample;
import java.util.List;

public interface MUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(MUser row);

    int insertSelective(MUser row);

    List<MUser> selectByExample(MUserExample example);

    MUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(MUser row);

    int updateByPrimaryKey(MUser row);
}