package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUser;

public final class UserConverter {

  private UserConverter() {}

  public static UserModel toModel(MUser entity) {
    if (entity == null) {
      return null;
    }

    return UserModel.reconstruct(
        entity.getUserId(),
        entity.getEmail(),
        entity.getPasswordHash(),
        DateConverter.toLocalDateTime(entity.getPasswordChangedAt()),
        entity.getAuthProvider(),
        entity.getAuthProviderId(),
        entity.getDisplayName(),
        entity.getLocale(),
        entity.getNotificationEnabled(),
        entity.getProfileImageKey(),
        DateConverter.toLocalDateTime(entity.getEmailVerifiedAt()),
        entity.getIsActive(),
        DateConverter.toLocalDateTime(entity.getCreatedAt()),
        DateConverter.toLocalDateTime(entity.getUpdatedAt()));
  }

  public static MUser toEntity(UserModel model) {
    if (model == null) {
      return null;
    }
    MUser entity = new MUser();

    entity.setUserId(model.getUserId());
    entity.setEmail(model.getEmail());
    entity.setPasswordHash(model.getPasswordHash());
    entity.setPasswordChangedAt(DateConverter.toDate(model.getPasswordChangedAt()));
    entity.setAuthProvider(model.getAuthProvider());
    entity.setAuthProviderId(model.getAuthProviderId());
    entity.setDisplayName(model.getDisplayName());
    entity.setLocale(model.getLocale());
    entity.setNotificationEnabled(model.isNotificationEnabled());
    entity.setProfileImageKey(model.getProfileImageKey());
    entity.setEmailVerifiedAt(DateConverter.toDate(model.getEmailVerifiedAt()));
    entity.setIsActive(model.isActive());
    // created_at, updated_at はDB側で更新するため、渡さない

    return entity;
  }
}
