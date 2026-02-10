package com.hwhub.backend.presentation.rest.user;

import com.hwhub.backend.application.service.UserIconService;
import com.hwhub.backend.application.service.UserService;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.presentation.rest.auth.GoogleOAuthLinkHelper;
import com.hwhub.backend.presentation.rest.user.dto.ChangePasswordRequest;
import com.hwhub.backend.presentation.rest.user.dto.CreateIconUploadUrlRequest;
import com.hwhub.backend.presentation.rest.user.dto.CreateIconUploadUrlResponse;
import com.hwhub.backend.presentation.rest.user.dto.OAuthStartResponse;
import com.hwhub.backend.presentation.rest.user.dto.UpdateIconRequest;
import com.hwhub.backend.presentation.rest.user.dto.UpdateUserProfileRequest;
import com.hwhub.backend.presentation.rest.user.dto.UserHouseholdDto;
import com.hwhub.backend.presentation.rest.user.dto.UserProfileResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
   * <p>ユーザーが未認証の場合、例外をスローします。
   *
   * @param authentication Spring Securityによる認証情報
   * @return ユーザーが所属する世帯情報（IDと名称）のリスト
   * @throws ResponseStatusException 認証情報がない場合 (HttpStatus.UNAUTHORIZED)
   */
  @GetMapping("/me/households")
  public List<UserHouseholdDto> getUserHouseholds(Authentication authentication) {
    if (authentication == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthenticated");
    }
    Long userId = Long.parseLong(authentication.getName());
    return userService.getHouseholds(userId).stream().map(UserHouseholdDto::fromModel).toList();
  }

  /**
   * 認証ユーザーの現在のプロフィール情報（表示名、言語設定など）を取得します。
   *
   * @param authentication Spring Securityによる認証情報
   * @return 認証ユーザーのプロフィール情報を含むレスポンスオブジェクト
   */
  @GetMapping("/me/profile")
  public UserProfileResponse getProfile(Authentication authentication) {
    Long userId = Long.parseLong(authentication.getName());
    UserModel user = userService.getProfile(userId);
    return UserProfileResponse.fromModel(user);
  }

  /**
   * 認証ユーザーのプロフィール情報（表示名、言語設定など）を更新します。
   *
   * @param authentication Spring Securityによる認証情報
   * @param request 更新するプロフィール情報（displayName, locale）
   * @return 更新後のプロフィール情報を含むレスポンスオブジェクト
   */
  @PutMapping("/me/profile")
  public UserProfileResponse updateProfile(
      Authentication authentication, @Valid @RequestBody UpdateUserProfileRequest request) {
    Long userId = Long.parseLong(authentication.getName());
    UserModel updated = userService.updateProfile(userId, request.displayName(), request.locale());
    return UserProfileResponse.fromModel(updated);
  }

  @PostMapping("/me/icon/upload-url")
  public CreateIconUploadUrlResponse createIconUploadUrl(
      @Valid @RequestBody CreateIconUploadUrlRequest request, Authentication authentication) {
    Long userId = Long.parseLong(authentication.getName());
    var result = userIconService.createUploadUrl(userId, request.fileName(), request.mimeType());
    return new CreateIconUploadUrlResponse(result.uploadUrl(), result.fileKey());
  }

  @PostMapping("/me/icon")
  public void updateIcon(
      @Valid @RequestBody UpdateIconRequest request, Authentication authentication) {
    Long userId = Long.parseLong(authentication.getName());
    userIconService.updateUserIcon(userId, request.fileKey());
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAccount(Authentication authentication) {
    Long userId = Long.parseLong(authentication.getName());
    userService.deleteAccount(userId);
  }

  /**
   * ログイン中のパスワード変更
   *
   * @param request 入力値
   * @return 204 No Content（成功時は返却なし）
   */
  @PutMapping("/me/password")
  public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
    userService.changePassword(request.currentPassword(), request.newPassword());
    return ResponseEntity.noContent().build();
  }

  /** Googleアカウント連携開始（ログイン中ユーザーのみ） GET /api/users/me/google/link/start */
  @GetMapping("/me/google/link/start")
  public ResponseEntity<OAuthStartResponse> startGoogleLink(
      Authentication authentication, HttpServletResponse response) {
    Long userId = requireUserId(authentication);

    String state = linkHelper.generateStateForLink(userId);
    linkHelper.setStateCookie(response, state);

    String url = linkHelper.buildAuthorizationUrl(state);
    return ResponseEntity.ok(new OAuthStartResponse(url));
  }

  private Long requireUserId(Authentication authentication) {
    if (authentication == null || authentication.getName() == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthenticated");
    }
    return Long.parseLong(authentication.getName());
  }
}
