package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHousework;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseworkExample;
import java.util.List;

public interface MHouseworkMapper {
    int deleteByPrimaryKey(Long houseworkId);

    int insert(MHousework row);

    int insertSelective(MHousework row);

    List<MHousework> selectByExample(MHouseworkExample example);

    MHousework selectByPrimaryKey(Long houseworkId);

    int updateByPrimaryKeySelective(MHousework row);

    int updateByPrimaryKey(MHousework row);
}