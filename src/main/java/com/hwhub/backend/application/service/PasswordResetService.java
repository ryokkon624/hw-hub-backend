package com.hwhub.backend.application.service;

import com.hwhub.backend.config.PasswordResetProperties;
import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.model.UserPasswordResetModel;
import com.hwhub.backend.domain.notification.PasswordResetMailSender;
import com.hwhub.backend.domain.repository.UserPasswordResetRepository;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.presentation.rest.common.PasswordResetCooldownException;
import com.hwhub.backend.presentation.rest.common.PasswordResetLimitExceededException;
import com.hwhub.backend.presentation.rest.common.PasswordResetTokenExpiredException;
import com.hwhub.backend.presentation.rest.common.PasswordResetTokenInvalidException;
import com.hwhub.backend.tool.VerificationTokenGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

  private static final long USER_ID_ADMIN = 1;

  private final PasswordResetProperties properties;
  private final UserRepository userRepository;
  private final UserPasswordResetRepository userPasswordResetRepository;
  private final PasswordEncoder passwordEncoder;

  private final PasswordResetMailSender mailerSender;

  /** パスワードリセット要求（常に204で返すことを前提に内部で処理する） */
  @Transactional
  public void requestReset(String email) {
    if (!properties.enabled()) {
      // 無効化中は何もしない
      return;
    }

    Optional<UserModel> userOpt = userRepository.findByEmail(email);
    if (userOpt.isEmpty()) {
      // 情報漏えい防止：存在しないメールでも成功扱い
      return;
    }

    UserModel user = userOpt.get();
    if (!user.isActive()) {
      // 情報漏えい防止：非アクティブでも成功扱い
      return;
    }

    LocalDateTime now = LocalDateTime.now();

    enforceCooldown(user.getUserId(), now);
    enforceDailyLimit(user.getUserId(), now.toLocalDate());

    String token = VerificationTokenGenerator.generateToken();
    UserPasswordResetModel model =
        UserPasswordResetModel.create(user.getUserId(), token, now, properties.tokenTtlMinutes());

    userPasswordResetRepository.insert(model, USER_ID_ADMIN, ProgramType.ONL_AUTH.getCode());

    if (properties.sendMail()) {
      String url = buildResetUrl(token);
      mailerSender.sendPasswordResetMail(
          user.getEmail(), user.getDisplayName(), url, user.getLocale());
    }
  }

  /** パスワードリセット確定 */
  @Transactional
  public void confirmReset(String token, String newPassword) {
    if (!properties.enabled()) {
      throw new PasswordResetTokenInvalidException();
    }

    LocalDateTime now = LocalDateTime.now();
    byte[] tokenHash = UserPasswordResetModel.hashToken(token);

    // token を検索（未使用 & 期限内）
    UserPasswordResetModel reset =
        userPasswordResetRepository
            .findUsableByTokenHash(tokenHash, now)
            .orElseThrow(PasswordResetTokenInvalidException::new);

    if (reset.getExpiresAt().isBefore(now)) {
      throw new PasswordResetTokenExpiredException();
    }

    // パスワード更新
    UserModel user =
        userRepository
            .findById(reset.getUserId())
            .orElseThrow(PasswordResetTokenInvalidException::new);
    String hash = passwordEncoder.encode(newPassword);
    user.changePasswordHash(hash, now);
    userRepository.updatePassword(user, USER_ID_ADMIN, ProgramType.ONL_PWDRST.getCode());

    // token を使用済みに更新
    int updated =
        userPasswordResetRepository.markUsedIfUnused(
            reset.getUserPasswordResetId(), now, USER_ID_ADMIN, ProgramType.ONL_AUTH.getCode());

    // ここで0件なら二重実行/競合、invalid 扱い
    if (updated == 0) {
      throw new PasswordResetTokenInvalidException();
    }
  }

  private void enforceCooldown(Long userId, LocalDateTime now) {
    userPasswordResetRepository
        .findLatestRequestedAt(userId)
        .ifPresent(
            latest -> {
              if (latest.isAfter(now.minusSeconds(properties.resendCooldownSeconds()))) {
                throw new PasswordResetCooldownException();
              }
            });
  }

  private void enforceDailyLimit(Long userId, LocalDate today) {
    LocalDateTime start = today.atStartOfDay();
    LocalDateTime end = today.plusDays(1).atStartOfDay();

    int count = userPasswordResetRepository.countRequestedOnDate(userId, start, end);
    if (count >= properties.maxRequestsPerDay()) {
      throw new PasswordResetLimitExceededException();
    }
  }

  /**
   * パスワードリセット用のURLを作成する。 ex) https://stg.familyapp-hwhub.com/password/reset?token=xxx
   *
   * @param token トークン
   * @return
   */
  private String buildResetUrl(String token) {
    return properties.frontBaseUrl() + properties.resetPath() + "?token=" + token;
  }
}
