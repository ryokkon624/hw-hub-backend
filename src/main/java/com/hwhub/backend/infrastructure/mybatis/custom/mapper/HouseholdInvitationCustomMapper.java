package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.HouseholdInvitationModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseholdInvitationCustomMapper {

  public HouseholdInvitationModel selectByToken(String token);

  public List<HouseholdInvitationModel> selectByHouseholdId(Long householdId);
}
