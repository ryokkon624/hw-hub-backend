package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.HouseworkTaskRecalcRequestModel;
import com.hwhub.backend.domain.repository.HouseworkTaskRecalcRequestRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.HouseworkTaskRecalcRequestConverter;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskRecalcRequest;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.THouseworkTaskRecalcRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisHouseworkTaskRecalcRequestRepository
    implements HouseworkTaskRecalcRequestRepository {

  private final THouseworkTaskRecalcRequestMapper mapper;

  @Override
  public void enqueue(
          HouseworkTaskRecalcRequestModel model, long userId, String program) {

    THouseworkTaskRecalcRequest entity = HouseworkTaskRecalcRequestConverter.toEntity(model);

    entity.setCreateUserId(userId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);

    mapper.insertSelective(entity);
  }
}
