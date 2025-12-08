package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MCode;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MCodeMapper {
    int deleteByPrimaryKey(@Param("codeType") String codeType, @Param("codeValue") String codeValue);

    int insert(MCode row);

    int insertSelective(MCode row);

    List<MCode> selectByExample(MCodeExample example);

    MCode selectByPrimaryKey(@Param("codeType") String codeType, @Param("codeValue") String codeValue);

    int updateByPrimaryKeySelective(MCode row);

    int updateByPrimaryKey(MCode row);
}