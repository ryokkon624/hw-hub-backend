package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.repository.HouseholdRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.HouseholdConverter;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHousehold;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.MHouseholdMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisHouseholdRepository implements HouseholdRepository {

  private final MHouseholdMapper mapper;

  @Override
  public HouseholdModel insert(HouseholdModel model, Long userId, String program) {
    MHousehold entity = HouseholdConverter.toEntity(model);
    entity.setCreateUserId(userId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);

    mapper.insertSelective(entity);

    return HouseholdConverter.toModel(entity);
  }

  @Override
  public HouseholdModel findById(Long householdId) {
    MHousehold entity = mapper.selectByPrimaryKey(householdId);
    return HouseholdConverter.toModel(entity);
  }

  @Override
  public void update(HouseholdModel model, Long userId, String program) {
    MHousehold entity = HouseholdConverter.toEntity(model);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);

    // null更新はあり得ない
    mapper.updateByPrimaryKeySelective(entity);
  }
}
