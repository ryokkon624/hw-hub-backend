package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateId;
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel;
import com.hwhub.backend.domain.repository.HouseworkTemplateRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.HouseworkTemplateConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.HouseworkTemplateCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseworkTemplate;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.MHouseworkTemplateMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisHouseworkTemplateRepository implements HouseworkTemplateRepository {

  private final MHouseworkTemplateMapper mapper;
  private final HouseworkTemplateCustomMapper customMapper;

  @Override
  public List<HouseworkTemplateModel> findAll() {
    List<MHouseworkTemplate> records = mapper.selectByExample(null);
    return records.stream().map(HouseworkTemplateConverter::toModel).collect(Collectors.toList());
  }

  @Override
  public HouseworkTemplateModel insert(
      HouseworkTemplateModel model, Long operatorUserId, String program) {
    MHouseworkTemplate entity = HouseworkTemplateConverter.toEntity(model);
    entity.setCreateUserId(operatorUserId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(operatorUserId);
    entity.setUpdateProgram(program);
    mapper.insertSelective(entity);
    return HouseworkTemplateConverter.toModel(entity);
  }

  @Override
  public void update(HouseworkTemplateModel model, Long operatorUserId, String program) {
    MHouseworkTemplate entity = HouseworkTemplateConverter.toEntity(model);
    customMapper.update(entity, operatorUserId, program);
  }

  @Override
  public void delete(HouseworkTemplateId id, Long operatorUserId, String program) {
    mapper.deleteByPrimaryKey(id.value());
  }
}
