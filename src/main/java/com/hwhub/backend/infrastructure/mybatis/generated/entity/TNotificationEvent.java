package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class TNotificationEvent {
  private Long notificationEventId;

  private Long householdId;

  private String eventTypeCode;

  private Long actorUserId;

  private Long targetUserId;

  private String entityTypeCode;

  private Long entityId;

  private Date occurredAt;

  private Date processedAt;

  private String dedupeKey;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

  private String paramsJson;

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

  public String getEventTypeCode() {
    return eventTypeCode;
  }

  public void setEventTypeCode(String eventTypeCode) {
    this.eventTypeCode = eventTypeCode == null ? null : eventTypeCode.trim();
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

  public String getEntityTypeCode() {
    return entityTypeCode;
  }

  public void setEntityTypeCode(String entityTypeCode) {
    this.entityTypeCode = entityTypeCode == null ? null : entityTypeCode.trim();
  }

  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public Date getOccurredAt() {
    return occurredAt;
  }

  public void setOccurredAt(Date occurredAt) {
    this.occurredAt = occurredAt;
  }

  public Date getProcessedAt() {
    return processedAt;
  }

  public void setProcessedAt(Date processedAt) {
    this.processedAt = processedAt;
  }

  public String getDedupeKey() {
    return dedupeKey;
  }

  public void setDedupeKey(String dedupeKey) {
    this.dedupeKey = dedupeKey == null ? null : dedupeKey.trim();
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
