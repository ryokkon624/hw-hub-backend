package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.HouseholdModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserHouseholdCustomMapper {

  List<HouseholdModel> selectHouseholdsByUserId(@Param("userId") Long userId);
}
