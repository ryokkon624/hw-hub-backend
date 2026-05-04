package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTask;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskExample;
import java.util.List;

public interface THouseworkTaskMapper {
    int deleteByPrimaryKey(Long houseworkTaskId);

    int insert(THouseworkTask row);

    int insertSelective(THouseworkTask row);

    List<THouseworkTask> selectByExample(THouseworkTaskExample example);

    THouseworkTask selectByPrimaryKey(Long houseworkTaskId);

    int updateByPrimaryKeySelective(THouseworkTask row);

    int updateByPrimaryKey(THouseworkTask row);
}