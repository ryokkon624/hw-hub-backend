package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.UserEmailVerificationModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserEmailVerification;

public class UserEmailVerificationConverter {
  private UserEmailVerificationConverter() {}

  public static UserEmailVerificationModel toModel(TUserEmailVerification entity) {
    if (entity == null) {
      return null;
    }

    return UserEmailVerificationModel.reconstruct(
        entity.getUserEmailVerificationId(),
        entity.getUserId(),
        entity.getTokenHash(),
        DateConverter.toLocalDateTime(entity.getExpiresAt()),
        DateConverter.toLocalDateTime(entity.getUsedAt()),
        DateConverter.toLocalDateTime(entity.getRequestedAt()),
        entity.getRequestCount());
  }

  public static TUserEmailVerification toEntity(UserEmailVerificationModel model) {
    if (model == null) {
      return null;
    }

    TUserEmailVerification entity = new TUserEmailVerification();
    entity.setUserEmailVerificationId(model.getUserEmailVerificationId());
    entity.setUserId(model.getUserId());
    entity.setTokenHash(model.getTokenHash());
    entity.setExpiresAt(DateConverter.toDate(model.getExpiresAt()));
    entity.setUsedAt(DateConverter.toDate(model.getUsedAt()));
    entity.setRequestedAt(DateConverter.toDate(model.getRequestedAt()));
    entity.setRequestCount(model.getRequestCount());

    return entity;
  }
}
