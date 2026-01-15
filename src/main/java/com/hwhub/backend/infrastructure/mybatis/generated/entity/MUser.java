package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class MUser {
  private Long userId;

  private String email;

  private String passwordHash;

  private String authProvider;

  private String authProviderId;

  private String displayName;

  private String profileImageKey;

  private String locale;

  private Date emailVerifiedAt;

  private Boolean isActive;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email == null ? null : email.trim();
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash == null ? null : passwordHash.trim();
  }

  public String getAuthProvider() {
    return authProvider;
  }

  public void setAuthProvider(String authProvider) {
    this.authProvider = authProvider == null ? null : authProvider.trim();
  }

  public String getAuthProviderId() {
    return authProviderId;
  }

  public void setAuthProviderId(String authProviderId) {
    this.authProviderId = authProviderId == null ? null : authProviderId.trim();
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName == null ? null : displayName.trim();
  }

  public String getProfileImageKey() {
    return profileImageKey;
  }

  public void setProfileImageKey(String profileImageKey) {
    this.profileImageKey = profileImageKey == null ? null : profileImageKey.trim();
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale == null ? null : locale.trim();
  }

  public Date getEmailVerifiedAt() {
    return emailVerifiedAt;
  }

  public void setEmailVerifiedAt(Date emailVerifiedAt) {
    this.emailVerifiedAt = emailVerifiedAt;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public Long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public String getCreateProgram() {
    return createProgram;
  }

  public void setCreateProgram(String createProgram) {
    this.createProgram = createProgram == null ? null : createProgram.trim();
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Long getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(Long updateUserId) {
    this.updateUserId = updateUserId;
  }

  public String getUpdateProgram() {
    return updateProgram;
  }

  public void setUpdateProgram(String updateProgram) {
    this.updateProgram = updateProgram == null ? null : updateProgram.trim();
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
