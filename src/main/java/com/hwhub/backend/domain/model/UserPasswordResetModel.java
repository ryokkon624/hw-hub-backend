package com.hwhub.backend.domain.model;

import com.hwhub.backend.tool.Sha256Hasher;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserPasswordResetModel {

  private static final int REQUEST_COUNT_INITIAL = 1;

  private Long userPasswordResetId;
  private Long userId;
  private byte[] tokenHash;
  private LocalDateTime expiresAt;
  private LocalDateTime usedAt;
  private LocalDateTime requestedAt;
  private int requestCount;

  /**
   * 全プロパティを引数に取るコンストラクタ
   *
   * @param userPasswordResetId ID
   * @param userId ユーザID
   * @param tokenHash トークンハッシュ
   * @param expiresAt 有効期限
   * @param usedAt 使用済み日時
   * @param requestedAt 要求日時
   * @param requestCount 要求回数
   */
  private UserPasswordResetModel(
      Long userPasswordResetId,
      Long userId,
      byte[] tokenHash,
      LocalDateTime expiresAt,
      LocalDateTime usedAt,
      LocalDateTime requestedAt,
      int requestCount) {
    this.userPasswordResetId = userPasswordResetId;
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
   * @param userPasswordResetId ID
   * @param userId ユーザID
   * @param tokenHash トークンハッシュ
   * @param expiresAt 有効期限
   * @param usedAt 使用済み日時
   * @param requestedAt 要求日時
   * @param requestCount 要求回数
   * @return インスタンスを返す。
   */
  public static UserPasswordResetModel reconstruct(
      Long userPasswordResetId,
      Long userId,
      byte[] tokenHash,
      LocalDateTime expiresAt,
      LocalDateTime usedAt,
      LocalDateTime requestedAt,
      int requestCount) {
    return new UserPasswordResetModel(
        userPasswordResetId, userId, tokenHash, expiresAt, usedAt, requestedAt, requestCount);
  }

  /**
   * 新規追加時のファクトリメソッド。
   *
   * @param userId ユーザID
   * @param token トークン
   * @param requestedAt 要求日時
   * @param tokenTtlMinutes トークンの有効期間（分）
   * @return インスタンスを返す。
   */
  public static UserPasswordResetModel create(
      Long userId, String token, LocalDateTime requestedAt, int tokenTtlMinutes) {

    LocalDateTime expiresAt = requestedAt.plusMinutes(tokenTtlMinutes);
    return new UserPasswordResetModel(
        null, userId, hashToken(token), expiresAt, null, requestedAt, REQUEST_COUNT_INITIAL);
  }

  public static byte[] hashToken(String token) {
    return Sha256Hasher.sha256(token);
  }
}
