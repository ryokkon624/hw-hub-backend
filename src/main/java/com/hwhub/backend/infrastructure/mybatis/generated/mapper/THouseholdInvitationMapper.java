package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseholdInvitation;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseholdInvitationExample;
import java.util.List;

public interface THouseholdInvitationMapper {
  int deleteByPrimaryKey(String invitationToken);

  int insert(THouseholdInvitation row);

  int insertSelective(THouseholdInvitation row);

  List<THouseholdInvitation> selectByExample(THouseholdInvitationExample example);

  THouseholdInvitation selectByPrimaryKey(String invitationToken);

  int updateByPrimaryKeySelective(THouseholdInvitation row);

  int updateByPrimaryKey(THouseholdInvitation row);
}
