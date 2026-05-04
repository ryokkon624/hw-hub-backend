package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseworkTemplate;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseworkTemplateExample;
import java.util.List;

public interface MHouseworkTemplateMapper {
    int deleteByPrimaryKey(Long houseworkTemplateId);

    int insert(MHouseworkTemplate row);

    int insertSelective(MHouseworkTemplate row);

    List<MHouseworkTemplate> selectByExample(MHouseworkTemplateExample example);

    MHouseworkTemplate selectByPrimaryKey(Long houseworkTemplateId);

    int updateByPrimaryKeySelective(MHouseworkTemplate row);

    int updateByPrimaryKey(MHouseworkTemplate row);
}