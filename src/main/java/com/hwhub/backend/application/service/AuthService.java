package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.auth.dto.LoginRequest;
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedException;
import com.hwhub.backend.security.JwtProvider;
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

    // 画像表示用のURLを生成し設定
    user.setIconUrl(userIconService.getIconUrl(user.getProfileImageKey()));

    String token = jwtProvider.generateToken(user.getUserId(), user.getDisplayName());
    return new LoginInfo(token, user);
  }

  public LoginInfo register(UserModel model) {

    // emailの重複チェック
    long exits = userRepository.countByEmail(model.getEmail());
    if (exits > 0) {
      throw new EmailAlreadyUsedException(model.getEmail());
    }

    // パスワードをハッシュ化
    String hash = passwordEncoder.encode(model.getPassword());
    model.setPasswordHash(hash);

    UserModel inserted =
        userRepository.insert(model, USER_ID_ADMIN, ProgramType.ONL_AUTH.getCode());

    // 画像表示用のURLを生成し設定
    inserted.setIconUrl(userIconService.getIconUrl(inserted.getProfileImageKey()));

    String token = jwtProvider.generateToken(inserted.getUserId(), inserted.getDisplayName());
    return new LoginInfo(token, inserted);
  }

  public record LoginInfo(String token, UserModel user) {}
}
