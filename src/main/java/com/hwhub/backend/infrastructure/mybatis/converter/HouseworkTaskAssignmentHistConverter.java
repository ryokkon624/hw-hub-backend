package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.HouseworkTaskAssignmentHistModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskAssignmentHistory;

public final class HouseworkTaskAssignmentHistConverter {

  private HouseworkTaskAssignmentHistConverter() {}

  public static HouseworkTaskAssignmentHistModel toModel(THouseworkTaskAssignmentHistory entity) {
    if (entity == null) {
      return null;
    }

    return HouseworkTaskAssignmentHistModel.reconstruct(
        entity.getHouseworkTaskAssignmentHistoryId(),
        entity.getHouseworkTaskId(),
        entity.getHouseholdId(),
        entity.getFromAssigneeUserId(),
        entity.getToAssigneeUserId(),
        entity.getOperatedUserId(),
        entity.getAssignReasonType(),
        entity.getNote(),
        DateConverter.toLocalDateTime(entity.getChangedAt()));
  }

  public static THouseworkTaskAssignmentHistory toEntity(HouseworkTaskAssignmentHistModel model) {
    if (model == null) {
      return null;
    }

    THouseworkTaskAssignmentHistory entity = new THouseworkTaskAssignmentHistory();
    entity.setHouseworkTaskAssignmentHistoryId(model.getHouseworkTaskAssignmentHistoryId());
    entity.setHouseworkTaskId(model.getHouseworkTaskId());
    entity.setHouseholdId(model.getHouseholdId());
    entity.setFromAssigneeUserId(model.getFromAssigneeUserId());
    entity.setToAssigneeUserId(model.getToAssigneeUserId());
    entity.setOperatedUserId(model.getOperatedUserId());
    entity.setAssignReasonType(model.getAssignReasonType());
    entity.setNote(model.getNote());
    entity.setChangedAt(DateConverter.toDate(model.getChangedAt()));

    return entity;
  }
}
