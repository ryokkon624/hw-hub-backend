package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class THouseholdInvitation {
  private String invitationToken;

  private Long householdId;

  private Long inviterUserId;

  private String invitedEmail;

  private String status;

  private Date expiresAt;

  private Long acceptedUserId;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

  public String getInvitationToken() {
    return invitationToken;
  }

  public void setInvitationToken(String invitationToken) {
    this.invitationToken = invitationToken == null ? null : invitationToken.trim();
  }

  public Long getHouseholdId() {
    return householdId;
  }

  public void setHouseholdId(Long householdId) {
    this.householdId = householdId;
  }

  public Long getInviterUserId() {
    return inviterUserId;
  }

  public void setInviterUserId(Long inviterUserId) {
    this.inviterUserId = inviterUserId;
  }

  public String getInvitedEmail() {
    return invitedEmail;
  }

  public void setInvitedEmail(String invitedEmail) {
    this.invitedEmail = invitedEmail == null ? null : invitedEmail.trim();
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status == null ? null : status.trim();
  }

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  public Long getAcceptedUserId() {
    return acceptedUserId;
  }

  public void setAcceptedUserId(Long acceptedUserId) {
    this.acceptedUserId = acceptedUserId;
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
