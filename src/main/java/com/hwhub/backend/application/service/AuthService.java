package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.auth.dto.LoginRequest;
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedException;
import com.hwhub.backend.security.JwtProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final UserIconService userIconService;

  private static final long USER_ID_ADMIN = 1;

  public LoginInfo login(LoginRequest request) {
    UserModel user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
      throw new BadCredentialsException("Invalid password");
    }

    if (!user.isActive()) {
      throw new BadCredentialsException("Account is deactivated");
    }

    // 画像表示用のURLを生成し設定
    user.setIconUrl(userIconService.getIconUrl(user.getProfileImageKey()));

    String token = jwtProvider.generateToken(user.getUserId(), user.getDisplayName());
    return new LoginInfo(token, user);
  }

  public LoginInfo register(UserModel model) {

    // emailの重複チェック
    // 既に存在しかつactiveな場合はエラー、非アクティブな場合は再有効化して更新
    Optional<UserModel> existing = userRepository.findByEmail(model.getEmail());

    UserModel targetUser;

    if (existing.isPresent()) {
      UserModel found = existing.get();
      if (found.isActive()) {
        throw new EmailAlreadyUsedException(model.getEmail());
      }
      // 再有効化
      // パスワードをハッシュ化
      String hash = passwordEncoder.encode(model.getPassword());
      found.setPasswordHash(hash);
      found.changeProfile(model.getDisplayName(), model.getLocale());
      found.activate();

      userRepository.updateForReactivation(
          found, found.getUserId(), ProgramType.ONL_AUTH.getCode());
      targetUser = found;

    } else {
      // 新規登録
      // パスワードをハッシュ化
      String hash = passwordEncoder.encode(model.getPassword());
      model.setPasswordHash(hash);

      targetUser = userRepository.insert(model, USER_ID_ADMIN, ProgramType.ONL_AUTH.getCode());
    }

    // 画像表示用のURLを生成し設定
    targetUser.setIconUrl(userIconService.getIconUrl(targetUser.getProfileImageKey()));

    String token = jwtProvider.generateToken(targetUser.getUserId(), targetUser.getDisplayName());
    return new LoginInfo(token, targetUser);
  }

  public record LoginInfo(String token, UserModel user) {}
}
