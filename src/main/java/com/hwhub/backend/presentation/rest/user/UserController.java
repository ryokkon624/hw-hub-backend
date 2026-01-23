package com.hwhub.backend.presentation.rest.user;

import com.hwhub.backend.application.service.UserIconService;
import com.hwhub.backend.application.service.UserService;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.presentation.rest.user.dto.*;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
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
}
