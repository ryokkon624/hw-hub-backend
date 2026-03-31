package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseworkTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseworkTemplateCustomMapper {

  void update(
      @Param("item") MHouseworkTemplate item,
      @Param("userId") Long userId,
      @Param("program") String program);
}
