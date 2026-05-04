package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.HouseworkModel;
import com.hwhub.backend.domain.repository.HouseworkRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.HouseworkConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.HouseworkCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHousework;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseworkExample;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.MHouseworkMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisHouseworkRepository implements HouseworkRepository {

  private final MHouseworkMapper mapper;
  private final HouseworkCustomMapper customMapper;

  @Override
  public HouseworkModel findByHouseworkId(Long houseworkId) {
    var entity = mapper.selectByPrimaryKey(houseworkId);
    return entity == null ? null : HouseworkConverter.toModel(entity);
  }

  @Override
  public List<HouseworkModel> findByHouseholdId(Long householdId) {
    MHouseworkExample example = new MHouseworkExample();
    example.createCriteria().andHouseholdIdEqualTo(householdId);

    return mapper.selectByExample(example).stream().map(HouseworkConverter::toModel).toList();
  }

  @Override
  public HouseworkModel insert(HouseworkModel model, Long userId, String program) {
    MHousework entity = HouseworkConverter.toEntity(model);
    entity.setCreateUserId(userId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);
    mapper.insertSelective(entity);

    return HouseworkConverter.toModel(entity);
  }

  @Override
  public HouseworkModel update(HouseworkModel model, Long userId, String program) {
    MHousework entity = HouseworkConverter.toEntity(model);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);
    customMapper.update(model, userId, program);

    return HouseworkConverter.toModel(entity);
  }

  @Override
  public void clearAssignee(Long householdId, Long userId, Long loginUserId, String program) {
    customMapper.clearDefaultAssignee(householdId, userId, loginUserId, program);
  }
}
