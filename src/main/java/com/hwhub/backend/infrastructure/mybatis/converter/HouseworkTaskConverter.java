package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.HouseworkTask4AssignModel;
import com.hwhub.backend.domain.model.HouseworkTaskAssignmentHistModel;
import com.hwhub.backend.domain.model.HouseworkTaskModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTask;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskAssignmentHistory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class HouseworkTaskConverter {

  private HouseworkTaskConverter() {}

  public static HouseworkTaskModel toModel(
      THouseworkTask entity, List<THouseworkTaskAssignmentHistory> entities) {
    if (entity == null) return null;

    List<HouseworkTaskAssignmentHistModel> models = new ArrayList<>();
    if (!entities.isEmpty()) {
      models =
          entities.stream()
              .map(HouseworkTaskAssignmentHistConverter::toModel)
              .collect(Collectors.toList());
    }

    return HouseworkTask4AssignModel.reconstruct(
        entity.getHouseworkTaskId(),
        entity.getHouseholdId(),
        entity.getHouseworkId(),
        entity.getName(),
        entity.getDescription(),
        entity.getCategory(),
        DateConverter.toLocalDate(entity.getTargetDate()),
        entity.getAssigneeUserId(),
        entity.getStatus(),
        entity.getAssignReasonType(),
        DateConverter.toLocalDate(entity.getDoneAt()),
        entity.getSkippedReason(),
        models);
  }

  public static THouseworkTask toEntity(HouseworkTaskModel model) {
    if (model == null) return null;

    THouseworkTask entity = new THouseworkTask();
    entity.setHouseworkTaskId(model.getHouseworkTaskId());
    entity.setHouseholdId(model.getHouseholdId());
    entity.setHouseworkId(model.getHouseworkId());
    entity.setTargetDate(DateConverter.toDate(model.getTargetDate()));
    entity.setAssigneeUserId(model.getAssigneeUserId());
    entity.setStatus(model.getStatus());
    entity.setAssignReasonType(model.getAssignReasonType());
    entity.setDoneAt(DateConverter.toDate(model.getDoneAt()));
    entity.setSkippedReason(model.getSkippedReason());

    return entity;
  }
}
