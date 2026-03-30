package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.HouseworkTemplate.HouseworkTemplateModel;
import com.hwhub.backend.domain.repository.HouseworkTemplateRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.HouseworkTemplateConverter;
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

  @Override
  public List<HouseworkTemplateModel> findAll() {
    List<MHouseworkTemplate> records = mapper.selectByExample(null);
    return records.stream().map(HouseworkTemplateConverter::toModel).collect(Collectors.toList());
  }
}
