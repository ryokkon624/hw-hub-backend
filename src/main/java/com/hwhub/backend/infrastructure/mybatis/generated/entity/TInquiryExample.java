package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TInquiryExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public TInquiryExample() {
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

    public Criteria andCategoryIsNull() {
      addCriterion("category is null");
      return (Criteria) this;
    }

    public Criteria andCategoryIsNotNull() {
      addCriterion("category is not null");
      return (Criteria) this;
    }

    public Criteria andCategoryEqualTo(String value) {
      addCriterion("category =", value, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryNotEqualTo(String value) {
      addCriterion("category <>", value, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryGreaterThan(String value) {
      addCriterion("category >", value, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryGreaterThanOrEqualTo(String value) {
      addCriterion("category >=", value, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryLessThan(String value) {
      addCriterion("category <", value, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryLessThanOrEqualTo(String value) {
      addCriterion("category <=", value, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryLike(String value) {
      addCriterion("category like", value, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryNotLike(String value) {
      addCriterion("category not like", value, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryIn(List<String> values) {
      addCriterion("category in", values, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryNotIn(List<String> values) {
      addCriterion("category not in", values, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryBetween(String value1, String value2) {
      addCriterion("category between", value1, value2, "category");
      return (Criteria) this;
    }

    public Criteria andCategoryNotBetween(String value1, String value2) {
      addCriterion("category not between", value1, value2, "category");
      return (Criteria) this;
    }

    public Criteria andStatusIsNull() {
      addCriterion("`status` is null");
      return (Criteria) this;
    }

    public Criteria andStatusIsNotNull() {
      addCriterion("`status` is not null");
      return (Criteria) this;
    }

    public Criteria andStatusEqualTo(String value) {
      addCriterion("`status` =", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotEqualTo(String value) {
      addCriterion("`status` <>", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThan(String value) {
      addCriterion("`status` >", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThanOrEqualTo(String value) {
      addCriterion("`status` >=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThan(String value) {
      addCriterion("`status` <", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThanOrEqualTo(String value) {
      addCriterion("`status` <=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLike(String value) {
      addCriterion("`status` like", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotLike(String value) {
      addCriterion("`status` not like", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusIn(List<String> values) {
      addCriterion("`status` in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotIn(List<String> values) {
      addCriterion("`status` not in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusBetween(String value1, String value2) {
      addCriterion("`status` between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotBetween(String value1, String value2) {
      addCriterion("`status` not between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andTitleIsNull() {
      addCriterion("title is null");
      return (Criteria) this;
    }

    public Criteria andTitleIsNotNull() {
      addCriterion("title is not null");
      return (Criteria) this;
    }

    public Criteria andTitleEqualTo(String value) {
      addCriterion("title =", value, "title");
      return (Criteria) this;
    }

    public Criteria andTitleNotEqualTo(String value) {
      addCriterion("title <>", value, "title");
      return (Criteria) this;
    }

    public Criteria andTitleGreaterThan(String value) {
      addCriterion("title >", value, "title");
      return (Criteria) this;
    }

    public Criteria andTitleGreaterThanOrEqualTo(String value) {
      addCriterion("title >=", value, "title");
      return (Criteria) this;
    }

    public Criteria andTitleLessThan(String value) {
      addCriterion("title <", value, "title");
      return (Criteria) this;
    }

    public Criteria andTitleLessThanOrEqualTo(String value) {
      addCriterion("title <=", value, "title");
      return (Criteria) this;
    }

    public Criteria andTitleLike(String value) {
      addCriterion("title like", value, "title");
      return (Criteria) this;
    }

    public Criteria andTitleNotLike(String value) {
      addCriterion("title not like", value, "title");
      return (Criteria) this;
    }

    public Criteria andTitleIn(List<String> values) {
      addCriterion("title in", values, "title");
      return (Criteria) this;
    }

    public Criteria andTitleNotIn(List<String> values) {
      addCriterion("title not in", values, "title");
      return (Criteria) this;
    }

    public Criteria andTitleBetween(String value1, String value2) {
      addCriterion("title between", value1, value2, "title");
      return (Criteria) this;
    }

    public Criteria andTitleNotBetween(String value1, String value2) {
      addCriterion("title not between", value1, value2, "title");
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

    public Criteria andUiClientIsNull() {
      addCriterion("ui_client is null");
      return (Criteria) this;
    }

    public Criteria andUiClientIsNotNull() {
      addCriterion("ui_client is not null");
      return (Criteria) this;
    }

    public Criteria andUiClientEqualTo(String value) {
      addCriterion("ui_client =", value, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientNotEqualTo(String value) {
      addCriterion("ui_client <>", value, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientGreaterThan(String value) {
      addCriterion("ui_client >", value, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientGreaterThanOrEqualTo(String value) {
      addCriterion("ui_client >=", value, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientLessThan(String value) {
      addCriterion("ui_client <", value, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientLessThanOrEqualTo(String value) {
      addCriterion("ui_client <=", value, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientLike(String value) {
      addCriterion("ui_client like", value, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientNotLike(String value) {
      addCriterion("ui_client not like", value, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientIn(List<String> values) {
      addCriterion("ui_client in", values, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientNotIn(List<String> values) {
      addCriterion("ui_client not in", values, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientBetween(String value1, String value2) {
      addCriterion("ui_client between", value1, value2, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiClientNotBetween(String value1, String value2) {
      addCriterion("ui_client not between", value1, value2, "uiClient");
      return (Criteria) this;
    }

    public Criteria andUiVersionIsNull() {
      addCriterion("ui_version is null");
      return (Criteria) this;
    }

    public Criteria andUiVersionIsNotNull() {
      addCriterion("ui_version is not null");
      return (Criteria) this;
    }

    public Criteria andUiVersionEqualTo(String value) {
      addCriterion("ui_version =", value, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionNotEqualTo(String value) {
      addCriterion("ui_version <>", value, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionGreaterThan(String value) {
      addCriterion("ui_version >", value, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionGreaterThanOrEqualTo(String value) {
      addCriterion("ui_version >=", value, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionLessThan(String value) {
      addCriterion("ui_version <", value, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionLessThanOrEqualTo(String value) {
      addCriterion("ui_version <=", value, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionLike(String value) {
      addCriterion("ui_version like", value, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionNotLike(String value) {
      addCriterion("ui_version not like", value, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionIn(List<String> values) {
      addCriterion("ui_version in", values, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionNotIn(List<String> values) {
      addCriterion("ui_version not in", values, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionBetween(String value1, String value2) {
      addCriterion("ui_version between", value1, value2, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andUiVersionNotBetween(String value1, String value2) {
      addCriterion("ui_version not between", value1, value2, "uiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionIsNull() {
      addCriterion("api_version is null");
      return (Criteria) this;
    }

    public Criteria andApiVersionIsNotNull() {
      addCriterion("api_version is not null");
      return (Criteria) this;
    }

    public Criteria andApiVersionEqualTo(String value) {
      addCriterion("api_version =", value, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionNotEqualTo(String value) {
      addCriterion("api_version <>", value, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionGreaterThan(String value) {
      addCriterion("api_version >", value, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionGreaterThanOrEqualTo(String value) {
      addCriterion("api_version >=", value, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionLessThan(String value) {
      addCriterion("api_version <", value, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionLessThanOrEqualTo(String value) {
      addCriterion("api_version <=", value, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionLike(String value) {
      addCriterion("api_version like", value, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionNotLike(String value) {
      addCriterion("api_version not like", value, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionIn(List<String> values) {
      addCriterion("api_version in", values, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionNotIn(List<String> values) {
      addCriterion("api_version not in", values, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionBetween(String value1, String value2) {
      addCriterion("api_version between", value1, value2, "apiVersion");
      return (Criteria) this;
    }

    public Criteria andApiVersionNotBetween(String value1, String value2) {
      addCriterion("api_version not between", value1, value2, "apiVersion");
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
