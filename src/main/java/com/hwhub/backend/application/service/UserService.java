package com.hwhub.backend.application.service;

import com.hwhub.backend.application.service.oauth.GoogleOAuthService;
import com.hwhub.backend.domain.enums.AuthProvider;
import com.hwhub.backend.domain.enums.NotificationGroup;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.enums.ThemeMode;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo;
import com.hwhub.backend.domain.repository.HouseholdMemberRepository;
import com.hwhub.backend.domain.repository.UserNotificationSettingRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.common.CurrentPasswordInvalidException;
import com.hwhub.backend.presentation.rest.common.GoogleAccountAlreadyLinkedException;
import com.hwhub.backend.presentation.rest.common.GoogleSubAlreadyUsedException;
import com.hwhub.backend.presentation.rest.common.PasswordSameAsOldException;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserNotificationSettingRepository settingRepository;
  private final UserIconService userIconService;
  private final HouseholdMemberRepository householdMemberRepository;
  private final PasswordEncoder passwordEncoder;
  private final GoogleOAuthService googleOAuthService;

  public List<HouseholdModel> getHouseholds(Long userId) {
    return userRepository.findHouseholdsByUserId(userId);
  }

  @Transactional(readOnly = true)
  public UserModel getProfile(Long userId) {
    UserModel model =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    model.setIconUrl(userIconService.getIconUrl(model.getProfileImageKey()));
    return model;
  }

  @Transactional
  public UserModel updateProfile(Long userId, String displayName, String locale) {
    UserModel user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    user.changeProfile(displayName, locale);
    userRepository.updateForEnduser(user, userId, ProgramType.ONL_USR.getCode());

    user.setIconUrl(userIconService.getIconUrl(user.getProfileImageKey()));

    return user;
  }

  @Transactional
  public void deleteAccount(Long userId) {
    // 所属世帯のチェック
    List<HouseholdModel> households = userRepository.findHouseholdsByUserId(userId);

    // OWNERの世帯IDを一括抽出
    List<Long> ownerHouseholdIds =
        households.stream().filter(h -> h.isOwner(userId)).map(h -> h.getHouseholdId()).toList();

    if (!ownerHouseholdIds.isEmpty()) {
      // OWNERの世帯のメンバー数を一括取得（N+1 解消）
      Map<Long, Integer> memberCountMap =
          householdMemberRepository.countActiveMembersByHouseholdIds(ownerHouseholdIds);

      for (HouseholdModel h : households) {
        if (!h.isOwner(userId)) continue;
        int memberCount = memberCountMap.getOrDefault(h.getHouseholdId(), 0);
        if (memberCount > 1) {
          // 自分以外にもメンバーがいる場合は退会不可
          throw new IllegalArgumentException("Cannot delete account: household has other members");
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
   * <p>重要： passwordChangedAt を更新して、既存JWTを（後段の検証で）無効化できるようにする。
   */
  @Transactional
  public void changePassword(Long userId, String currentPassword, String newPassword) {
    UserModel user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

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

  @Transactional
  public void linkGoogleAccount(Long loginUserId, String code) {
    // code -> token -> userinfo
    var token = googleOAuthService.exchangeCodeForToken(code);
    GoogleUserInfo info = googleOAuthService.fetchUserInfo(token.getAccessToken());

    linkGoogleAccountByInfo(loginUserId, info);
  }

  /**
   * モバイル用 Google アカウント連携。Flutter が取得した idToken を検証してリンクする。
   *
   * @param loginUserId 連携対象のログイン中ユーザーID
   * @param idToken Flutter(google_sign_in) が取得した Google ID Token
   */
  @Transactional
  public void linkGoogleAccountByIdToken(Long loginUserId, String idToken) {
    GoogleUserInfo info = googleOAuthService.verifyIdToken(idToken);
    linkGoogleAccountByInfo(loginUserId, info);
  }

  /**
   * GoogleUserInfo を使って Google アカウントをリンクする共通処理。
   *
   * @param loginUserId 連携対象のログイン中ユーザーID
   * @param info 検証済みの Google ユーザー情報
   */
  private void linkGoogleAccountByInfo(Long loginUserId, GoogleUserInfo info) {
    // すでにこのログインユーザーが GOOGLE 連携済みなら弾く
    var user = userRepository.findById(loginUserId).orElseThrow();
    if (AuthProvider.GOOGLE.getCode().equals(user.getAuthProvider())
        && user.getAuthProviderId() != null) {
      throw new GoogleAccountAlreadyLinkedException();
    }

    // sub の重複チェック（別ユーザーがこのsubを使ってたらNG）
    userRepository
        .findByAuthProviderAndAuthProviderId(AuthProvider.GOOGLE.getCode(), info.getSub())
        .ifPresent(
            existingUser -> {
              if (!existingUser.getUserId().equals(loginUserId)) {
                throw new GoogleSubAlreadyUsedException();
              }
            });

    // password_hash を NULL に更新＝以後パスワードログイン不可
    String displayName =
        info.getName() != null && !info.getName().isBlank()
            ? info.getName()
            : user.getDisplayName();

    userRepository.linkGoogleAccount(
        loginUserId, info.getSub(), info.getEmail(), displayName, ProgramType.ONL_USR.getCode());
  }

  @Transactional(readOnly = true)
  public NotificationSettingsResult getSettings(Long userId) {

    boolean notificationEnabled = userRepository.isNotificationEnabled(userId);

    Map<NotificationGroup, Boolean> merged = buildNotificationGroupMap(userId, notificationEnabled);

    return new NotificationSettingsResult(notificationEnabled, merged);
  }

  @Transactional
  public NotificationSettingsResult updateNotificationEnabled(
      Long userId, boolean notificationEnabled, Map<NotificationGroup, Boolean> groupSettings) {

    userRepository.updateNotificationEnabled(
        userId, notificationEnabled, userId, ProgramType.ONL_USR.getCode());

    if (notificationEnabled) {
      for (var e : groupSettings.entrySet()) {
        NotificationGroup group = e.getKey();
        Boolean enabled = e.getValue();

        if (enabled == null) continue;

        if (enabled) {
          // 差分を削除
          settingRepository.delete(userId, group);
        } else {
          // 差分を登録
          settingRepository.upsert(userId, group, false, userId, ProgramType.ONL_USR.getCode());
        }
      }
    }

    Map<NotificationGroup, Boolean> merged = buildNotificationGroupMap(userId, notificationEnabled);

    return new NotificationSettingsResult(notificationEnabled, merged);
  }

  /**
   * NotificationGroupを全列挙し、指定されたユーザの設定値を含めて返却する。
   *
   * <p>ループ内でクエリを発行する N+1 を避けるため、全グループの設定を一括取得してから参照する。
   *
   * @param userId ユーザID
   * @param notificationEnabled 通知有効フラグ
   * @return NotificationGroupと設定値のマップ
   */
  private Map<NotificationGroup, Boolean> buildNotificationGroupMap(
      Long userId, boolean notificationEnabled) {
    // 全グループの設定を一括取得（N+1 解消）
    Map<NotificationGroup, Boolean> enabledSettings =
        notificationEnabled ? settingRepository.findAllEnabled(userId) : Map.of();

    Map<NotificationGroup, Boolean> map = new LinkedHashMap<>();
    for (NotificationGroup g : NotificationGroup.values()) {
      // 設定がなければデフォルトで true（差分管理方式）
      boolean gEnabled = notificationEnabled && enabledSettings.getOrDefault(g, true);
      map.put(g, gEnabled);
    }
    return map;
  }

  /**
   * テーマモードを更新する。
   *
   * @param userId ユーザID
   * @param themeMode 新しいテーマモード
   */
  @Transactional
  public void updateThemeMode(Long userId, ThemeMode themeMode) {
    UserModel user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    user.changeThemeMode(themeMode);
    userRepository.updateThemeMode(user, userId, ProgramType.ONL_USR.getCode());
  }

  public record NotificationSettingsResult(
      boolean notificationEnabled, Map<NotificationGroup, Boolean> groupSettings) {}
}
