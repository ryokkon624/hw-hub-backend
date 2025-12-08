package com.hwhub.backend.domain.model;

import com.hwhub.backend.domain.enums.HouseholdMemberStatus;
import lombok.Getter;

@Getter
public class HouseholdMemberModel {

  private static final String ROLE_MEMBER = "MEMBER";

  private Long householdId;
  private Long userId;
  private String displayName;
  private String profileImageKey;
  private String iconUrl;
  private String nickname;
  private String status;
  private String role;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param householdId 世帯ID
   * @param userId ユーザID
   * @param displayName ユーザの表示名
   * @param profileImageKey ユーザのプロフィール画像キー
   * @param iconUrl プロフィール画像のUTL
   * @param nickname ニックネーム
   * @param status ステータス
   * @param role ロール
   */
  private HouseholdMemberModel(
      Long householdId,
      Long userId,
      String displayName,
      String profileImageKey,
      String iconUrl,
      String nickname,
      String status,
      String role) {
    this.householdId = householdId;
    this.userId = userId;
    this.displayName = displayName;
    this.profileImageKey = profileImageKey;
    this.iconUrl = iconUrl;
    this.nickname = nickname;
    this.status = status;
    this.role = role;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param householdId 世帯ID
   * @param userId ユーザID
   * @param displayName ユーザの表示名
   * @param profileImageKey ユーザのプロフィール画像キー
   * @param iconUrl プロフィール画像のUTL
   * @param nickname ニックネーム
   * @param status ステータス
   * @param role ロール
   * @return インスタンスを返す。
   */
  public static HouseholdMemberModel reconstruct(
      Long householdId,
      Long userId,
      String displayName,
      String profileImageKey,
      String iconUrl,
      String nickname,
      String status,
      String role) {
    return new HouseholdMemberModel(
        householdId, userId, displayName, profileImageKey, iconUrl, nickname, status, role);
  }

  /**
   * 新規追加時のファクトリメソッド。
   *
   * @param householdId 世帯ID
   * @param userId ユーザID
   * @param displayName ユーザの表示名
   * @return インスタンスを返す。
   */
  public static HouseholdMemberModel create(Long householdId, Long userId, String displayName) {
    return new HouseholdMemberModel(
        householdId,
        userId,
        displayName,
        null,
        null,
        displayName,
        HouseholdMemberStatus.ACTIVE.getCode(),
        ROLE_MEMBER);
  }

  /** 世帯に再参加する。 */
  public void rejoin() {
    this.status = HouseholdMemberStatus.ACTIVE.getCode();
  }

  /** 世帯から離脱する。 */
  public void leave() {
    this.status = HouseholdMemberStatus.LEFT.getCode();
  }

  /**
   * アイコンのURLを変更する。Infrastructure層で生成後に設定すること。
   *
   * @param iconUrl アイコンのURL
   */
  public void changeIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }

  /**
   * ニックネームを変更する。
   *
   * @param nickname
   */
  public void changeNickname(String nickname) {
    this.nickname = nickname;
  }
}
