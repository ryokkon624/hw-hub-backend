package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.enums.InvitationStatus;
import com.hwhub.backend.domain.model.HouseholdInvitationModel;
import com.hwhub.backend.domain.repository.HouseholdInvitationRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.HouseholdInvitationConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.HouseholdInvitationCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseholdInvitation;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseholdInvitationExample;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.THouseholdInvitationMapper;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisHouseholdInvitationRepository implements HouseholdInvitationRepository {

  private final HouseholdInvitationCustomMapper customMapper;
  private final THouseholdInvitationMapper mapper;

  @Override
  public HouseholdInvitationModel selectByToken(String token) {
    return customMapper.selectByToken(token);
  }

  @Override
  public List<HouseholdInvitationModel> selectByHouseholdId(Long householdId) {
    return customMapper.selectByHouseholdId(householdId);
  }

  @Override
  public void update(HouseholdInvitationModel model, Long userId, String program) {
    THouseholdInvitation entity = HouseholdInvitationConverter.toEntity(model);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);
    mapper.updateByPrimaryKeySelective(entity);
  }

  @Override
  public HouseholdInvitationModel insert(
      HouseholdInvitationModel model, Long userId, String program) {
    THouseholdInvitation entity = HouseholdInvitationConverter.toEntity(model);
    entity.setCreateUserId(userId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);
    mapper.insertSelective(entity);

    return customMapper.selectByToken(entity.getInvitationToken());
  }

  @Override
  public long countByActiveEmail(Long householdId, String email) {
    THouseholdInvitationExample example = new THouseholdInvitationExample();
    example
        .createCriteria()
        .andHouseholdIdEqualTo(householdId)
        .andInvitedEmailEqualTo(email)
        .andStatusEqualTo(InvitationStatus.PENDING.getCode())
        .andExpiresAtGreaterThan(new Date());

    return mapper.selectByExample(example).size();
  }
}
