package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.HouseholdMemberModel;
import com.hwhub.backend.domain.repository.HouseholdMemberRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.HouseholdMemberConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.HouseholdMemberCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseholdMember;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.MHouseholdMemberMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisHouseholdMemberRepository implements HouseholdMemberRepository {

  private final HouseholdMemberCustomMapper customMapper;
  private final MHouseholdMemberMapper mapper;

  @Override
  public List<HouseholdMemberModel> findActiveByHouseholdId(Long householdId) {
    return customMapper.findActiveByHouseholdId(householdId);
  }

  @Override
  public boolean existsActiveByHouseholdIdAndUserId(Long householdId, Long userId) {
    int count = customMapper.countActiveByHouseholdIdAndUserId(householdId, userId);
    return count > 0;
  }

  @Override
  public void insert(HouseholdMemberModel model, Long userId, String program) {
    MHouseholdMember entity = HouseholdMemberConverter.toEntity(model);
    entity.setCreateUserId(userId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);

    mapper.insertSelective(entity);
  }

  @Override
  public HouseholdMemberModel findById(Long householdId, Long userId) {
    MHouseholdMember entity = mapper.selectByPrimaryKey(householdId, userId);
    return HouseholdMemberConverter.toModel(entity);
  }

  @Override
  public void update(HouseholdMemberModel model, Long userId, String program) {
    MHouseholdMember entity = HouseholdMemberConverter.toEntity(model);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);

    // null更新はあり得ない
    mapper.updateByPrimaryKeySelective(entity);
  }
}
