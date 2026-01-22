package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.UserPasswordResetModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserPasswordReset;

public class UserPasswordResetConverter {

  public static UserPasswordResetModel toModel(TUserPasswordReset entity) {
    if (entity == null) return null;

    return UserPasswordResetModel.reconstruct(
        entity.getUserPasswordResetId(),
        entity.getUserId(),
        entity.getTokenHash(),
        DateConverter.toLocalDateTime(entity.getExpiresAt()),
        DateConverter.toLocalDateTime(entity.getUsedAt()),
        DateConverter.toLocalDateTime(entity.getRequestedAt()),
        entity.getRequestCount());
  }

  public static TUserPasswordReset toEntity(UserPasswordResetModel model) {
    if (model == null) return null;

    TUserPasswordReset entity = new TUserPasswordReset();
    entity.setUserPasswordResetId(model.getUserPasswordResetId());
    entity.setUserId(model.getUserId());
    entity.setTokenHash(model.getTokenHash());
    entity.setExpiresAt(DateConverter.toDate(model.getExpiresAt()));
    entity.setUsedAt(DateConverter.toDate(model.getUsedAt()));
    entity.setRequestedAt(DateConverter.toDate(model.getRequestedAt()));
    entity.setRequestCount(model.getRequestCount());
    return entity;
  }
}
