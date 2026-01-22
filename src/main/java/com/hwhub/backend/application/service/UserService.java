package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.HouseholdMemberRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.common.CurrentPasswordInvalidException;
import com.hwhub.backend.presentation.rest.common.PasswordSameAsOldException;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserIconService userIconService;
  private final HouseholdMemberRepository householdMemberRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthUserResolver authUserResolver;

  public List<HouseholdModel> getHouseholds(Long userId) {
    return userRepository.findHouseholdsByUserId(userId);
  }

  @Transactional(readOnly = true)
  public UserModel getProfile(Long userId) {
    UserModel model = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found. userId=" + userId));
    model.setIconUrl(userIconService.getIconUrl(model.getProfileImageKey()));
    return model;
  }

  @Transactional
  public UserModel updateProfile(Long userId, String displayName, String locale) {
    UserModel user = userRepository.findById(userId)
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
              "Cannot delete account because you are the owner of household '" + h.getName()
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

  /**
   * ログイン中ユーザのパスワードを変更する。
   *
   * <p>
   * 重要： passwordChangedAt を更新して、既存JWTを（後段の検証で）無効化できるようにする。
   */
  @Transactional
  public void changePassword(String currentPassword, String newPassword) {
    Long userId = authUserResolver.requireUserId();

    UserModel user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

    // 現在パスワード一致チェック
    if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
      throw new CurrentPasswordInvalidException();
    }

    // 同一パスワード禁止
    if (passwordEncoder.matches(newPassword, user.getPasswordHash())) {
      throw new PasswordSameAsOldException();
    }

    // パスワード更新
    String newHash = passwordEncoder.encode(newPassword);
    LocalDateTime now = LocalDateTime.now();
    user.changePasswordHash(newHash, now);
    userRepository.updatePassword(user, userId, ProgramType.ONL_USR.getCode());
  }
}
