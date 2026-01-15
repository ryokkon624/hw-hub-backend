package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.UserModel;
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
}
