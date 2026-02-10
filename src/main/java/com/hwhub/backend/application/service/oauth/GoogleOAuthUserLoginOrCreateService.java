package com.hwhub.backend.application.service.oauth;

import com.hwhub.backend.domain.enums.AuthProvider;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.oauth.google.GoogleUserInfo;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedForLocalAccountException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Google OAuth ユーザーに対して、 ログインまたは新規ユーザー作成を行い、JWT を発行するサービス。
 *
 * <p>責務:
 *
 * <ul>
 *   <li>GoogleOAuthUserResolver の判定結果に基づき
 *   <li>既存ユーザーならログイン
 *   <li>新規ユーザーなら m_user を作成
 *   <li>最終的に JwtProvider を使って JWT を発行する
 * </ul>
 *
 * <p>このクラスは:
 *
 * <ul>
 *   <li>OAuth の詳細（code / state / token）を知らない
 *   <li>純粋に「ユーザー認証」という観点に集中する
 * </ul>
 *
 * <p>位置づけ:
 *
 * <ul>
 *   <li>認証ドメイン寄りの Application Service
 *   <li>email/password ログインの Service と思想的に同列
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class GoogleOAuthUserLoginOrCreateService {

  private final UserRepository userRepository;

  private static final long USER_ID_ADMIN = 1L;

  @Transactional
  public UserModel loginOrCreate(GoogleUserInfo info) {
    String sub = info.getSub();
    String email = info.getEmail();
    String name = info.getName();

    // 既にGOOGLE紐付け済みならそれを返す
    var existing =
        userRepository.findByAuthProviderAndAuthProviderId(AuthProvider.GOOGLE.getCode(), sub);
    if (existing.isPresent()) {
      return existing.get();
    }

    // まだ紐付いてない場合
    // emailが既に使われているなら、「ログインしてからgoogleアカウントと連携させる
    if (email != null && userRepository.countByEmail(email) > 0) {
      throw new EmailAlreadyUsedForLocalAccountException("Email already used.");
    }

    // 新規作成（password_hashはNULL）
    LocalDateTime now = LocalDateTime.now();
    String displayName = (name == null || name.isBlank()) ? "User" : name;

    UserModel model = UserModel.createGoogleUser(email, sub, displayName, now);

    return userRepository.insert(model, USER_ID_ADMIN, ProgramType.ONL_AUTH_GOOGLE.getCode());
  }
}
