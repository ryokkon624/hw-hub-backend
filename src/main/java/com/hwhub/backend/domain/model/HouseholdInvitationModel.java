package com.hwhub.backend.domain.model;

import com.hwhub.backend.domain.enums.InvitationStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class HouseholdInvitationModel {
  private String invitationToken;
  private Long householdId;
  private String householdName;
  private Long inviterUserId;
  private String inviterName;
  private String invitedEmail;
  private String status;
  private LocalDateTime expiresAt;
  private Long acceptedUserId;
  private String acceptedUserName;
  private LocalDateTime createdAt;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param invitationToken 招待トークン
   * @param householdId 世帯ID
   * @param householdName 世帯名
   * @param inviterUserId 招待者ユーザID
   * @param inviterName 招待者名
   * @param invitedEmail 招待されたユーザのメールアドレス
   * @param status 世帯メンバーステータス
   * @param expiresAt 招待の有効期限
   * @param acceptedUserId 招待を受けたユーザのユーザID
   * @param acceptedUserName 招待を受けたユーザのユーザID
   * @param createdAt 招待の作成日時
   */
  private HouseholdInvitationModel(
      String invitationToken,
      Long householdId,
      String householdName,
      Long inviterUserId,
      String inviterName,
      String invitedEmail,
      String status,
      LocalDateTime expiresAt,
      Long acceptedUserId,
      String acceptedUserName,
      LocalDateTime createdAt) {
    this.invitationToken = invitationToken;
    this.householdId = householdId;
    this.householdName = householdName;
    this.inviterUserId = inviterUserId;
    this.inviterName = inviterName;
    this.invitedEmail = invitedEmail;
    this.status = status;
    this.expiresAt = expiresAt;
    this.acceptedUserId = acceptedUserId;
    this.acceptedUserName = acceptedUserName;
    this.createdAt = createdAt;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param invitationToken 招待トークン
   * @param householdId 世帯ID
   * @param householdName 世帯名
   * @param inviterUserId 招待者ユーザID
   * @param inviterName 招待者名
   * @param invitedEmail 招待されたユーザのメールアドレス
   * @param status 世帯メンバーステータス
   * @param expiresAt 招待の有効期限
   * @param acceptedUserId 招待を受けたユーザのユーザID
   * @param acceptedUserName 招待を受けたユーザのユーザID
   * @param createdAt 招待の作成日時
   * @return インスタンスを返す。
   */
  public static HouseholdInvitationModel reconstruct(
      String invitationToken,
      Long householdId,
      String householdName,
      Long inviterUserId,
      String inviterName,
      String invitedEmail,
      String status,
      LocalDateTime expiresAt,
      Long acceptedUserId,
      String acceptedUserName,
      LocalDateTime createdAt) {
    return new HouseholdInvitationModel(
        invitationToken,
        householdId,
        householdName,
        inviterUserId,
        inviterName,
        invitedEmail,
        status,
        expiresAt,
        acceptedUserId,
        acceptedUserName,
        createdAt);
  }

  public static HouseholdInvitationModel create(
      Long householdId, Long inviterUserId, String invitedEmail) {
    return new HouseholdInvitationModel(
        UUID.randomUUID().toString(),
        householdId,
        null,
        inviterUserId,
        null,
        invitedEmail,
        InvitationStatus.PENDING.getCode(),
        LocalDateTime.now().plusDays(7),
        null,
        null,
        null);
  }

  public boolean isExpired() {
    return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
  }

  public boolean isTerminal() {
    InvitationStatus e = InvitationStatus.fromCode(this.status);
    return e == InvitationStatus.ACCEPTED
        || e == InvitationStatus.DECLINED
        || e == InvitationStatus.REVOKED;
  }
}
