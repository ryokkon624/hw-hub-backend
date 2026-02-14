package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class TNotification {
  private Long notificationId;

  private Long householdId;

  private String notificationTypeCode;

  private Long actorUserId;

  private Long targetUserId;

  private Boolean isRead;

  private Date readAt;

  private String titleKey;

  private String bodyKey;

  private String linkTypeCode;

  private Long linkId;

  private Date occurredAt;

  private String aggregatedKey;

  private Integer aggregatedCount;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

  private String paramsJson;

  public Long getNotificationId() {
    return notificationId;
  }

  public void setNotificationId(Long notificationId) {
    this.notificationId = notificationId;
  }

  public Long getHouseholdId() {
    return householdId;
  }

  public void setHouseholdId(Long householdId) {
    this.householdId = householdId;
  }

  public String getNotificationTypeCode() {
    return notificationTypeCode;
  }

  public void setNotificationTypeCode(String notificationTypeCode) {
    this.notificationTypeCode = notificationTypeCode == null ? null : notificationTypeCode.trim();
  }

  public Long getActorUserId() {
    return actorUserId;
  }

  public void setActorUserId(Long actorUserId) {
    this.actorUserId = actorUserId;
  }

  public Long getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(Long targetUserId) {
    this.targetUserId = targetUserId;
  }

  public Boolean getIsRead() {
    return isRead;
  }

  public void setIsRead(Boolean isRead) {
    this.isRead = isRead;
  }

  public Date getReadAt() {
    return readAt;
  }

  public void setReadAt(Date readAt) {
    this.readAt = readAt;
  }

  public String getTitleKey() {
    return titleKey;
  }

  public void setTitleKey(String titleKey) {
    this.titleKey = titleKey == null ? null : titleKey.trim();
  }

  public String getBodyKey() {
    return bodyKey;
  }

  public void setBodyKey(String bodyKey) {
    this.bodyKey = bodyKey == null ? null : bodyKey.trim();
  }

  public String getLinkTypeCode() {
    return linkTypeCode;
  }

  public void setLinkTypeCode(String linkTypeCode) {
    this.linkTypeCode = linkTypeCode == null ? null : linkTypeCode.trim();
  }

  public Long getLinkId() {
    return linkId;
  }

  public void setLinkId(Long linkId) {
    this.linkId = linkId;
  }

  public Date getOccurredAt() {
    return occurredAt;
  }

  public void setOccurredAt(Date occurredAt) {
    this.occurredAt = occurredAt;
  }

  public String getAggregatedKey() {
    return aggregatedKey;
  }

  public void setAggregatedKey(String aggregatedKey) {
    this.aggregatedKey = aggregatedKey == null ? null : aggregatedKey.trim();
  }

  public Integer getAggregatedCount() {
    return aggregatedCount;
  }

  public void setAggregatedCount(Integer aggregatedCount) {
    this.aggregatedCount = aggregatedCount;
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

  public String getParamsJson() {
    return paramsJson;
  }

  public void setParamsJson(String paramsJson) {
    this.paramsJson = paramsJson == null ? null : paramsJson.trim();
  }
}
