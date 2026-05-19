package com.hwhub.backend.presentation.rest.user;

import com.hwhub.backend.application.service.UserIconService;
import com.hwhub.backend.application.service.UserService;
import com.hwhub.backend.application.service.UserService.NotificationSettingsResult;
import com.hwhub.backend.domain.enums.NotificationGroup;
import com.hwhub.backend.domain.enums.ThemeMode;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.presentation.rest.auth.GoogleOAuthLinkHelper;
import com.hwhub.backend.presentation.rest.auth.dto.GoogleMobileLoginRequest;
import com.hwhub.backend.presentation.rest.user.dto.ChangePasswordRequest;
import com.hwhub.backend.presentation.rest.user.dto.CreateIconUploadUrlRequest;
import com.hwhub.backend.presentation.rest.user.dto.CreateIconUploadUrlResponse;
import com.hwhub.backend.presentation.rest.user.dto.NotificationSettingsResponse;
import com.hwhub.backend.presentation.rest.user.dto.OAuthStartResponse;
import com.hwhub.backend.presentation.rest.user.dto.UpdateIconRequest;
import com.hwhub.backend.presentation.rest.user.dto.UpdateNotificationSettingsRequest;
import com.hwhub.backend.presentation.rest.user.dto.UpdateThemeRequest;
import com.hwhub.backend.presentation.rest.user.dto.UpdateUserProfileRequest;
import com.hwhub.backend.presentation.rest.user.dto.UserHouseholdDto;
import com.hwhub.backend.presentation.rest.user.dto.UserProfileResponse;
import com.hwhub.backend.security.CurrentUserId;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * ユーザー関連の操作（プロフィール管理、所属世帯情報の取得など）を行うAPIコントローラです。
 *
 * <p>このエンドポイントは、認証されたユーザーのみが利用可能です。 ベースパス: /api/user
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserIconService userIconService;
  private final GoogleOAuthLinkHelper linkHelper;

  /**
   * 認証ユーザーが所属する全ての世帯（Household）の情報を取得します。
   *
   * @param userId 認証済みユーザーID
   * @return ユーザーが所属する世帯情報（IDと名称）のリスト
   */
  @GetMapping("/me/households")
  public List<UserHouseholdDto> getUserHouseholds(@CurrentUserId Long userId) {
    return userService.getHouseholds(userId).stream().map(UserHouseholdDto::fromModel).toList();
  }

  /**
   * 認証ユーザーの現在のプロフィール情報（表示名、言語設定など）を取得します。
   *
   * @param userId 認証済みユーザーID
   * @return 認証ユーザーのプロフィール情報を含むレスポンスオブジェクト
   */
  @GetMapping("/me/profile")
  public UserProfileResponse getProfile(@CurrentUserId Long userId) {
    UserModel user = userService.getProfile(userId);
    return UserProfileResponse.fromModel(user);
  }

  /**
   * 認証ユーザーのプロフィール情報（表示名、言語設定など）を更新します。
   *
   * @param userId 認証済みユーザーID
   * @param request 更新するプロフィール情報（displayName, locale）
   * @return 更新後のプロフィール情報を含むレスポンスオブジェクト
   */
  @PutMapping("/me/profile")
  public UserProfileResponse updateProfile(
      @CurrentUserId Long userId, @Valid @RequestBody UpdateUserProfileRequest request) {
    UserModel updated = userService.updateProfile(userId, request.displayName(), request.locale());
    return UserProfileResponse.fromModel(updated);
  }

  @PostMapping("/me/icon/upload-url")
  public CreateIconUploadUrlResponse createIconUploadUrl(
      @Valid @RequestBody CreateIconUploadUrlRequest request, @CurrentUserId Long userId) {
    var result = userIconService.createUploadUrl(userId, request.fileName(), request.mimeType());
    return new CreateIconUploadUrlResponse(result.uploadUrl(), result.fileKey());
  }

  @PostMapping("/me/icon")
  public void updateIcon(
      @Valid @RequestBody UpdateIconRequest request, @CurrentUserId Long userId) {
    userIconService.updateUserIcon(userId, request.fileKey());
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAccount(@CurrentUserId Long userId) {
    userService.deleteAccount(userId);
  }

  /**
   * ログイン中のパスワード変更
   *
   * @param request 入力値
   * @return 204 No Content（成功時は返却なし）
   */
  @PutMapping("/me/password")
  public ResponseEntity<Void> changePassword(
      @CurrentUserId Long userId, @Valid @RequestBody ChangePasswordRequest request) {
    userService.changePassword(userId, request.currentPassword(), request.newPassword());
    return ResponseEntity.noContent().build();
  }

  /**
   * モバイル用 Google アカウント連携（ログイン中ユーザーのみ）
   *
   * <p>Flutter(google_sign_in) が取得した idToken を受け取り、Google のサーバーで検証後に HwHub ユーザーと連携する。 Web
   * 版の連携フロー（OAuth Authorization Code）と異なり、モバイルでは idToken を直接受け取る。
   *
   * <p>POST /api/users/me/google/link/mobile
   */
  @PostMapping("/me/google/link/mobile")
  public ResponseEntity<Void> linkGoogleAccountByMobile(
      @CurrentUserId Long userId, @Valid @RequestBody GoogleMobileLoginRequest request) {
    userService.linkGoogleAccountByIdToken(userId, request.idToken());
    return ResponseEntity.noContent().build();
  }

  /** Googleアカウント連携開始（ログイン中ユーザーのみ） GET /api/users/me/google/link/start */
  @GetMapping("/me/google/link/start")
  public ResponseEntity<OAuthStartResponse> startGoogleLink(
      @CurrentUserId Long userId, HttpServletResponse response) {
    String state = linkHelper.generateStateForLink(userId);
    linkHelper.setStateCookie(response, state);

    String url = linkHelper.buildAuthorizationUrl(state);
    return ResponseEntity.ok(new OAuthStartResponse(url));
  }

  /**
   * ログイン中のユーザーのテーマモードを更新する。
   *
   * @param userId 認証済みユーザーID
   * @param request テーマモード（SYSTEM / LIGHT / DARK）
   * @return 204 No Content
   */
  @PatchMapping("/me/theme")
  public ResponseEntity<Void> updateTheme(
      @CurrentUserId Long userId, @Valid @RequestBody UpdateThemeRequest request) {
    ThemeMode themeMode = ThemeMode.fromCode(request.themeMode());
    userService.updateThemeMode(userId, themeMode);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/me/notification-settings")
  public NotificationSettingsResponse getNotificationSettings(@CurrentUserId Long userId) {
    NotificationSettingsResult result = userService.getSettings(userId);
    return toNotificationSettingsResponse(result);
  }

  @PutMapping("/me/notification-settings")
  public NotificationSettingsResponse updateNotificationSettings(
      @RequestBody UpdateNotificationSettingsRequest req, @CurrentUserId Long userId) {
    // Mapの詰め替え(Request -> Service)
    Map<NotificationGroup, Boolean> groupSettings = new LinkedHashMap<>();
    if (req.groupSettings() != null) {
      for (var e : req.groupSettings().entrySet()) {
        if (e.getKey() == null || e.getValue() == null) continue;
        groupSettings.put(NotificationGroup.fromCode(e.getKey()), e.getValue());
      }
    }

    NotificationSettingsResult result =
        userService.updateNotificationEnabled(userId, req.notificationEnabled(), groupSettings);

    return toNotificationSettingsResponse(result);
  }

  /**
   * NotificationSettingsResultをNotificationSettingsResponseに変換する。
   *
   * @param result NotificationSettingsResult
   * @return NotificationSettingsResponse
   */
  private NotificationSettingsResponse toNotificationSettingsResponse(
      NotificationSettingsResult result) {

    Map<String, Boolean> responseGroupSettings = new LinkedHashMap<>();
    for (var e : result.groupSettings().entrySet()) {
      responseGroupSettings.put(e.getKey().getCode(), e.getValue());
    }

    return new NotificationSettingsResponse(result.notificationEnabled(), responseGroupSettings);
  }
}
