package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class TNotificationEvent {
  private Long notificationEventId;

  private Long householdId;

  private String notificationType;

  private Long actorUserId;

  private Long targetUserId;

  private Long entityId;

  private Date aggregationDate;

  private Date occurredAt;

  private String eventStatus;

  private String processingKey;

  private Date processingStartedAt;

  private Date processedAt;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

  public Long getNotificationEventId() {
    return notificationEventId;
  }

  public void setNotificationEventId(Long notificationEventId) {
    this.notificationEventId = notificationEventId;
  }

  public Long getHouseholdId() {
    return householdId;
  }

  public void setHouseholdId(Long householdId) {
    this.householdId = householdId;
  }

  public String getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType == null ? null : notificationType.trim();
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

  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public Date getAggregationDate() {
    return aggregationDate;
  }

  public void setAggregationDate(Date aggregationDate) {
    this.aggregationDate = aggregationDate;
  }

  public Date getOccurredAt() {
    return occurredAt;
  }

  public void setOccurredAt(Date occurredAt) {
    this.occurredAt = occurredAt;
  }

  public String getEventStatus() {
    return eventStatus;
  }

  public void setEventStatus(String eventStatus) {
    this.eventStatus = eventStatus == null ? null : eventStatus.trim();
  }

  public String getProcessingKey() {
    return processingKey;
  }

  public void setProcessingKey(String processingKey) {
    this.processingKey = processingKey == null ? null : processingKey.trim();
  }

  public Date getProcessingStartedAt() {
    return processingStartedAt;
  }

  public void setProcessingStartedAt(Date processingStartedAt) {
    this.processingStartedAt = processingStartedAt;
  }

  public Date getProcessedAt() {
    return processedAt;
  }

  public void setProcessedAt(Date processedAt) {
    this.processedAt = processedAt;
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
