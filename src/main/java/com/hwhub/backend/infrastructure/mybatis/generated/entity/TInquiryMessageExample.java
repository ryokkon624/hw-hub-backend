package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TInquiryMessageExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public TInquiryMessageExample() {
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

    public Criteria andMessageIdIsNull() {
      addCriterion("message_id is null");
      return (Criteria) this;
    }

    public Criteria andMessageIdIsNotNull() {
      addCriterion("message_id is not null");
      return (Criteria) this;
    }

    public Criteria andMessageIdEqualTo(Long value) {
      addCriterion("message_id =", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdNotEqualTo(Long value) {
      addCriterion("message_id <>", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdGreaterThan(Long value) {
      addCriterion("message_id >", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdGreaterThanOrEqualTo(Long value) {
      addCriterion("message_id >=", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdLessThan(Long value) {
      addCriterion("message_id <", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdLessThanOrEqualTo(Long value) {
      addCriterion("message_id <=", value, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdIn(List<Long> values) {
      addCriterion("message_id in", values, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdNotIn(List<Long> values) {
      addCriterion("message_id not in", values, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdBetween(Long value1, Long value2) {
      addCriterion("message_id between", value1, value2, "messageId");
      return (Criteria) this;
    }

    public Criteria andMessageIdNotBetween(Long value1, Long value2) {
      addCriterion("message_id not between", value1, value2, "messageId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdIsNull() {
      addCriterion("inquiry_id is null");
      return (Criteria) this;
    }

    public Criteria andInquiryIdIsNotNull() {
      addCriterion("inquiry_id is not null");
      return (Criteria) this;
    }

    public Criteria andInquiryIdEqualTo(Long value) {
      addCriterion("inquiry_id =", value, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdNotEqualTo(Long value) {
      addCriterion("inquiry_id <>", value, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdGreaterThan(Long value) {
      addCriterion("inquiry_id >", value, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdGreaterThanOrEqualTo(Long value) {
      addCriterion("inquiry_id >=", value, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdLessThan(Long value) {
      addCriterion("inquiry_id <", value, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdLessThanOrEqualTo(Long value) {
      addCriterion("inquiry_id <=", value, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdIn(List<Long> values) {
      addCriterion("inquiry_id in", values, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdNotIn(List<Long> values) {
      addCriterion("inquiry_id not in", values, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdBetween(Long value1, Long value2) {
      addCriterion("inquiry_id between", value1, value2, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andInquiryIdNotBetween(Long value1, Long value2) {
      addCriterion("inquiry_id not between", value1, value2, "inquiryId");
      return (Criteria) this;
    }

    public Criteria andSeqIsNull() {
      addCriterion("seq is null");
      return (Criteria) this;
    }

    public Criteria andSeqIsNotNull() {
      addCriterion("seq is not null");
      return (Criteria) this;
    }

    public Criteria andSeqEqualTo(Integer value) {
      addCriterion("seq =", value, "seq");
      return (Criteria) this;
    }

    public Criteria andSeqNotEqualTo(Integer value) {
      addCriterion("seq <>", value, "seq");
      return (Criteria) this;
    }

    public Criteria andSeqGreaterThan(Integer value) {
      addCriterion("seq >", value, "seq");
      return (Criteria) this;
    }

    public Criteria andSeqGreaterThanOrEqualTo(Integer value) {
      addCriterion("seq >=", value, "seq");
      return (Criteria) this;
    }

    public Criteria andSeqLessThan(Integer value) {
      addCriterion("seq <", value, "seq");
      return (Criteria) this;
    }

    public Criteria andSeqLessThanOrEqualTo(Integer value) {
      addCriterion("seq <=", value, "seq");
      return (Criteria) this;
    }

    public Criteria andSeqIn(List<Integer> values) {
      addCriterion("seq in", values, "seq");
      return (Criteria) this;
    }

    public Criteria andSeqNotIn(List<Integer> values) {
      addCriterion("seq not in", values, "seq");
      return (Criteria) this;
    }

    public Criteria andSeqBetween(Integer value1, Integer value2) {
      addCriterion("seq between", value1, value2, "seq");
      return (Criteria) this;
    }

    public Criteria andSeqNotBetween(Integer value1, Integer value2) {
      addCriterion("seq not between", value1, value2, "seq");
      return (Criteria) this;
    }

    public Criteria andSenderTypeIsNull() {
      addCriterion("sender_type is null");
      return (Criteria) this;
    }

    public Criteria andSenderTypeIsNotNull() {
      addCriterion("sender_type is not null");
      return (Criteria) this;
    }

    public Criteria andSenderTypeEqualTo(String value) {
      addCriterion("sender_type =", value, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeNotEqualTo(String value) {
      addCriterion("sender_type <>", value, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeGreaterThan(String value) {
      addCriterion("sender_type >", value, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeGreaterThanOrEqualTo(String value) {
      addCriterion("sender_type >=", value, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeLessThan(String value) {
      addCriterion("sender_type <", value, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeLessThanOrEqualTo(String value) {
      addCriterion("sender_type <=", value, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeLike(String value) {
      addCriterion("sender_type like", value, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeNotLike(String value) {
      addCriterion("sender_type not like", value, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeIn(List<String> values) {
      addCriterion("sender_type in", values, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeNotIn(List<String> values) {
      addCriterion("sender_type not in", values, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeBetween(String value1, String value2) {
      addCriterion("sender_type between", value1, value2, "senderType");
      return (Criteria) this;
    }

    public Criteria andSenderTypeNotBetween(String value1, String value2) {
      addCriterion("sender_type not between", value1, value2, "senderType");
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
