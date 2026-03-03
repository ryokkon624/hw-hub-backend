package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MUserNotificationSettingExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public MUserNotificationSettingExample() {
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

    public Criteria andUserIdIsNull() {
      addCriterion("user_id is null");
      return (Criteria) this;
    }

    public Criteria andUserIdIsNotNull() {
      addCriterion("user_id is not null");
      return (Criteria) this;
    }

    public Criteria andUserIdEqualTo(Long value) {
      addCriterion("user_id =", value, "userId");
      return (Criteria) this;
    }

    public Criteria andUserIdNotEqualTo(Long value) {
      addCriterion("user_id <>", value, "userId");
      return (Criteria) this;
    }

    public Criteria andUserIdGreaterThan(Long value) {
      addCriterion("user_id >", value, "userId");
      return (Criteria) this;
    }

    public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
      addCriterion("user_id >=", value, "userId");
      return (Criteria) this;
    }

    public Criteria andUserIdLessThan(Long value) {
      addCriterion("user_id <", value, "userId");
      return (Criteria) this;
    }

    public Criteria andUserIdLessThanOrEqualTo(Long value) {
      addCriterion("user_id <=", value, "userId");
      return (Criteria) this;
    }

    public Criteria andUserIdIn(List<Long> values) {
      addCriterion("user_id in", values, "userId");
      return (Criteria) this;
    }

    public Criteria andUserIdNotIn(List<Long> values) {
      addCriterion("user_id not in", values, "userId");
      return (Criteria) this;
    }

    public Criteria andUserIdBetween(Long value1, Long value2) {
      addCriterion("user_id between", value1, value2, "userId");
      return (Criteria) this;
    }

    public Criteria andUserIdNotBetween(Long value1, Long value2) {
      addCriterion("user_id not between", value1, value2, "userId");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupIsNull() {
      addCriterion("notification_group is null");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupIsNotNull() {
      addCriterion("notification_group is not null");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupEqualTo(String value) {
      addCriterion("notification_group =", value, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupNotEqualTo(String value) {
      addCriterion("notification_group <>", value, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupGreaterThan(String value) {
      addCriterion("notification_group >", value, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupGreaterThanOrEqualTo(String value) {
      addCriterion("notification_group >=", value, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupLessThan(String value) {
      addCriterion("notification_group <", value, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupLessThanOrEqualTo(String value) {
      addCriterion("notification_group <=", value, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupLike(String value) {
      addCriterion("notification_group like", value, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupNotLike(String value) {
      addCriterion("notification_group not like", value, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupIn(List<String> values) {
      addCriterion("notification_group in", values, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupNotIn(List<String> values) {
      addCriterion("notification_group not in", values, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupBetween(String value1, String value2) {
      addCriterion("notification_group between", value1, value2, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andNotificationGroupNotBetween(String value1, String value2) {
      addCriterion("notification_group not between", value1, value2, "notificationGroup");
      return (Criteria) this;
    }

    public Criteria andEnabledIsNull() {
      addCriterion("enabled is null");
      return (Criteria) this;
    }

    public Criteria andEnabledIsNotNull() {
      addCriterion("enabled is not null");
      return (Criteria) this;
    }

    public Criteria andEnabledEqualTo(Boolean value) {
      addCriterion("enabled =", value, "enabled");
      return (Criteria) this;
    }

    public Criteria andEnabledNotEqualTo(Boolean value) {
      addCriterion("enabled <>", value, "enabled");
      return (Criteria) this;
    }

    public Criteria andEnabledGreaterThan(Boolean value) {
      addCriterion("enabled >", value, "enabled");
      return (Criteria) this;
    }

    public Criteria andEnabledGreaterThanOrEqualTo(Boolean value) {
      addCriterion("enabled >=", value, "enabled");
      return (Criteria) this;
    }

    public Criteria andEnabledLessThan(Boolean value) {
      addCriterion("enabled <", value, "enabled");
      return (Criteria) this;
    }

    public Criteria andEnabledLessThanOrEqualTo(Boolean value) {
      addCriterion("enabled <=", value, "enabled");
      return (Criteria) this;
    }

    public Criteria andEnabledIn(List<Boolean> values) {
      addCriterion("enabled in", values, "enabled");
      return (Criteria) this;
    }

    public Criteria andEnabledNotIn(List<Boolean> values) {
      addCriterion("enabled not in", values, "enabled");
      return (Criteria) this;
    }

    public Criteria andEnabledBetween(Boolean value1, Boolean value2) {
      addCriterion("enabled between", value1, value2, "enabled");
      return (Criteria) this;
    }

    public Criteria andEnabledNotBetween(Boolean value1, Boolean value2) {
      addCriterion("enabled not between", value1, value2, "enabled");
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
