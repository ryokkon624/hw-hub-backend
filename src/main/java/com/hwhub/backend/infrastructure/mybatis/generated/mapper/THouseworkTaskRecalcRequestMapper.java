package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskRecalcRequest;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskRecalcRequestExample;
import java.util.List;

public interface THouseworkTaskRecalcRequestMapper {
  int deleteByPrimaryKey(Long requestId);

  int insert(THouseworkTaskRecalcRequest row);

  int insertSelective(THouseworkTaskRecalcRequest row);

  List<THouseworkTaskRecalcRequest> selectByExample(THouseworkTaskRecalcRequestExample example);

  THouseworkTaskRecalcRequest selectByPrimaryKey(Long requestId);

  int updateByPrimaryKeySelective(THouseworkTaskRecalcRequest row);

  int updateByPrimaryKey(THouseworkTaskRecalcRequest row);
}
