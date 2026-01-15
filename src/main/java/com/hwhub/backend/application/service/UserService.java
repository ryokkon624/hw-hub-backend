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
  private final com.hwhub.backend.domain.repository.HouseholdMemberRepository
      householdMemberRepository;

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

  @Transactional
  public void deleteAccount(Long userId) {
    // 所属世帯のチェック
    List<HouseholdModel> households = userRepository.findHouseholdsByUserId(userId);
    for (HouseholdModel h : households) {
      // 自分がOWNERの場合
      if (h.isOwner(userId)) {
        // メンバー数をチェック
        List<com.hwhub.backend.domain.model.HouseholdMemberModel> members =
            householdMemberRepository.findActiveByHouseholdId(h.getHouseholdId());
        if (members.size() > 1) {
          // 自分以外にもメンバーがいる場合は退会不可
          throw new IllegalArgumentException(
              "Cannot delete account because you are the owner of household '"
                  + h.getName()
                  + "' which has other members. Please transfer ownership or remove members first.");
        }
        // メンバーが自分のみならOK（この世帯は後日バッチ削除される）
      }
    }

    // ユーザーを非活性化 (論理削除)
    userRepository.deactivate(userId, ProgramType.ONL_USR.getCode());

    // 世帯メンバーから物理削除
    householdMemberRepository.deleteByUserId(userId);
  }
}
