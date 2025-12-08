package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserIconService userIconService;

  public List<HouseholdModel> getHouseholds(Long userId) {
    return userRepository.findHouseholdsByUserId(userId);
  }

  @Transactional(readOnly = true)
  public UserModel getProfile(Long userId) {
    UserModel model =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found. userId=" + userId));
    model.setIconUrl(userIconService.getIconUrl(model.getProfileImageKey()));
    return model;
  }

  @Transactional
  public UserModel updateProfile(Long userId, String displayName, String locale) {
    UserModel user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found. userId=" + userId));

    user.changeProfile(displayName, locale);
    userRepository.updateForEnduser(user, userId, ProgramType.ONL_USR.getCode());

    user.setIconUrl(userIconService.getIconUrl(user.getProfileImageKey()));
    return user;
  }
}
