package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MCodeExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public MCodeExample() {
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

    public Criteria andCodeTypeIsNull() {
      addCriterion("code_type is null");
      return (Criteria) this;
    }

    public Criteria andCodeTypeIsNotNull() {
      addCriterion("code_type is not null");
      return (Criteria) this;
    }

    public Criteria andCodeTypeEqualTo(String value) {
      addCriterion("code_type =", value, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNotEqualTo(String value) {
      addCriterion("code_type <>", value, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeGreaterThan(String value) {
      addCriterion("code_type >", value, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeGreaterThanOrEqualTo(String value) {
      addCriterion("code_type >=", value, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeLessThan(String value) {
      addCriterion("code_type <", value, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeLessThanOrEqualTo(String value) {
      addCriterion("code_type <=", value, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeLike(String value) {
      addCriterion("code_type like", value, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNotLike(String value) {
      addCriterion("code_type not like", value, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeIn(List<String> values) {
      addCriterion("code_type in", values, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNotIn(List<String> values) {
      addCriterion("code_type not in", values, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeBetween(String value1, String value2) {
      addCriterion("code_type between", value1, value2, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNotBetween(String value1, String value2) {
      addCriterion("code_type not between", value1, value2, "codeType");
      return (Criteria) this;
    }

    public Criteria andCodeValueIsNull() {
      addCriterion("code_value is null");
      return (Criteria) this;
    }

    public Criteria andCodeValueIsNotNull() {
      addCriterion("code_value is not null");
      return (Criteria) this;
    }

    public Criteria andCodeValueEqualTo(String value) {
      addCriterion("code_value =", value, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueNotEqualTo(String value) {
      addCriterion("code_value <>", value, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueGreaterThan(String value) {
      addCriterion("code_value >", value, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueGreaterThanOrEqualTo(String value) {
      addCriterion("code_value >=", value, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueLessThan(String value) {
      addCriterion("code_value <", value, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueLessThanOrEqualTo(String value) {
      addCriterion("code_value <=", value, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueLike(String value) {
      addCriterion("code_value like", value, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueNotLike(String value) {
      addCriterion("code_value not like", value, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueIn(List<String> values) {
      addCriterion("code_value in", values, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueNotIn(List<String> values) {
      addCriterion("code_value not in", values, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueBetween(String value1, String value2) {
      addCriterion("code_value between", value1, value2, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeValueNotBetween(String value1, String value2) {
      addCriterion("code_value not between", value1, value2, "codeValue");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameIsNull() {
      addCriterion("code_type_name is null");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameIsNotNull() {
      addCriterion("code_type_name is not null");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEqualTo(String value) {
      addCriterion("code_type_name =", value, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameNotEqualTo(String value) {
      addCriterion("code_type_name <>", value, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameGreaterThan(String value) {
      addCriterion("code_type_name >", value, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameGreaterThanOrEqualTo(String value) {
      addCriterion("code_type_name >=", value, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameLessThan(String value) {
      addCriterion("code_type_name <", value, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameLessThanOrEqualTo(String value) {
      addCriterion("code_type_name <=", value, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameLike(String value) {
      addCriterion("code_type_name like", value, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameNotLike(String value) {
      addCriterion("code_type_name not like", value, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameIn(List<String> values) {
      addCriterion("code_type_name in", values, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameNotIn(List<String> values) {
      addCriterion("code_type_name not in", values, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameBetween(String value1, String value2) {
      addCriterion("code_type_name between", value1, value2, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameNotBetween(String value1, String value2) {
      addCriterion("code_type_name not between", value1, value2, "codeTypeName");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnIsNull() {
      addCriterion("code_type_name_en is null");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnIsNotNull() {
      addCriterion("code_type_name_en is not null");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnEqualTo(String value) {
      addCriterion("code_type_name_en =", value, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnNotEqualTo(String value) {
      addCriterion("code_type_name_en <>", value, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnGreaterThan(String value) {
      addCriterion("code_type_name_en >", value, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnGreaterThanOrEqualTo(String value) {
      addCriterion("code_type_name_en >=", value, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnLessThan(String value) {
      addCriterion("code_type_name_en <", value, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnLessThanOrEqualTo(String value) {
      addCriterion("code_type_name_en <=", value, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnLike(String value) {
      addCriterion("code_type_name_en like", value, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnNotLike(String value) {
      addCriterion("code_type_name_en not like", value, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnIn(List<String> values) {
      addCriterion("code_type_name_en in", values, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnNotIn(List<String> values) {
      addCriterion("code_type_name_en not in", values, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnBetween(String value1, String value2) {
      addCriterion("code_type_name_en between", value1, value2, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andCodeTypeNameEnNotBetween(String value1, String value2) {
      addCriterion("code_type_name_en not between", value1, value2, "codeTypeNameEn");
      return (Criteria) this;
    }

    public Criteria andNameIsNull() {
      addCriterion("`name` is null");
      return (Criteria) this;
    }

    public Criteria andNameIsNotNull() {
      addCriterion("`name` is not null");
      return (Criteria) this;
    }

    public Criteria andNameEqualTo(String value) {
      addCriterion("`name` =", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameNotEqualTo(String value) {
      addCriterion("`name` <>", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameGreaterThan(String value) {
      addCriterion("`name` >", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameGreaterThanOrEqualTo(String value) {
      addCriterion("`name` >=", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameLessThan(String value) {
      addCriterion("`name` <", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameLessThanOrEqualTo(String value) {
      addCriterion("`name` <=", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameLike(String value) {
      addCriterion("`name` like", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameNotLike(String value) {
      addCriterion("`name` not like", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameIn(List<String> values) {
      addCriterion("`name` in", values, "name");
      return (Criteria) this;
    }

    public Criteria andNameNotIn(List<String> values) {
      addCriterion("`name` not in", values, "name");
      return (Criteria) this;
    }

    public Criteria andNameBetween(String value1, String value2) {
      addCriterion("`name` between", value1, value2, "name");
      return (Criteria) this;
    }

    public Criteria andNameNotBetween(String value1, String value2) {
      addCriterion("`name` not between", value1, value2, "name");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaIsNull() {
      addCriterion("display_name_ja is null");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaIsNotNull() {
      addCriterion("display_name_ja is not null");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaEqualTo(String value) {
      addCriterion("display_name_ja =", value, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaNotEqualTo(String value) {
      addCriterion("display_name_ja <>", value, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaGreaterThan(String value) {
      addCriterion("display_name_ja >", value, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaGreaterThanOrEqualTo(String value) {
      addCriterion("display_name_ja >=", value, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaLessThan(String value) {
      addCriterion("display_name_ja <", value, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaLessThanOrEqualTo(String value) {
      addCriterion("display_name_ja <=", value, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaLike(String value) {
      addCriterion("display_name_ja like", value, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaNotLike(String value) {
      addCriterion("display_name_ja not like", value, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaIn(List<String> values) {
      addCriterion("display_name_ja in", values, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaNotIn(List<String> values) {
      addCriterion("display_name_ja not in", values, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaBetween(String value1, String value2) {
      addCriterion("display_name_ja between", value1, value2, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameJaNotBetween(String value1, String value2) {
      addCriterion("display_name_ja not between", value1, value2, "displayNameJa");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnIsNull() {
      addCriterion("display_name_en is null");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnIsNotNull() {
      addCriterion("display_name_en is not null");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnEqualTo(String value) {
      addCriterion("display_name_en =", value, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnNotEqualTo(String value) {
      addCriterion("display_name_en <>", value, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnGreaterThan(String value) {
      addCriterion("display_name_en >", value, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnGreaterThanOrEqualTo(String value) {
      addCriterion("display_name_en >=", value, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnLessThan(String value) {
      addCriterion("display_name_en <", value, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnLessThanOrEqualTo(String value) {
      addCriterion("display_name_en <=", value, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnLike(String value) {
      addCriterion("display_name_en like", value, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnNotLike(String value) {
      addCriterion("display_name_en not like", value, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnIn(List<String> values) {
      addCriterion("display_name_en in", values, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnNotIn(List<String> values) {
      addCriterion("display_name_en not in", values, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnBetween(String value1, String value2) {
      addCriterion("display_name_en between", value1, value2, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEnNotBetween(String value1, String value2) {
      addCriterion("display_name_en not between", value1, value2, "displayNameEn");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsIsNull() {
      addCriterion("display_name_es is null");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsIsNotNull() {
      addCriterion("display_name_es is not null");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsEqualTo(String value) {
      addCriterion("display_name_es =", value, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsNotEqualTo(String value) {
      addCriterion("display_name_es <>", value, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsGreaterThan(String value) {
      addCriterion("display_name_es >", value, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsGreaterThanOrEqualTo(String value) {
      addCriterion("display_name_es >=", value, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsLessThan(String value) {
      addCriterion("display_name_es <", value, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsLessThanOrEqualTo(String value) {
      addCriterion("display_name_es <=", value, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsLike(String value) {
      addCriterion("display_name_es like", value, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsNotLike(String value) {
      addCriterion("display_name_es not like", value, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsIn(List<String> values) {
      addCriterion("display_name_es in", values, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsNotIn(List<String> values) {
      addCriterion("display_name_es not in", values, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsBetween(String value1, String value2) {
      addCriterion("display_name_es between", value1, value2, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andDisplayNameEsNotBetween(String value1, String value2) {
      addCriterion("display_name_es not between", value1, value2, "displayNameEs");
      return (Criteria) this;
    }

    public Criteria andRemarksIsNull() {
      addCriterion("remarks is null");
      return (Criteria) this;
    }

    public Criteria andRemarksIsNotNull() {
      addCriterion("remarks is not null");
      return (Criteria) this;
    }

    public Criteria andRemarksEqualTo(String value) {
      addCriterion("remarks =", value, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksNotEqualTo(String value) {
      addCriterion("remarks <>", value, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksGreaterThan(String value) {
      addCriterion("remarks >", value, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksGreaterThanOrEqualTo(String value) {
      addCriterion("remarks >=", value, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksLessThan(String value) {
      addCriterion("remarks <", value, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksLessThanOrEqualTo(String value) {
      addCriterion("remarks <=", value, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksLike(String value) {
      addCriterion("remarks like", value, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksNotLike(String value) {
      addCriterion("remarks not like", value, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksIn(List<String> values) {
      addCriterion("remarks in", values, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksNotIn(List<String> values) {
      addCriterion("remarks not in", values, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksBetween(String value1, String value2) {
      addCriterion("remarks between", value1, value2, "remarks");
      return (Criteria) this;
    }

    public Criteria andRemarksNotBetween(String value1, String value2) {
      addCriterion("remarks not between", value1, value2, "remarks");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderIsNull() {
      addCriterion("display_order is null");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderIsNotNull() {
      addCriterion("display_order is not null");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderEqualTo(String value) {
      addCriterion("display_order =", value, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderNotEqualTo(String value) {
      addCriterion("display_order <>", value, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderGreaterThan(String value) {
      addCriterion("display_order >", value, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderGreaterThanOrEqualTo(String value) {
      addCriterion("display_order >=", value, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderLessThan(String value) {
      addCriterion("display_order <", value, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderLessThanOrEqualTo(String value) {
      addCriterion("display_order <=", value, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderLike(String value) {
      addCriterion("display_order like", value, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderNotLike(String value) {
      addCriterion("display_order not like", value, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderIn(List<String> values) {
      addCriterion("display_order in", values, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderNotIn(List<String> values) {
      addCriterion("display_order not in", values, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderBetween(String value1, String value2) {
      addCriterion("display_order between", value1, value2, "displayOrder");
      return (Criteria) this;
    }

    public Criteria andDisplayOrderNotBetween(String value1, String value2) {
      addCriterion("display_order not between", value1, value2, "displayOrder");
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
