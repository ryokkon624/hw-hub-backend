package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TShoppingItemExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public TShoppingItemExample() {
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

    public Criteria andShoppingItemIdIsNull() {
      addCriterion("shopping_item_id is null");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdIsNotNull() {
      addCriterion("shopping_item_id is not null");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdEqualTo(Long value) {
      addCriterion("shopping_item_id =", value, "shoppingItemId");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdNotEqualTo(Long value) {
      addCriterion("shopping_item_id <>", value, "shoppingItemId");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdGreaterThan(Long value) {
      addCriterion("shopping_item_id >", value, "shoppingItemId");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdGreaterThanOrEqualTo(Long value) {
      addCriterion("shopping_item_id >=", value, "shoppingItemId");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdLessThan(Long value) {
      addCriterion("shopping_item_id <", value, "shoppingItemId");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdLessThanOrEqualTo(Long value) {
      addCriterion("shopping_item_id <=", value, "shoppingItemId");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdIn(List<Long> values) {
      addCriterion("shopping_item_id in", values, "shoppingItemId");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdNotIn(List<Long> values) {
      addCriterion("shopping_item_id not in", values, "shoppingItemId");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdBetween(Long value1, Long value2) {
      addCriterion("shopping_item_id between", value1, value2, "shoppingItemId");
      return (Criteria) this;
    }

    public Criteria andShoppingItemIdNotBetween(Long value1, Long value2) {
      addCriterion("shopping_item_id not between", value1, value2, "shoppingItemId");
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

    public Criteria andMemoIsNull() {
      addCriterion("memo is null");
      return (Criteria) this;
    }

    public Criteria andMemoIsNotNull() {
      addCriterion("memo is not null");
      return (Criteria) this;
    }

    public Criteria andMemoEqualTo(String value) {
      addCriterion("memo =", value, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoNotEqualTo(String value) {
      addCriterion("memo <>", value, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoGreaterThan(String value) {
      addCriterion("memo >", value, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoGreaterThanOrEqualTo(String value) {
      addCriterion("memo >=", value, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoLessThan(String value) {
      addCriterion("memo <", value, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoLessThanOrEqualTo(String value) {
      addCriterion("memo <=", value, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoLike(String value) {
      addCriterion("memo like", value, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoNotLike(String value) {
      addCriterion("memo not like", value, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoIn(List<String> values) {
      addCriterion("memo in", values, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoNotIn(List<String> values) {
      addCriterion("memo not in", values, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoBetween(String value1, String value2) {
      addCriterion("memo between", value1, value2, "memo");
      return (Criteria) this;
    }

    public Criteria andMemoNotBetween(String value1, String value2) {
      addCriterion("memo not between", value1, value2, "memo");
      return (Criteria) this;
    }

    public Criteria andStoreTypeIsNull() {
      addCriterion("store_type is null");
      return (Criteria) this;
    }

    public Criteria andStoreTypeIsNotNull() {
      addCriterion("store_type is not null");
      return (Criteria) this;
    }

    public Criteria andStoreTypeEqualTo(String value) {
      addCriterion("store_type =", value, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeNotEqualTo(String value) {
      addCriterion("store_type <>", value, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeGreaterThan(String value) {
      addCriterion("store_type >", value, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeGreaterThanOrEqualTo(String value) {
      addCriterion("store_type >=", value, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeLessThan(String value) {
      addCriterion("store_type <", value, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeLessThanOrEqualTo(String value) {
      addCriterion("store_type <=", value, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeLike(String value) {
      addCriterion("store_type like", value, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeNotLike(String value) {
      addCriterion("store_type not like", value, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeIn(List<String> values) {
      addCriterion("store_type in", values, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeNotIn(List<String> values) {
      addCriterion("store_type not in", values, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeBetween(String value1, String value2) {
      addCriterion("store_type between", value1, value2, "storeType");
      return (Criteria) this;
    }

    public Criteria andStoreTypeNotBetween(String value1, String value2) {
      addCriterion("store_type not between", value1, value2, "storeType");
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

    public Criteria andFavoriteIsNull() {
      addCriterion("favorite is null");
      return (Criteria) this;
    }

    public Criteria andFavoriteIsNotNull() {
      addCriterion("favorite is not null");
      return (Criteria) this;
    }

    public Criteria andFavoriteEqualTo(String value) {
      addCriterion("favorite =", value, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteNotEqualTo(String value) {
      addCriterion("favorite <>", value, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteGreaterThan(String value) {
      addCriterion("favorite >", value, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteGreaterThanOrEqualTo(String value) {
      addCriterion("favorite >=", value, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteLessThan(String value) {
      addCriterion("favorite <", value, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteLessThanOrEqualTo(String value) {
      addCriterion("favorite <=", value, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteLike(String value) {
      addCriterion("favorite like", value, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteNotLike(String value) {
      addCriterion("favorite not like", value, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteIn(List<String> values) {
      addCriterion("favorite in", values, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteNotIn(List<String> values) {
      addCriterion("favorite not in", values, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteBetween(String value1, String value2) {
      addCriterion("favorite between", value1, value2, "favorite");
      return (Criteria) this;
    }

    public Criteria andFavoriteNotBetween(String value1, String value2) {
      addCriterion("favorite not between", value1, value2, "favorite");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtIsNull() {
      addCriterion("purchased_at is null");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtIsNotNull() {
      addCriterion("purchased_at is not null");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtEqualTo(Date value) {
      addCriterionForJDBCDate("purchased_at =", value, "purchasedAt");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtNotEqualTo(Date value) {
      addCriterionForJDBCDate("purchased_at <>", value, "purchasedAt");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtGreaterThan(Date value) {
      addCriterionForJDBCDate("purchased_at >", value, "purchasedAt");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("purchased_at >=", value, "purchasedAt");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtLessThan(Date value) {
      addCriterionForJDBCDate("purchased_at <", value, "purchasedAt");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("purchased_at <=", value, "purchasedAt");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtIn(List<Date> values) {
      addCriterionForJDBCDate("purchased_at in", values, "purchasedAt");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtNotIn(List<Date> values) {
      addCriterionForJDBCDate("purchased_at not in", values, "purchasedAt");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("purchased_at between", value1, value2, "purchasedAt");
      return (Criteria) this;
    }

    public Criteria andPurchasedAtNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("purchased_at not between", value1, value2, "purchasedAt");
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
