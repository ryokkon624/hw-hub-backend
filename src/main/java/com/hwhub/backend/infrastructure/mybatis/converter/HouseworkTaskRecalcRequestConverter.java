package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.HouseworkTaskRecalcRequestModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskRecalcRequest;

public final class HouseworkTaskRecalcRequestConverter {
  private HouseworkTaskRecalcRequestConverter() {}

  public static THouseworkTaskRecalcRequest toEntity(HouseworkTaskRecalcRequestModel model) {
    if (model == null) {
      return null;
    }

    THouseworkTaskRecalcRequest entity = new THouseworkTaskRecalcRequest();
    entity.setHouseworkId(model.getHouseworkId());
    entity.setRecalcRequestStatus(model.getRecalcRequestStatus());
    entity.setRetryCount(model.getRetryCount());
    entity.setLastErrorMessage(model.getLastErrorMessage());

    return entity;
  }
}
