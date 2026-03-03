package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.UserModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

  Optional<UserModel> findById(Long userId);

  Optional<UserModel> findByEmail(String email);

  List<HouseholdModel> findHouseholdsByUserId(Long userId);

  void updateForEnduser(UserModel user, Long userId, String updateProgram);

  UserModel insert(UserModel model, Long userId, String program);

  long countByEmail(String email);

  void updateProfileImgKey(UserModel model, String program);

  void deactivate(Long userId, String program);

  void updateForReactivation(UserModel user, Long userId, String program);

  void markEmailVerified(
      Long userId, LocalDateTime verifiedAt, Long updateUserId, String programTypeCode);

  void updatePassword(UserModel model, Long updateUserId, String programTypeCode);

  Optional<LocalDateTime> findPasswordChangedAt(Long userId);

  Optional<UserModel> findByAuthProviderAndAuthProviderId(String provider, String providerId);

  void updateAuthProvider(
      Long userId, String provider, String providerId, Long updateUserId, String programTypeCode);

  void linkGoogleAccount(
      Long userId, String googleSub, String email, String displayName, String programTypeCode);

  boolean isNotificationEnabled(Long userId);

  boolean updateNotificationEnabled(Long userId, boolean enabled, Long userId2, String code);
}
