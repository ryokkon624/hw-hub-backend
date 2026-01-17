package com.hwhub.backend.domain.model;

import com.hwhub.backend.tool.Sha256Hasher;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserEmailVerificationModel {
  private static final int REQUEST_COUNT_INITIAL = 1;

  private Long userEmailVerificationId;
  private Long userId;
  private byte[] tokenHash;
  private LocalDateTime expiresAt;
  private LocalDateTime usedAt;
  private LocalDateTime requestedAt;
  private int requestCount;

  /**
   * 全プロパティを引数に取るコンストラクタ
   *
   * @param userEmailVerificationId ID
   * @param userId ユーザID
   * @param expiresAt 有効期限
   * @param usedAt 使用日時
   * @param requestedAt 請求日時
   * @param requestCount 請求回数
   */
  private UserEmailVerificationModel(
      Long userEmailVerificationId,
      Long userId,
      byte[] tokenHash,
      LocalDateTime expiresAt,
      LocalDateTime usedAt,
      LocalDateTime requestedAt,
      int requestCount) {
    this.userEmailVerificationId = userEmailVerificationId;
    this.userId = userId;
    this.tokenHash = tokenHash;
    this.expiresAt = expiresAt;
    this.usedAt = usedAt;
    this.requestedAt = requestedAt;
    this.requestCount = requestCount;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param userEmailVerificationId ID
   * @param userId ユーザID
   * @param expiresAt 有効期限
   * @param usedAt 使用日時
   * @param requestedAt 請求日時
   * @param requestCount 請求回数
   * @return
   */
  public static UserEmailVerificationModel reconstruct(
      Long userEmailVerificationId,
      Long userId,
      byte[] tokenHash,
      LocalDateTime expiresAt,
      LocalDateTime usedAt,
      LocalDateTime requestedAt,
      int requestCount) {
    return new UserEmailVerificationModel(
        userEmailVerificationId, userId, tokenHash, expiresAt, usedAt, requestedAt, requestCount);
  }

  /**
   * ユーザメール認証を生成する。
   *
   * @param userId ユーザID
   * @param tokenTtlMinutes トークンのTTL
   * @return
   */
  public static UserEmailVerificationModel create(
      Long userId, String token, LocalDateTime requestedAt, int tokenTtlMinutes) {

    LocalDateTime expiresAt = requestedAt.plusMinutes(tokenTtlMinutes);
    return new UserEmailVerificationModel(
        null, userId, hashToken(token), expiresAt, null, requestedAt, REQUEST_COUNT_INITIAL);
  }

  public static byte[] hashToken(String token) {
    return Sha256Hasher.sha256(token);
  }
}
