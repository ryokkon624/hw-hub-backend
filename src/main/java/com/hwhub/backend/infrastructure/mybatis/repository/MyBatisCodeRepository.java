package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.CodeModel;
import com.hwhub.backend.domain.repository.CodeRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.CodeConverter;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MCode;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MCodeExample;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.MCodeMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisCodeRepository implements CodeRepository {

  private final MCodeMapper mapper;

  @Override
  public List<CodeModel> findAll() {
    MCodeExample example = new MCodeExample();
    example.setOrderByClause("code_type, code_value");

    List<MCode> entities = mapper.selectByExample(example);
    return entities.stream().map(CodeConverter::toModel).toList();
  }

  @Override
  public List<CodeModel> findByCodeType(String codeType) {
    MCodeExample example = new MCodeExample();
    example.createCriteria().andCodeTypeEqualTo(codeType);
    example.setOrderByClause("code_value");

    List<MCode> entities = mapper.selectByExample(example);
    return entities.stream().map(CodeConverter::toModel).toList();
  }
}
