package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class THouseworkTask {
  private Long houseworkTaskId;

  private Long householdId;

  private Long houseworkId;

  private String name;

  private String description;

  private String category;

  private Date targetDate;

  private Long assigneeUserId;

  private String status;

  private String assignReasonType;

  private Date doneAt;

  private String skippedReason;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

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

  public Long getHouseworkId() {
    return houseworkId;
  }

  public void setHouseworkId(Long houseworkId) {
    this.houseworkId = houseworkId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description == null ? null : description.trim();
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category == null ? null : category.trim();
  }

  public Date getTargetDate() {
    return targetDate;
  }

  public void setTargetDate(Date targetDate) {
    this.targetDate = targetDate;
  }

  public Long getAssigneeUserId() {
    return assigneeUserId;
  }

  public void setAssigneeUserId(Long assigneeUserId) {
    this.assigneeUserId = assigneeUserId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status == null ? null : status.trim();
  }

  public String getAssignReasonType() {
    return assignReasonType;
  }

  public void setAssignReasonType(String assignReasonType) {
    this.assignReasonType = assignReasonType == null ? null : assignReasonType.trim();
  }

  public Date getDoneAt() {
    return doneAt;
  }

  public void setDoneAt(Date doneAt) {
    this.doneAt = doneAt;
  }

  public String getSkippedReason() {
    return skippedReason;
  }

  public void setSkippedReason(String skippedReason) {
    this.skippedReason = skippedReason == null ? null : skippedReason.trim();
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
