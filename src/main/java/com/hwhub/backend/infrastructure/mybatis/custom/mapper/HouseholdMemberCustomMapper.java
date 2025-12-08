package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.HouseholdMemberModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseholdMemberCustomMapper {
  List<HouseholdMemberModel> findActiveByHouseholdId(Long householdId);

  int countActiveByHouseholdIdAndUserId(
      @Param("householdId") Long householdId, @Param("userId") Long userId);
}
