package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TNotificationExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public TNotificationExample() {
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

    public Criteria andNotificationIdIsNull() {
      addCriterion("notification_id is null");
      return (Criteria) this;
    }

    public Criteria andNotificationIdIsNotNull() {
      addCriterion("notification_id is not null");
      return (Criteria) this;
    }

    public Criteria andNotificationIdEqualTo(Long value) {
      addCriterion("notification_id =", value, "notificationId");
      return (Criteria) this;
    }

    public Criteria andNotificationIdNotEqualTo(Long value) {
      addCriterion("notification_id <>", value, "notificationId");
      return (Criteria) this;
    }

    public Criteria andNotificationIdGreaterThan(Long value) {
      addCriterion("notification_id >", value, "notificationId");
      return (Criteria) this;
    }

    public Criteria andNotificationIdGreaterThanOrEqualTo(Long value) {
      addCriterion("notification_id >=", value, "notificationId");
      return (Criteria) this;
    }

    public Criteria andNotificationIdLessThan(Long value) {
      addCriterion("notification_id <", value, "notificationId");
      return (Criteria) this;
    }

    public Criteria andNotificationIdLessThanOrEqualTo(Long value) {
      addCriterion("notification_id <=", value, "notificationId");
      return (Criteria) this;
    }

    public Criteria andNotificationIdIn(List<Long> values) {
      addCriterion("notification_id in", values, "notificationId");
      return (Criteria) this;
    }

    public Criteria andNotificationIdNotIn(List<Long> values) {
      addCriterion("notification_id not in", values, "notificationId");
      return (Criteria) this;
    }

    public Criteria andNotificationIdBetween(Long value1, Long value2) {
      addCriterion("notification_id between", value1, value2, "notificationId");
      return (Criteria) this;
    }

    public Criteria andNotificationIdNotBetween(Long value1, Long value2) {
      addCriterion("notification_id not between", value1, value2, "notificationId");
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

    public Criteria andIsReadIsNull() {
      addCriterion("is_read is null");
      return (Criteria) this;
    }

    public Criteria andIsReadIsNotNull() {
      addCriterion("is_read is not null");
      return (Criteria) this;
    }

    public Criteria andIsReadEqualTo(Boolean value) {
      addCriterion("is_read =", value, "isRead");
      return (Criteria) this;
    }

    public Criteria andIsReadNotEqualTo(Boolean value) {
      addCriterion("is_read <>", value, "isRead");
      return (Criteria) this;
    }

    public Criteria andIsReadGreaterThan(Boolean value) {
      addCriterion("is_read >", value, "isRead");
      return (Criteria) this;
    }

    public Criteria andIsReadGreaterThanOrEqualTo(Boolean value) {
      addCriterion("is_read >=", value, "isRead");
      return (Criteria) this;
    }

    public Criteria andIsReadLessThan(Boolean value) {
      addCriterion("is_read <", value, "isRead");
      return (Criteria) this;
    }

    public Criteria andIsReadLessThanOrEqualTo(Boolean value) {
      addCriterion("is_read <=", value, "isRead");
      return (Criteria) this;
    }

    public Criteria andIsReadIn(List<Boolean> values) {
      addCriterion("is_read in", values, "isRead");
      return (Criteria) this;
    }

    public Criteria andIsReadNotIn(List<Boolean> values) {
      addCriterion("is_read not in", values, "isRead");
      return (Criteria) this;
    }

    public Criteria andIsReadBetween(Boolean value1, Boolean value2) {
      addCriterion("is_read between", value1, value2, "isRead");
      return (Criteria) this;
    }

    public Criteria andIsReadNotBetween(Boolean value1, Boolean value2) {
      addCriterion("is_read not between", value1, value2, "isRead");
      return (Criteria) this;
    }

    public Criteria andReadAtIsNull() {
      addCriterion("read_at is null");
      return (Criteria) this;
    }

    public Criteria andReadAtIsNotNull() {
      addCriterion("read_at is not null");
      return (Criteria) this;
    }

    public Criteria andReadAtEqualTo(Date value) {
      addCriterion("read_at =", value, "readAt");
      return (Criteria) this;
    }

    public Criteria andReadAtNotEqualTo(Date value) {
      addCriterion("read_at <>", value, "readAt");
      return (Criteria) this;
    }

    public Criteria andReadAtGreaterThan(Date value) {
      addCriterion("read_at >", value, "readAt");
      return (Criteria) this;
    }

    public Criteria andReadAtGreaterThanOrEqualTo(Date value) {
      addCriterion("read_at >=", value, "readAt");
      return (Criteria) this;
    }

    public Criteria andReadAtLessThan(Date value) {
      addCriterion("read_at <", value, "readAt");
      return (Criteria) this;
    }

    public Criteria andReadAtLessThanOrEqualTo(Date value) {
      addCriterion("read_at <=", value, "readAt");
      return (Criteria) this;
    }

    public Criteria andReadAtIn(List<Date> values) {
      addCriterion("read_at in", values, "readAt");
      return (Criteria) this;
    }

    public Criteria andReadAtNotIn(List<Date> values) {
      addCriterion("read_at not in", values, "readAt");
      return (Criteria) this;
    }

    public Criteria andReadAtBetween(Date value1, Date value2) {
      addCriterion("read_at between", value1, value2, "readAt");
      return (Criteria) this;
    }

    public Criteria andReadAtNotBetween(Date value1, Date value2) {
      addCriterion("read_at not between", value1, value2, "readAt");
      return (Criteria) this;
    }

    public Criteria andTitleKeyIsNull() {
      addCriterion("title_key is null");
      return (Criteria) this;
    }

    public Criteria andTitleKeyIsNotNull() {
      addCriterion("title_key is not null");
      return (Criteria) this;
    }

    public Criteria andTitleKeyEqualTo(String value) {
      addCriterion("title_key =", value, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyNotEqualTo(String value) {
      addCriterion("title_key <>", value, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyGreaterThan(String value) {
      addCriterion("title_key >", value, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyGreaterThanOrEqualTo(String value) {
      addCriterion("title_key >=", value, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyLessThan(String value) {
      addCriterion("title_key <", value, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyLessThanOrEqualTo(String value) {
      addCriterion("title_key <=", value, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyLike(String value) {
      addCriterion("title_key like", value, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyNotLike(String value) {
      addCriterion("title_key not like", value, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyIn(List<String> values) {
      addCriterion("title_key in", values, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyNotIn(List<String> values) {
      addCriterion("title_key not in", values, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyBetween(String value1, String value2) {
      addCriterion("title_key between", value1, value2, "titleKey");
      return (Criteria) this;
    }

    public Criteria andTitleKeyNotBetween(String value1, String value2) {
      addCriterion("title_key not between", value1, value2, "titleKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyIsNull() {
      addCriterion("body_key is null");
      return (Criteria) this;
    }

    public Criteria andBodyKeyIsNotNull() {
      addCriterion("body_key is not null");
      return (Criteria) this;
    }

    public Criteria andBodyKeyEqualTo(String value) {
      addCriterion("body_key =", value, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyNotEqualTo(String value) {
      addCriterion("body_key <>", value, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyGreaterThan(String value) {
      addCriterion("body_key >", value, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyGreaterThanOrEqualTo(String value) {
      addCriterion("body_key >=", value, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyLessThan(String value) {
      addCriterion("body_key <", value, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyLessThanOrEqualTo(String value) {
      addCriterion("body_key <=", value, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyLike(String value) {
      addCriterion("body_key like", value, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyNotLike(String value) {
      addCriterion("body_key not like", value, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyIn(List<String> values) {
      addCriterion("body_key in", values, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyNotIn(List<String> values) {
      addCriterion("body_key not in", values, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyBetween(String value1, String value2) {
      addCriterion("body_key between", value1, value2, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andBodyKeyNotBetween(String value1, String value2) {
      addCriterion("body_key not between", value1, value2, "bodyKey");
      return (Criteria) this;
    }

    public Criteria andLinkTypeIsNull() {
      addCriterion("link_type is null");
      return (Criteria) this;
    }

    public Criteria andLinkTypeIsNotNull() {
      addCriterion("link_type is not null");
      return (Criteria) this;
    }

    public Criteria andLinkTypeEqualTo(String value) {
      addCriterion("link_type =", value, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeNotEqualTo(String value) {
      addCriterion("link_type <>", value, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeGreaterThan(String value) {
      addCriterion("link_type >", value, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeGreaterThanOrEqualTo(String value) {
      addCriterion("link_type >=", value, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeLessThan(String value) {
      addCriterion("link_type <", value, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeLessThanOrEqualTo(String value) {
      addCriterion("link_type <=", value, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeLike(String value) {
      addCriterion("link_type like", value, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeNotLike(String value) {
      addCriterion("link_type not like", value, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeIn(List<String> values) {
      addCriterion("link_type in", values, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeNotIn(List<String> values) {
      addCriterion("link_type not in", values, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeBetween(String value1, String value2) {
      addCriterion("link_type between", value1, value2, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkTypeNotBetween(String value1, String value2) {
      addCriterion("link_type not between", value1, value2, "linkType");
      return (Criteria) this;
    }

    public Criteria andLinkIdIsNull() {
      addCriterion("link_id is null");
      return (Criteria) this;
    }

    public Criteria andLinkIdIsNotNull() {
      addCriterion("link_id is not null");
      return (Criteria) this;
    }

    public Criteria andLinkIdEqualTo(Long value) {
      addCriterion("link_id =", value, "linkId");
      return (Criteria) this;
    }

    public Criteria andLinkIdNotEqualTo(Long value) {
      addCriterion("link_id <>", value, "linkId");
      return (Criteria) this;
    }

    public Criteria andLinkIdGreaterThan(Long value) {
      addCriterion("link_id >", value, "linkId");
      return (Criteria) this;
    }

    public Criteria andLinkIdGreaterThanOrEqualTo(Long value) {
      addCriterion("link_id >=", value, "linkId");
      return (Criteria) this;
    }

    public Criteria andLinkIdLessThan(Long value) {
      addCriterion("link_id <", value, "linkId");
      return (Criteria) this;
    }

    public Criteria andLinkIdLessThanOrEqualTo(Long value) {
      addCriterion("link_id <=", value, "linkId");
      return (Criteria) this;
    }

    public Criteria andLinkIdIn(List<Long> values) {
      addCriterion("link_id in", values, "linkId");
      return (Criteria) this;
    }

    public Criteria andLinkIdNotIn(List<Long> values) {
      addCriterion("link_id not in", values, "linkId");
      return (Criteria) this;
    }

    public Criteria andLinkIdBetween(Long value1, Long value2) {
      addCriterion("link_id between", value1, value2, "linkId");
      return (Criteria) this;
    }

    public Criteria andLinkIdNotBetween(Long value1, Long value2) {
      addCriterion("link_id not between", value1, value2, "linkId");
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

    public Criteria andAggregatedKeyIsNull() {
      addCriterion("aggregated_key is null");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyIsNotNull() {
      addCriterion("aggregated_key is not null");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyEqualTo(String value) {
      addCriterion("aggregated_key =", value, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyNotEqualTo(String value) {
      addCriterion("aggregated_key <>", value, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyGreaterThan(String value) {
      addCriterion("aggregated_key >", value, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyGreaterThanOrEqualTo(String value) {
      addCriterion("aggregated_key >=", value, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyLessThan(String value) {
      addCriterion("aggregated_key <", value, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyLessThanOrEqualTo(String value) {
      addCriterion("aggregated_key <=", value, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyLike(String value) {
      addCriterion("aggregated_key like", value, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyNotLike(String value) {
      addCriterion("aggregated_key not like", value, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyIn(List<String> values) {
      addCriterion("aggregated_key in", values, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyNotIn(List<String> values) {
      addCriterion("aggregated_key not in", values, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyBetween(String value1, String value2) {
      addCriterion("aggregated_key between", value1, value2, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedKeyNotBetween(String value1, String value2) {
      addCriterion("aggregated_key not between", value1, value2, "aggregatedKey");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountIsNull() {
      addCriterion("aggregated_count is null");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountIsNotNull() {
      addCriterion("aggregated_count is not null");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountEqualTo(Integer value) {
      addCriterion("aggregated_count =", value, "aggregatedCount");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountNotEqualTo(Integer value) {
      addCriterion("aggregated_count <>", value, "aggregatedCount");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountGreaterThan(Integer value) {
      addCriterion("aggregated_count >", value, "aggregatedCount");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountGreaterThanOrEqualTo(Integer value) {
      addCriterion("aggregated_count >=", value, "aggregatedCount");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountLessThan(Integer value) {
      addCriterion("aggregated_count <", value, "aggregatedCount");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountLessThanOrEqualTo(Integer value) {
      addCriterion("aggregated_count <=", value, "aggregatedCount");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountIn(List<Integer> values) {
      addCriterion("aggregated_count in", values, "aggregatedCount");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountNotIn(List<Integer> values) {
      addCriterion("aggregated_count not in", values, "aggregatedCount");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountBetween(Integer value1, Integer value2) {
      addCriterion("aggregated_count between", value1, value2, "aggregatedCount");
      return (Criteria) this;
    }

    public Criteria andAggregatedCountNotBetween(Integer value1, Integer value2) {
      addCriterion("aggregated_count not between", value1, value2, "aggregatedCount");
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
