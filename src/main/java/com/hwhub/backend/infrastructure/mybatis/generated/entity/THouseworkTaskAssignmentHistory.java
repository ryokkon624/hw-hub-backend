package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class THouseworkTaskAssignmentHistory {
  private Long houseworkTaskAssignmentHistoryId;

  private Long houseworkTaskId;

  private Long householdId;

  private Long fromAssigneeUserId;

  private Long toAssigneeUserId;

  private Long operatedUserId;

  private String assignReasonType;

  private String note;

  private Date changedAt;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

  public Long getHouseworkTaskAssignmentHistoryId() {
    return houseworkTaskAssignmentHistoryId;
  }

  public void setHouseworkTaskAssignmentHistoryId(Long houseworkTaskAssignmentHistoryId) {
    this.houseworkTaskAssignmentHistoryId = houseworkTaskAssignmentHistoryId;
  }

  public Long getHouseworkTaskId() {
    return houseworkTaskId;
  }

  public void setHouseworkTaskId(Long houseworkTaskId) {
    this.houseworkTaskId = houseworkTaskId;
  }

  public Long getHouseholdId() {
    return householdId;
  }

  public void setHouseholdId(Long householdId) {
    this.householdId = householdId;
  }

  public Long getFromAssigneeUserId() {
    return fromAssigneeUserId;
  }

  public void setFromAssigneeUserId(Long fromAssigneeUserId) {
    this.fromAssigneeUserId = fromAssigneeUserId;
  }

  public Long getToAssigneeUserId() {
    return toAssigneeUserId;
  }

  public void setToAssigneeUserId(Long toAssigneeUserId) {
    this.toAssigneeUserId = toAssigneeUserId;
  }

  public Long getOperatedUserId() {
    return operatedUserId;
  }

  public void setOperatedUserId(Long operatedUserId) {
    this.operatedUserId = operatedUserId;
  }

  public String getAssignReasonType() {
    return assignReasonType;
  }

  public void setAssignReasonType(String assignReasonType) {
    this.assignReasonType = assignReasonType == null ? null : assignReasonType.trim();
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note == null ? null : note.trim();
  }

  public Date getChangedAt() {
    return changedAt;
  }

  public void setChangedAt(Date changedAt) {
    this.changedAt = changedAt;
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
