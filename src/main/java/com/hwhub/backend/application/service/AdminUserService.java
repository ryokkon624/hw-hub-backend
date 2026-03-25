package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.Permission;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.AdminUserSearchCondition;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedException;
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException;
import com.hwhub.backend.security.RequiresPermission;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserIconService userIconService;

  /**
   * 管理者用ユーザー検索
   *
   * @param condition 検索条件
   * @return ユーザーリスト
   */
  @RequiresPermission(Permission.USER_LIST_VIEW)
  @Transactional(readOnly = true)
  public List<UserModel> searchUsers(AdminUserSearchCondition condition) {
    List<UserModel> users = userRepository.searchByCondition(condition);
    users.forEach(u -> u.setIconUrl(userIconService.getIconUrl(u.getProfileImageKey())));
    return users;
  }

  /**
   * 管理者専用ユーザー登録。 メール認証スキップ・即時 active・email_verified_at を NOW でセット。
   *
   * @param email メールアドレス
   * @param password パスワード
   * @param displayName 表示名
   * @param locale ロケール
   * @param operatorUserId 操作者ID
   * @return 作成されたユーザー
   * @throws EmailAlreadyUsedException メールアドレスが既に使用されている場合
   */
  @RequiresPermission(Permission.USER_LIST_VIEW)
  @Transactional
  public UserModel createUser(
      String email, String password, String displayName, String locale, Long operatorUserId) {

    userRepository
        .findByEmail(email)
        .ifPresent(
            existing -> {
              throw new EmailAlreadyUsedException(email);
            });

    UserModel model = UserModel.create(email, password, displayName, locale);
    String hash = passwordEncoder.encode(password);
    model.setPasswordHash(hash);

    UserModel created =
        userRepository.insertByAdmin(model, operatorUserId, ProgramType.ONL_ADM_USR.getCode());

    userRepository.markEmailVerified(
        created.getUserId(),
        LocalDateTime.now(),
        operatorUserId,
        ProgramType.ONL_ADM_USR.getCode());

    return created;
  }

  /**
   * 管理者によるユーザー情報更新。 password は null/空白の場合は変更しない。
   *
   * @param targetUserId 対象ユーザーID
   * @param displayName 表示名
   * @param locale ロケール
   * @param password パスワード
   * @param isActive アクティブ状態
   * @param operatorUserId 操作者ID
   * @return 更新されたユーザー
   * @throws ResourceNotFoundException ユーザーが見つからない場合
   */
  @RequiresPermission(Permission.USER_LIST_VIEW)
  @Transactional
  public UserModel updateUser(
      Long targetUserId,
      String displayName,
      String locale,
      String password,
      Boolean isActive,
      Long operatorUserId) {

    UserModel user =
        userRepository
            .findById(targetUserId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + targetUserId));

    user.changeProfile(displayName, locale);

    if (isActive != null) {
      if (isActive) {
        user.activate();
      } else {
        user.deactivate();
      }
    }

    if (password != null && !password.isBlank()) {
      String hash = passwordEncoder.encode(password);
      user.setPasswordHash(hash);
    }

    userRepository.updateByAdmin(user, operatorUserId, ProgramType.ONL_ADM_USR.getCode());
    return user;
  }
}
