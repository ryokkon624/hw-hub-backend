package com.hwhub.backend.application.service;

import com.hwhub.backend.config.EmailVerificationProperties;
import com.hwhub.backend.domain.enums.AuthProvider;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.UserEmailVerificationModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.notification.VerificationMailSender;
import com.hwhub.backend.domain.repository.UserEmailVerificationRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.auth.dto.LoginRequest;
import com.hwhub.backend.presentation.rest.common.EmailAlreadyUsedException;
import com.hwhub.backend.presentation.rest.common.EmailAlreadyVerifiedException;
import com.hwhub.backend.presentation.rest.common.EmailNotVerifiedException;
import com.hwhub.backend.presentation.rest.common.EmailVerificationCooldownException;
import com.hwhub.backend.presentation.rest.common.EmailVerificationTokenInvalidException;
import com.hwhub.backend.presentation.rest.common.EmailVerificationTooManyRequestsException;
import com.hwhub.backend.presentation.rest.common.PasswordLoginNotAllowedException;
import com.hwhub.backend.security.JwtProvider;
import com.hwhub.backend.tool.VerificationTokenGenerator;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final UserIconService userIconService;
  private final EmailVerificationProperties emailVerificationProperties;
  private final UserEmailVerificationRepository userEmailVerificationRepository;
  private final VerificationMailSender verificationMailSender;

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

    // Google連携済みなら「パスワードログイン禁止」
    if (AuthProvider.GOOGLE.getCode().equals(user.getAuthProvider())
        || user.getPasswordHash() == null) {
      throw new PasswordLoginNotAllowedException();
    }

    if (emailVerificationProperties.enabled() && user.getEmailVerifiedAt() == null) {
      throw new EmailNotVerifiedException();
    }

    // 画像表示用のURLを生成し設定
    user.setIconUrl(userIconService.getIconUrl(user.getProfileImageKey()));

    String token = jwtProvider.generateToken(user.getUserId(), user.getDisplayName());
    return new LoginInfo(token, user);
  }

  public RegisterInfo register(UserModel model) {

    // emailの重複チェック
    // 既に存在しかつactiveな場合はエラー、非アクティブな場合は再有効化して更新
    Optional<UserModel> existing = userRepository.findByEmail(model.getEmail());

    UserModel targetUser;

    if (existing.isPresent()) {
      UserModel found = existing.get();
      if (!emailVerificationProperties.enabled() && found.isActive()) {
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

    if (!emailVerificationProperties.enabled()) {
      userRepository.markEmailVerified(
          targetUser.getUserId(),
          LocalDateTime.now(),
          USER_ID_ADMIN,
          ProgramType.ONL_AUTH.getCode());

      String token = jwtProvider.generateToken(targetUser.getUserId(), targetUser.getDisplayName());
      return RegisterInfo.loggedIn(token, targetUser);
    }

    // prod: verification 発行
    LocalDateTime expiresAt = issueVerificationFor(targetUser);
    return RegisterInfo.verificationRequired(targetUser, expiresAt);
  }

  @Transactional
  public void verifyEmail(String token) {

    LocalDateTime now = LocalDateTime.now();
    byte[] tokenHash = UserEmailVerificationModel.hashToken(token);

    // token_hash で「未使用＆期限内」を検索
    var model =
        userEmailVerificationRepository
            .findUsableByTokenHash(tokenHash, now)
            .orElseThrow(EmailVerificationTokenInvalidException::new);

    userEmailVerificationRepository.markUsed(
        model.getUserEmailVerificationId(), now, USER_ID_ADMIN, ProgramType.ONL_AUTH.getCode());

    // ユーザを認証済みに更新
    userRepository.markEmailVerified(
        model.getUserId(), now, USER_ID_ADMIN, ProgramType.ONL_AUTH.getCode());
  }

  @Transactional
  public void resendVerification(String email) {

    // 存在しないメールでも同じ応答にしたいので Optional のまま進める
    Optional<UserModel> opt = userRepository.findByEmail(email);
    if (opt.isEmpty()) {
      return; // 何もせず成功扱い
    }

    UserModel user = opt.get();

    // すでに認証済みなら 409
    if (user.getEmailVerifiedAt() != null) {
      throw new EmailAlreadyVerifiedException();
    }

    // resend制御 + token発行 +（send-mail=trueなら）送信
    issueVerificationFor(user);
  }

  private LocalDateTime issueVerificationFor(UserModel user) {

    LocalDateTime now = LocalDateTime.now();

    // resend 制御
    enforceResendPolicy(user.getUserId(), now);

    String token = VerificationTokenGenerator.generateToken();

    UserEmailVerificationModel model =
        UserEmailVerificationModel.create(
            user.getUserId(), token, now, emailVerificationProperties.tokenTtlMinutes());
    userEmailVerificationRepository.insert(model, USER_ID_ADMIN, ProgramType.ONL_AUTH.getCode());

    // メール送信は設定で制御
    if (emailVerificationProperties.sendMail()) {
      String verifyUrl = buildVerifyUrl(token);
      verificationMailSender.sendVerificationMail(
          Objects.requireNonNull(user.getEmail()),
          user.getDisplayName(),
          Objects.requireNonNull(verifyUrl),
          user.getLocale());
    }

    return model.getExpiresAt();
  }

  private void enforceResendPolicy(long userId, LocalDateTime now) {

    userEmailVerificationRepository
        .findLatestRequestedAt(userId)
        .ifPresent(
            latest -> {
              if (latest.isAfter(
                  now.minusSeconds(emailVerificationProperties.resendCooldownSeconds()))) {
                throw new EmailVerificationCooldownException();
              }
            });

    int count = userEmailVerificationRepository.countRequestedSince(userId, now.minusDays(1));
    if (count >= emailVerificationProperties.maxRequestsPerDay()) {
      throw new EmailVerificationTooManyRequestsException();
    }
  }

  private String buildVerifyUrl(String token) {
    return emailVerificationProperties.frontBaseUrl()
        + emailVerificationProperties.verifyPath()
        + "?token="
        + token;
  }

  public record LoginInfo(String token, UserModel user) {}

  public record RegisterInfo(
      boolean emailVerificationRequired,
      String token,
      UserModel user,
      LocalDateTime verificationExpiresAt) {

    public static RegisterInfo loggedIn(String token, UserModel user) {
      return new RegisterInfo(false, token, user, null);
    }

    public static RegisterInfo verificationRequired(UserModel user, LocalDateTime expiresAt) {
      return new RegisterInfo(true, null, user, expiresAt);
    }
  }
}
