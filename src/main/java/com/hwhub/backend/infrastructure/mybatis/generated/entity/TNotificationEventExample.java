package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TNotificationEventExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public TNotificationEventExample() {
    oredCriteria = new ArrayList<>();
  }

  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  public String getOrderByClause() {
    return orderByClause;
  }

  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  protected abstract static class GeneratedCriteria {
    protected List<Criterion> criteria;

    protected GeneratedCriteria() {
      super();
      criteria = new ArrayList<>();
    }

    public boolean isValid() {
      return criteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
      return criteria;
    }

    public List<Criterion> getCriteria() {
      return criteria;
    }

    protected void addCriterion(String condition) {
      if (condition == null) {
        throw new RuntimeException("Value for condition cannot be null");
      }
      criteria.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value));
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value1, value2));
    }

    protected void addCriterionForJDBCDate(String condition, Date value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      addCriterion(condition, new java.sql.Date(value.getTime()), property);
    }

    protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
      if (values == null || values.size() == 0) {
        throw new RuntimeException("Value list for " + property + " cannot be null or empty");
      }
      List<java.sql.Date> dateList = new ArrayList<>();
      Iterator<Date> iter = values.iterator();
      while (iter.hasNext()) {
        dateList.add(new java.sql.Date(iter.next().getTime()));
      }
      addCriterion(condition, dateList, property);
    }

    protected void addCriterionForJDBCDate(
        String condition, Date value1, Date value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      addCriterion(
          condition,
          new java.sql.Date(value1.getTime()),
          new java.sql.Date(value2.getTime()),
          property);
    }

    public Criteria andNotificationEventIdIsNull() {
      addCriterion("notification_event_id is null");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdIsNotNull() {
      addCriterion("notification_event_id is not null");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdEqualTo(Long value) {
      addCriterion("notification_event_id =", value, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdNotEqualTo(Long value) {
      addCriterion("notification_event_id <>", value, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdGreaterThan(Long value) {
      addCriterion("notification_event_id >", value, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdGreaterThanOrEqualTo(Long value) {
      addCriterion("notification_event_id >=", value, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdLessThan(Long value) {
      addCriterion("notification_event_id <", value, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdLessThanOrEqualTo(Long value) {
      addCriterion("notification_event_id <=", value, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdIn(List<Long> values) {
      addCriterion("notification_event_id in", values, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdNotIn(List<Long> values) {
      addCriterion("notification_event_id not in", values, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdBetween(Long value1, Long value2) {
      addCriterion("notification_event_id between", value1, value2, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andNotificationEventIdNotBetween(Long value1, Long value2) {
      addCriterion("notification_event_id not between", value1, value2, "notificationEventId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdIsNull() {
      addCriterion("household_id is null");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdIsNotNull() {
      addCriterion("household_id is not null");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdEqualTo(Long value) {
      addCriterion("household_id =", value, "householdId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdNotEqualTo(Long value) {
      addCriterion("household_id <>", value, "householdId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdGreaterThan(Long value) {
      addCriterion("household_id >", value, "householdId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdGreaterThanOrEqualTo(Long value) {
      addCriterion("household_id >=", value, "householdId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdLessThan(Long value) {
      addCriterion("household_id <", value, "householdId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdLessThanOrEqualTo(Long value) {
      addCriterion("household_id <=", value, "householdId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdIn(List<Long> values) {
      addCriterion("household_id in", values, "householdId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdNotIn(List<Long> values) {
      addCriterion("household_id not in", values, "householdId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdBetween(Long value1, Long value2) {
      addCriterion("household_id between", value1, value2, "householdId");
      return (Criteria) this;
    }

    public Criteria andHouseholdIdNotBetween(Long value1, Long value2) {
      addCriterion("household_id not between", value1, value2, "householdId");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeIsNull() {
      addCriterion("notification_type is null");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeIsNotNull() {
      addCriterion("notification_type is not null");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeEqualTo(String value) {
      addCriterion("notification_type =", value, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeNotEqualTo(String value) {
      addCriterion("notification_type <>", value, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeGreaterThan(String value) {
      addCriterion("notification_type >", value, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeGreaterThanOrEqualTo(String value) {
      addCriterion("notification_type >=", value, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeLessThan(String value) {
      addCriterion("notification_type <", value, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeLessThanOrEqualTo(String value) {
      addCriterion("notification_type <=", value, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeLike(String value) {
      addCriterion("notification_type like", value, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeNotLike(String value) {
      addCriterion("notification_type not like", value, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeIn(List<String> values) {
      addCriterion("notification_type in", values, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeNotIn(List<String> values) {
      addCriterion("notification_type not in", values, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeBetween(String value1, String value2) {
      addCriterion("notification_type between", value1, value2, "notificationType");
      return (Criteria) this;
    }

    public Criteria andNotificationTypeNotBetween(String value1, String value2) {
      addCriterion("notification_type not between", value1, value2, "notificationType");
      return (Criteria) this;
    }

    public Criteria andActorUserIdIsNull() {
      addCriterion("actor_user_id is null");
      return (Criteria) this;
    }

    public Criteria andActorUserIdIsNotNull() {
      addCriterion("actor_user_id is not null");
      return (Criteria) this;
    }

    public Criteria andActorUserIdEqualTo(Long value) {
      addCriterion("actor_user_id =", value, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andActorUserIdNotEqualTo(Long value) {
      addCriterion("actor_user_id <>", value, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andActorUserIdGreaterThan(Long value) {
      addCriterion("actor_user_id >", value, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andActorUserIdGreaterThanOrEqualTo(Long value) {
      addCriterion("actor_user_id >=", value, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andActorUserIdLessThan(Long value) {
      addCriterion("actor_user_id <", value, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andActorUserIdLessThanOrEqualTo(Long value) {
      addCriterion("actor_user_id <=", value, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andActorUserIdIn(List<Long> values) {
      addCriterion("actor_user_id in", values, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andActorUserIdNotIn(List<Long> values) {
      addCriterion("actor_user_id not in", values, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andActorUserIdBetween(Long value1, Long value2) {
      addCriterion("actor_user_id between", value1, value2, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andActorUserIdNotBetween(Long value1, Long value2) {
      addCriterion("actor_user_id not between", value1, value2, "actorUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdIsNull() {
      addCriterion("target_user_id is null");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdIsNotNull() {
      addCriterion("target_user_id is not null");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdEqualTo(Long value) {
      addCriterion("target_user_id =", value, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdNotEqualTo(Long value) {
      addCriterion("target_user_id <>", value, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdGreaterThan(Long value) {
      addCriterion("target_user_id >", value, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdGreaterThanOrEqualTo(Long value) {
      addCriterion("target_user_id >=", value, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdLessThan(Long value) {
      addCriterion("target_user_id <", value, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdLessThanOrEqualTo(Long value) {
      addCriterion("target_user_id <=", value, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdIn(List<Long> values) {
      addCriterion("target_user_id in", values, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdNotIn(List<Long> values) {
      addCriterion("target_user_id not in", values, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdBetween(Long value1, Long value2) {
      addCriterion("target_user_id between", value1, value2, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andTargetUserIdNotBetween(Long value1, Long value2) {
      addCriterion("target_user_id not between", value1, value2, "targetUserId");
      return (Criteria) this;
    }

    public Criteria andEntityIdIsNull() {
      addCriterion("entity_id is null");
      return (Criteria) this;
    }

    public Criteria andEntityIdIsNotNull() {
      addCriterion("entity_id is not null");
      return (Criteria) this;
    }

    public Criteria andEntityIdEqualTo(Long value) {
      addCriterion("entity_id =", value, "entityId");
      return (Criteria) this;
    }

    public Criteria andEntityIdNotEqualTo(Long value) {
      addCriterion("entity_id <>", value, "entityId");
      return (Criteria) this;
    }

    public Criteria andEntityIdGreaterThan(Long value) {
      addCriterion("entity_id >", value, "entityId");
      return (Criteria) this;
    }

    public Criteria andEntityIdGreaterThanOrEqualTo(Long value) {
      addCriterion("entity_id >=", value, "entityId");
      return (Criteria) this;
    }

    public Criteria andEntityIdLessThan(Long value) {
      addCriterion("entity_id <", value, "entityId");
      return (Criteria) this;
    }

    public Criteria andEntityIdLessThanOrEqualTo(Long value) {
      addCriterion("entity_id <=", value, "entityId");
      return (Criteria) this;
    }

    public Criteria andEntityIdIn(List<Long> values) {
      addCriterion("entity_id in", values, "entityId");
      return (Criteria) this;
    }

    public Criteria andEntityIdNotIn(List<Long> values) {
      addCriterion("entity_id not in", values, "entityId");
      return (Criteria) this;
    }

    public Criteria andEntityIdBetween(Long value1, Long value2) {
      addCriterion("entity_id between", value1, value2, "entityId");
      return (Criteria) this;
    }

    public Criteria andEntityIdNotBetween(Long value1, Long value2) {
      addCriterion("entity_id not between", value1, value2, "entityId");
      return (Criteria) this;
    }

    public Criteria andAggregationDateIsNull() {
      addCriterion("aggregation_date is null");
      return (Criteria) this;
    }

    public Criteria andAggregationDateIsNotNull() {
      addCriterion("aggregation_date is not null");
      return (Criteria) this;
    }

    public Criteria andAggregationDateEqualTo(Date value) {
      addCriterionForJDBCDate("aggregation_date =", value, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andAggregationDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("aggregation_date <>", value, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andAggregationDateGreaterThan(Date value) {
      addCriterionForJDBCDate("aggregation_date >", value, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andAggregationDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("aggregation_date >=", value, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andAggregationDateLessThan(Date value) {
      addCriterionForJDBCDate("aggregation_date <", value, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andAggregationDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("aggregation_date <=", value, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andAggregationDateIn(List<Date> values) {
      addCriterionForJDBCDate("aggregation_date in", values, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andAggregationDateNotIn(List<Date> values) {
      addCriterionForJDBCDate("aggregation_date not in", values, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andAggregationDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("aggregation_date between", value1, value2, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andAggregationDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("aggregation_date not between", value1, value2, "aggregationDate");
      return (Criteria) this;
    }

    public Criteria andOccurredAtIsNull() {
      addCriterion("occurred_at is null");
      return (Criteria) this;
    }

    public Criteria andOccurredAtIsNotNull() {
      addCriterion("occurred_at is not null");
      return (Criteria) this;
    }

    public Criteria andOccurredAtEqualTo(Date value) {
      addCriterion("occurred_at =", value, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andOccurredAtNotEqualTo(Date value) {
      addCriterion("occurred_at <>", value, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andOccurredAtGreaterThan(Date value) {
      addCriterion("occurred_at >", value, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andOccurredAtGreaterThanOrEqualTo(Date value) {
      addCriterion("occurred_at >=", value, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andOccurredAtLessThan(Date value) {
      addCriterion("occurred_at <", value, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andOccurredAtLessThanOrEqualTo(Date value) {
      addCriterion("occurred_at <=", value, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andOccurredAtIn(List<Date> values) {
      addCriterion("occurred_at in", values, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andOccurredAtNotIn(List<Date> values) {
      addCriterion("occurred_at not in", values, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andOccurredAtBetween(Date value1, Date value2) {
      addCriterion("occurred_at between", value1, value2, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andOccurredAtNotBetween(Date value1, Date value2) {
      addCriterion("occurred_at not between", value1, value2, "occurredAt");
      return (Criteria) this;
    }

    public Criteria andEventStatusIsNull() {
      addCriterion("event_status is null");
      return (Criteria) this;
    }

    public Criteria andEventStatusIsNotNull() {
      addCriterion("event_status is not null");
      return (Criteria) this;
    }

    public Criteria andEventStatusEqualTo(String value) {
      addCriterion("event_status =", value, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusNotEqualTo(String value) {
      addCriterion("event_status <>", value, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusGreaterThan(String value) {
      addCriterion("event_status >", value, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusGreaterThanOrEqualTo(String value) {
      addCriterion("event_status >=", value, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusLessThan(String value) {
      addCriterion("event_status <", value, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusLessThanOrEqualTo(String value) {
      addCriterion("event_status <=", value, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusLike(String value) {
      addCriterion("event_status like", value, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusNotLike(String value) {
      addCriterion("event_status not like", value, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusIn(List<String> values) {
      addCriterion("event_status in", values, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusNotIn(List<String> values) {
      addCriterion("event_status not in", values, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusBetween(String value1, String value2) {
      addCriterion("event_status between", value1, value2, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andEventStatusNotBetween(String value1, String value2) {
      addCriterion("event_status not between", value1, value2, "eventStatus");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyIsNull() {
      addCriterion("processing_key is null");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyIsNotNull() {
      addCriterion("processing_key is not null");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyEqualTo(String value) {
      addCriterion("processing_key =", value, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyNotEqualTo(String value) {
      addCriterion("processing_key <>", value, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyGreaterThan(String value) {
      addCriterion("processing_key >", value, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyGreaterThanOrEqualTo(String value) {
      addCriterion("processing_key >=", value, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyLessThan(String value) {
      addCriterion("processing_key <", value, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyLessThanOrEqualTo(String value) {
      addCriterion("processing_key <=", value, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyLike(String value) {
      addCriterion("processing_key like", value, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyNotLike(String value) {
      addCriterion("processing_key not like", value, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyIn(List<String> values) {
      addCriterion("processing_key in", values, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyNotIn(List<String> values) {
      addCriterion("processing_key not in", values, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyBetween(String value1, String value2) {
      addCriterion("processing_key between", value1, value2, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingKeyNotBetween(String value1, String value2) {
      addCriterion("processing_key not between", value1, value2, "processingKey");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtIsNull() {
      addCriterion("processing_started_at is null");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtIsNotNull() {
      addCriterion("processing_started_at is not null");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtEqualTo(Date value) {
      addCriterion("processing_started_at =", value, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtNotEqualTo(Date value) {
      addCriterion("processing_started_at <>", value, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtGreaterThan(Date value) {
      addCriterion("processing_started_at >", value, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtGreaterThanOrEqualTo(Date value) {
      addCriterion("processing_started_at >=", value, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtLessThan(Date value) {
      addCriterion("processing_started_at <", value, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtLessThanOrEqualTo(Date value) {
      addCriterion("processing_started_at <=", value, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtIn(List<Date> values) {
      addCriterion("processing_started_at in", values, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtNotIn(List<Date> values) {
      addCriterion("processing_started_at not in", values, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtBetween(Date value1, Date value2) {
      addCriterion("processing_started_at between", value1, value2, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessingStartedAtNotBetween(Date value1, Date value2) {
      addCriterion("processing_started_at not between", value1, value2, "processingStartedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtIsNull() {
      addCriterion("processed_at is null");
      return (Criteria) this;
    }

    public Criteria andProcessedAtIsNotNull() {
      addCriterion("processed_at is not null");
      return (Criteria) this;
    }

    public Criteria andProcessedAtEqualTo(Date value) {
      addCriterion("processed_at =", value, "processedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtNotEqualTo(Date value) {
      addCriterion("processed_at <>", value, "processedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtGreaterThan(Date value) {
      addCriterion("processed_at >", value, "processedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtGreaterThanOrEqualTo(Date value) {
      addCriterion("processed_at >=", value, "processedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtLessThan(Date value) {
      addCriterion("processed_at <", value, "processedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtLessThanOrEqualTo(Date value) {
      addCriterion("processed_at <=", value, "processedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtIn(List<Date> values) {
      addCriterion("processed_at in", values, "processedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtNotIn(List<Date> values) {
      addCriterion("processed_at not in", values, "processedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtBetween(Date value1, Date value2) {
      addCriterion("processed_at between", value1, value2, "processedAt");
      return (Criteria) this;
    }

    public Criteria andProcessedAtNotBetween(Date value1, Date value2) {
      addCriterion("processed_at not between", value1, value2, "processedAt");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdIsNull() {
      addCriterion("create_user_id is null");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdIsNotNull() {
      addCriterion("create_user_id is not null");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdEqualTo(Long value) {
      addCriterion("create_user_id =", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotEqualTo(Long value) {
      addCriterion("create_user_id <>", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdGreaterThan(Long value) {
      addCriterion("create_user_id >", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdGreaterThanOrEqualTo(Long value) {
      addCriterion("create_user_id >=", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdLessThan(Long value) {
      addCriterion("create_user_id <", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdLessThanOrEqualTo(Long value) {
      addCriterion("create_user_id <=", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdIn(List<Long> values) {
      addCriterion("create_user_id in", values, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotIn(List<Long> values) {
      addCriterion("create_user_id not in", values, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdBetween(Long value1, Long value2) {
      addCriterion("create_user_id between", value1, value2, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotBetween(Long value1, Long value2) {
      addCriterion("create_user_id not between", value1, value2, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateProgramIsNull() {
      addCriterion("create_program is null");
      return (Criteria) this;
    }

    public Criteria andCreateProgramIsNotNull() {
      addCriterion("create_program is not null");
      return (Criteria) this;
    }

    public Criteria andCreateProgramEqualTo(String value) {
      addCriterion("create_program =", value, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramNotEqualTo(String value) {
      addCriterion("create_program <>", value, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramGreaterThan(String value) {
      addCriterion("create_program >", value, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramGreaterThanOrEqualTo(String value) {
      addCriterion("create_program >=", value, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramLessThan(String value) {
      addCriterion("create_program <", value, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramLessThanOrEqualTo(String value) {
      addCriterion("create_program <=", value, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramLike(String value) {
      addCriterion("create_program like", value, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramNotLike(String value) {
      addCriterion("create_program not like", value, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramIn(List<String> values) {
      addCriterion("create_program in", values, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramNotIn(List<String> values) {
      addCriterion("create_program not in", values, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramBetween(String value1, String value2) {
      addCriterion("create_program between", value1, value2, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreateProgramNotBetween(String value1, String value2) {
      addCriterion("create_program not between", value1, value2, "createProgram");
      return (Criteria) this;
    }

    public Criteria andCreatedAtIsNull() {
      addCriterion("created_at is null");
      return (Criteria) this;
    }

    public Criteria andCreatedAtIsNotNull() {
      addCriterion("created_at is not null");
      return (Criteria) this;
    }

    public Criteria andCreatedAtEqualTo(Date value) {
      addCriterion("created_at =", value, "createdAt");
      return (Criteria) this;
    }

    public Criteria andCreatedAtNotEqualTo(Date value) {
      addCriterion("created_at <>", value, "createdAt");
      return (Criteria) this;
    }

    public Criteria andCreatedAtGreaterThan(Date value) {
      addCriterion("created_at >", value, "createdAt");
      return (Criteria) this;
    }

    public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
      addCriterion("created_at >=", value, "createdAt");
      return (Criteria) this;
    }

    public Criteria andCreatedAtLessThan(Date value) {
      addCriterion("created_at <", value, "createdAt");
      return (Criteria) this;
    }

    public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
      addCriterion("created_at <=", value, "createdAt");
      return (Criteria) this;
    }

    public Criteria andCreatedAtIn(List<Date> values) {
      addCriterion("created_at in", values, "createdAt");
      return (Criteria) this;
    }

    public Criteria andCreatedAtNotIn(List<Date> values) {
      addCriterion("created_at not in", values, "createdAt");
      return (Criteria) this;
    }

    public Criteria andCreatedAtBetween(Date value1, Date value2) {
      addCriterion("created_at between", value1, value2, "createdAt");
      return (Criteria) this;
    }

    public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
      addCriterion("created_at not between", value1, value2, "createdAt");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdIsNull() {
      addCriterion("update_user_id is null");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdIsNotNull() {
      addCriterion("update_user_id is not null");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdEqualTo(Long value) {
      addCriterion("update_user_id =", value, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdNotEqualTo(Long value) {
      addCriterion("update_user_id <>", value, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdGreaterThan(Long value) {
      addCriterion("update_user_id >", value, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdGreaterThanOrEqualTo(Long value) {
      addCriterion("update_user_id >=", value, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdLessThan(Long value) {
      addCriterion("update_user_id <", value, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdLessThanOrEqualTo(Long value) {
      addCriterion("update_user_id <=", value, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdIn(List<Long> values) {
      addCriterion("update_user_id in", values, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdNotIn(List<Long> values) {
      addCriterion("update_user_id not in", values, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdBetween(Long value1, Long value2) {
      addCriterion("update_user_id between", value1, value2, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateUserIdNotBetween(Long value1, Long value2) {
      addCriterion("update_user_id not between", value1, value2, "updateUserId");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramIsNull() {
      addCriterion("update_program is null");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramIsNotNull() {
      addCriterion("update_program is not null");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramEqualTo(String value) {
      addCriterion("update_program =", value, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramNotEqualTo(String value) {
      addCriterion("update_program <>", value, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramGreaterThan(String value) {
      addCriterion("update_program >", value, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramGreaterThanOrEqualTo(String value) {
      addCriterion("update_program >=", value, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramLessThan(String value) {
      addCriterion("update_program <", value, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramLessThanOrEqualTo(String value) {
      addCriterion("update_program <=", value, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramLike(String value) {
      addCriterion("update_program like", value, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramNotLike(String value) {
      addCriterion("update_program not like", value, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramIn(List<String> values) {
      addCriterion("update_program in", values, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramNotIn(List<String> values) {
      addCriterion("update_program not in", values, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramBetween(String value1, String value2) {
      addCriterion("update_program between", value1, value2, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdateProgramNotBetween(String value1, String value2) {
      addCriterion("update_program not between", value1, value2, "updateProgram");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtIsNull() {
      addCriterion("updated_at is null");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtIsNotNull() {
      addCriterion("updated_at is not null");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtEqualTo(Date value) {
      addCriterion("updated_at =", value, "updatedAt");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtNotEqualTo(Date value) {
      addCriterion("updated_at <>", value, "updatedAt");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtGreaterThan(Date value) {
      addCriterion("updated_at >", value, "updatedAt");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
      addCriterion("updated_at >=", value, "updatedAt");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtLessThan(Date value) {
      addCriterion("updated_at <", value, "updatedAt");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
      addCriterion("updated_at <=", value, "updatedAt");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtIn(List<Date> values) {
      addCriterion("updated_at in", values, "updatedAt");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtNotIn(List<Date> values) {
      addCriterion("updated_at not in", values, "updatedAt");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtBetween(Date value1, Date value2) {
      addCriterion("updated_at between", value1, value2, "updatedAt");
      return (Criteria) this;
    }

    public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
      addCriterion("updated_at not between", value1, value2, "updatedAt");
      return (Criteria) this;
    }
  }

  public static class Criteria extends GeneratedCriteria {
    protected Criteria() {
      super();
    }
  }

  public static class Criterion {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    public String getCondition() {
      return condition;
    }

    public Object getValue() {
      return value;
    }

    public Object getSecondValue() {
      return secondValue;
    }

    public boolean isNoValue() {
      return noValue;
    }

    public boolean isSingleValue() {
      return singleValue;
    }

    public boolean isBetweenValue() {
      return betweenValue;
    }

    public boolean isListValue() {
      return listValue;
    }

    public String getTypeHandler() {
      return typeHandler;
    }

    protected Criterion(String condition) {
      super();
      this.condition = condition;
      this.typeHandler = null;
      this.noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.typeHandler = typeHandler;
      if (value instanceof List<?>) {
        this.listValue = true;
      } else {
        this.singleValue = true;
      }
    }

    protected Criterion(String condition, Object value) {
      this(condition, value, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.secondValue = secondValue;
      this.typeHandler = typeHandler;
      this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
      this(condition, value, secondValue, null);
    }
  }
}
