package com.hwhub.backend.domain.model;

import com.hwhub.backend.domain.enums.TaskRecalcStatus;
import lombok.Getter;

@Getter
public class HouseworkTaskRecalcRequestModel {
  private final Long requestId;
  private final Long houseworkId;
  private final String recalcRequestStatus;
  private final Integer retryCount;
  private final String lastErrorMessage;

  private HouseworkTaskRecalcRequestModel(
      Long requestId,
      Long houseworkId,
      String recalcRequestStatus,
      Integer retryCount,
      String lastErrorMessage) {
    this.requestId = requestId;
    this.houseworkId = houseworkId;
    this.recalcRequestStatus = recalcRequestStatus;
    this.retryCount = retryCount;
    this.lastErrorMessage = lastErrorMessage;
  }

  public static HouseworkTaskRecalcRequestModel create(Long houseworkId) {
    return new HouseworkTaskRecalcRequestModel(
        null, houseworkId, TaskRecalcStatus.PENDING.getCode(), 0, null);
  }
}
