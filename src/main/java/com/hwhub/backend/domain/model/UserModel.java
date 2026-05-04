package com.hwhub.backend.domain.model;

import com.hwhub.backend.domain.enums.AuthProvider;
import com.hwhub.backend.domain.enums.ThemeMode;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserModel {
  private Long userId;
  private String email;
  private String password;
  private String passwordHash;
  private LocalDateTime passwordChangedAt;
  private String authProvider;
  private String authProviderId;
  private String displayName;
  private String locale;
  private ThemeMode themeMode;
  private boolean notificationEnabled;
  private String profileImageKey;
  private String iconUrl;
  private LocalDateTime emailVerifiedAt;
  private boolean isActive;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param userId ユーザID
   * @param email メールアドレス
   * @param password パスワード
   * @param passwordHash パスワードハッシュ
   * @param passwordChangedAt パスワード最終変更日時
   * @param authProvider 認証提供者
   * @param authProviderId 認証提供者ID
   * @param displayName 表示名
   * @param locale 利用言語
   * @param themeMode テーマモード
   * @param notificationEnabled 通知有効フラグ
   * @param profileImageKey プロフィール画像ストレージキー
   * @param iconUrl アイコンのURL
   * @param emailVerifiedAt 認証完了日時
   * @param isActive 活性フラグ
   * @param createdAt 作成日時
   * @param updatedAt 更新日時
   */
  private UserModel(
      Long userId,
      String email,
      String password,
      String passwordHash,
      LocalDateTime passwordChangedAt,
      String authProvider,
      String authProviderId,
      String displayName,
      String locale,
      ThemeMode themeMode,
      boolean notificationEnabled,
      String profileImageKey,
      String iconUrl,
      LocalDateTime emailVerifiedAt,
      boolean isActive,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.userId = userId;
    this.email = email;
    this.password = password;
    this.passwordHash = passwordHash;
    this.passwordChangedAt = passwordChangedAt;
    this.authProvider = authProvider;
    this.authProviderId = authProviderId;
    this.displayName = displayName;
    this.locale = locale;
    this.themeMode = themeMode;
    this.notificationEnabled = notificationEnabled;
    this.profileImageKey = profileImageKey;
    this.iconUrl = iconUrl;
    this.emailVerifiedAt = emailVerifiedAt;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param userId ユーザID
   * @param email メールアドレス
   * @param passwordHash パスワードハッシュ
   * @param passwordChangedAt パスワード最終変更日時
   * @param authProvider 認証提供者
   * @param authProviderId 認証提供者ID
   * @param displayName 表示名
   * @param locale 利用言語
   * @param themeMode テーマモード
   * @param notificationEnabled 通知有効フラグ
   * @param profileImageKey プロフィール画像ストレージキー
   * @param emailVerifiedAt 認証完了日時
   * @param isActive 活性フラグ
   * @param createdAt 作成日時
   * @param updatedAt 更新日時
   * @return インスタンスを返す。
   */
  public static UserModel reconstruct(
      Long userId,
      String email,
      String passwordHash,
      LocalDateTime passwordChangedAt,
      String authProvider,
      String authProviderId,
      String displayName,
      String locale,
      ThemeMode themeMode,
      boolean notificationEnabled,
      String profileImageKey,
      LocalDateTime emailVerifiedAt,
      boolean isActive,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    return new UserModel(
        userId,
        email,
        null,
        passwordHash,
        passwordChangedAt,
        authProvider,
        authProviderId,
        displayName,
        locale,
        themeMode,
        notificationEnabled,
        profileImageKey,
        null,
        emailVerifiedAt,
        isActive,
        createdAt,
        updatedAt);
  }

  /**
   * 新規追加時のファクトリメソッド。
   *
   * @param email メールアドレス
   * @param password パスワード
   * @param displayName 表示名
   * @param locale 利用言語
   * @return ユーザIDがnullのインスタンスを返す。
   */
  public static UserModel create(String email, String password, String displayName, String locale) {
    return new UserModel(
        null,
        email,
        password,
        null,
        null,
        AuthProvider.LOCAL.getCode(),
        null,
        displayName,
        locale,
        ThemeMode.SYSTEM,
        true,
        null,
        null,
        null,
        true,
        null,
        null);
  }

  /**
   * Google認証による新規追加時のファクトリメソッド。
   *
   * @param email メールアドレス
   * @param googleSub Googleサブ
   * @param displayName 表示名
   * @param now 現在日時
   * @return ユーザIDがnullのインスタンスを返す。
   */
  public static UserModel createGoogleUser(
      String email, String googleSub, String displayName, LocalDateTime now) {
    return new UserModel(
        null,
        email,
        null,
        null,
        null,
        AuthProvider.GOOGLE.getCode(),
        googleSub,
        displayName,
        "ja",
        ThemeMode.SYSTEM,
        true,
        null,
        null,
        now,
        true,
        null,
        null);
  }

  /**
   * パスワードハッシュを設定する。Infrastructure層でハッシュ化して設定すること。
   *
   * @param passwordHash パスワードハッシュ
   */
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public void changePasswordHash(String passwordHash, LocalDateTime changedAt) {
    this.passwordHash = passwordHash;
    this.passwordChangedAt = changedAt;
  }

  /**
   * アイコンのURLを設定する。Infrastructure層で生成後に設定すること。
   *
   * @param iconUrl アイコンのURL
   */
  public void setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }

  /**
   * ユーザに変更を許可している情報を更新する。
   *
   * @param displayName 表示名
   * @param locale 利用言語
   */
  public void changeProfile(String displayName, String locale) {
    this.displayName = displayName;
    this.locale = locale;
  }

  /**
   * テーマモードを変更する。
   *
   * @param themeMode テーマモード
   */
  public void changeThemeMode(ThemeMode themeMode) {
    this.themeMode = themeMode;
  }

  /**
   * プロフィール画像ストレージキーを変更する。
   *
   * @param profileImageKey プロフィール画像ストレージキー
   */
  public void changeProfileImageKey(String profileImageKey) {
    this.profileImageKey = profileImageKey;
  }

  /** ユーザを活性化する。 */
  public void activate() {
    this.isActive = true;
  }

  /** ユーザを非活性化する。 */
  public void deactivate() {
    this.isActive = false;
  }
}
