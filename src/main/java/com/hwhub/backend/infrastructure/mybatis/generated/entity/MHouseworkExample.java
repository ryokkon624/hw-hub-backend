package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MHouseworkExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public MHouseworkExample() {
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

    public Criteria andHouseworkIdIsNull() {
      addCriterion("housework_id is null");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdIsNotNull() {
      addCriterion("housework_id is not null");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdEqualTo(Long value) {
      addCriterion("housework_id =", value, "houseworkId");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdNotEqualTo(Long value) {
      addCriterion("housework_id <>", value, "houseworkId");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdGreaterThan(Long value) {
      addCriterion("housework_id >", value, "houseworkId");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdGreaterThanOrEqualTo(Long value) {
      addCriterion("housework_id >=", value, "houseworkId");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdLessThan(Long value) {
      addCriterion("housework_id <", value, "houseworkId");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdLessThanOrEqualTo(Long value) {
      addCriterion("housework_id <=", value, "houseworkId");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdIn(List<Long> values) {
      addCriterion("housework_id in", values, "houseworkId");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdNotIn(List<Long> values) {
      addCriterion("housework_id not in", values, "houseworkId");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdBetween(Long value1, Long value2) {
      addCriterion("housework_id between", value1, value2, "houseworkId");
      return (Criteria) this;
    }

    public Criteria andHouseworkIdNotBetween(Long value1, Long value2) {
      addCriterion("housework_id not between", value1, value2, "houseworkId");
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

    public Criteria andDescriptionIsNull() {
      addCriterion("description is null");
      return (Criteria) this;
    }

    public Criteria andDescriptionIsNotNull() {
      addCriterion("description is not null");
      return (Criteria) this;
    }

    public Criteria andDescriptionEqualTo(String value) {
      addCriterion("description =", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionNotEqualTo(String value) {
      addCriterion("description <>", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionGreaterThan(String value) {
      addCriterion("description >", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
      addCriterion("description >=", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionLessThan(String value) {
      addCriterion("description <", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionLessThanOrEqualTo(String value) {
      addCriterion("description <=", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionLike(String value) {
      addCriterion("description like", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionNotLike(String value) {
      addCriterion("description not like", value, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionIn(List<String> values) {
      addCriterion("description in", values, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionNotIn(List<String> values) {
      addCriterion("description not in", values, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionBetween(String value1, String value2) {
      addCriterion("description between", value1, value2, "description");
      return (Criteria) this;
    }

    public Criteria andDescriptionNotBetween(String value1, String value2) {
      addCriterion("description not between", value1, value2, "description");
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

    public Criteria andRecurrenceTypeIsNull() {
      addCriterion("recurrence_type is null");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeIsNotNull() {
      addCriterion("recurrence_type is not null");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeEqualTo(String value) {
      addCriterion("recurrence_type =", value, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeNotEqualTo(String value) {
      addCriterion("recurrence_type <>", value, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeGreaterThan(String value) {
      addCriterion("recurrence_type >", value, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeGreaterThanOrEqualTo(String value) {
      addCriterion("recurrence_type >=", value, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeLessThan(String value) {
      addCriterion("recurrence_type <", value, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeLessThanOrEqualTo(String value) {
      addCriterion("recurrence_type <=", value, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeLike(String value) {
      addCriterion("recurrence_type like", value, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeNotLike(String value) {
      addCriterion("recurrence_type not like", value, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeIn(List<String> values) {
      addCriterion("recurrence_type in", values, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeNotIn(List<String> values) {
      addCriterion("recurrence_type not in", values, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeBetween(String value1, String value2) {
      addCriterion("recurrence_type between", value1, value2, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andRecurrenceTypeNotBetween(String value1, String value2) {
      addCriterion("recurrence_type not between", value1, value2, "recurrenceType");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysIsNull() {
      addCriterion("weekly_days is null");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysIsNotNull() {
      addCriterion("weekly_days is not null");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysEqualTo(Integer value) {
      addCriterion("weekly_days =", value, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysNotEqualTo(Integer value) {
      addCriterion("weekly_days <>", value, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysGreaterThan(Integer value) {
      addCriterion("weekly_days >", value, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysGreaterThanOrEqualTo(Integer value) {
      addCriterion("weekly_days >=", value, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysLessThan(Integer value) {
      addCriterion("weekly_days <", value, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysLessThanOrEqualTo(Integer value) {
      addCriterion("weekly_days <=", value, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysIn(List<Integer> values) {
      addCriterion("weekly_days in", values, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysNotIn(List<Integer> values) {
      addCriterion("weekly_days not in", values, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysBetween(Integer value1, Integer value2) {
      addCriterion("weekly_days between", value1, value2, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andWeeklyDaysNotBetween(Integer value1, Integer value2) {
      addCriterion("weekly_days not between", value1, value2, "weeklyDays");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthIsNull() {
      addCriterion("day_of_month is null");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthIsNotNull() {
      addCriterion("day_of_month is not null");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthEqualTo(Integer value) {
      addCriterion("day_of_month =", value, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthNotEqualTo(Integer value) {
      addCriterion("day_of_month <>", value, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthGreaterThan(Integer value) {
      addCriterion("day_of_month >", value, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthGreaterThanOrEqualTo(Integer value) {
      addCriterion("day_of_month >=", value, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthLessThan(Integer value) {
      addCriterion("day_of_month <", value, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthLessThanOrEqualTo(Integer value) {
      addCriterion("day_of_month <=", value, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthIn(List<Integer> values) {
      addCriterion("day_of_month in", values, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthNotIn(List<Integer> values) {
      addCriterion("day_of_month not in", values, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthBetween(Integer value1, Integer value2) {
      addCriterion("day_of_month between", value1, value2, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andDayOfMonthNotBetween(Integer value1, Integer value2) {
      addCriterion("day_of_month not between", value1, value2, "dayOfMonth");
      return (Criteria) this;
    }

    public Criteria andNthWeekIsNull() {
      addCriterion("nth_week is null");
      return (Criteria) this;
    }

    public Criteria andNthWeekIsNotNull() {
      addCriterion("nth_week is not null");
      return (Criteria) this;
    }

    public Criteria andNthWeekEqualTo(Integer value) {
      addCriterion("nth_week =", value, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andNthWeekNotEqualTo(Integer value) {
      addCriterion("nth_week <>", value, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andNthWeekGreaterThan(Integer value) {
      addCriterion("nth_week >", value, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andNthWeekGreaterThanOrEqualTo(Integer value) {
      addCriterion("nth_week >=", value, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andNthWeekLessThan(Integer value) {
      addCriterion("nth_week <", value, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andNthWeekLessThanOrEqualTo(Integer value) {
      addCriterion("nth_week <=", value, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andNthWeekIn(List<Integer> values) {
      addCriterion("nth_week in", values, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andNthWeekNotIn(List<Integer> values) {
      addCriterion("nth_week not in", values, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andNthWeekBetween(Integer value1, Integer value2) {
      addCriterion("nth_week between", value1, value2, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andNthWeekNotBetween(Integer value1, Integer value2) {
      addCriterion("nth_week not between", value1, value2, "nthWeek");
      return (Criteria) this;
    }

    public Criteria andWeekdayIsNull() {
      addCriterion("weekday is null");
      return (Criteria) this;
    }

    public Criteria andWeekdayIsNotNull() {
      addCriterion("weekday is not null");
      return (Criteria) this;
    }

    public Criteria andWeekdayEqualTo(Integer value) {
      addCriterion("weekday =", value, "weekday");
      return (Criteria) this;
    }

    public Criteria andWeekdayNotEqualTo(Integer value) {
      addCriterion("weekday <>", value, "weekday");
      return (Criteria) this;
    }

    public Criteria andWeekdayGreaterThan(Integer value) {
      addCriterion("weekday >", value, "weekday");
      return (Criteria) this;
    }

    public Criteria andWeekdayGreaterThanOrEqualTo(Integer value) {
      addCriterion("weekday >=", value, "weekday");
      return (Criteria) this;
    }

    public Criteria andWeekdayLessThan(Integer value) {
      addCriterion("weekday <", value, "weekday");
      return (Criteria) this;
    }

    public Criteria andWeekdayLessThanOrEqualTo(Integer value) {
      addCriterion("weekday <=", value, "weekday");
      return (Criteria) this;
    }

    public Criteria andWeekdayIn(List<Integer> values) {
      addCriterion("weekday in", values, "weekday");
      return (Criteria) this;
    }

    public Criteria andWeekdayNotIn(List<Integer> values) {
      addCriterion("weekday not in", values, "weekday");
      return (Criteria) this;
    }

    public Criteria andWeekdayBetween(Integer value1, Integer value2) {
      addCriterion("weekday between", value1, value2, "weekday");
      return (Criteria) this;
    }

    public Criteria andWeekdayNotBetween(Integer value1, Integer value2) {
      addCriterion("weekday not between", value1, value2, "weekday");
      return (Criteria) this;
    }

    public Criteria andStartDateIsNull() {
      addCriterion("start_date is null");
      return (Criteria) this;
    }

    public Criteria andStartDateIsNotNull() {
      addCriterion("start_date is not null");
      return (Criteria) this;
    }

    public Criteria andStartDateEqualTo(Date value) {
      addCriterionForJDBCDate("start_date =", value, "startDate");
      return (Criteria) this;
    }

    public Criteria andStartDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("start_date <>", value, "startDate");
      return (Criteria) this;
    }

    public Criteria andStartDateGreaterThan(Date value) {
      addCriterionForJDBCDate("start_date >", value, "startDate");
      return (Criteria) this;
    }

    public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("start_date >=", value, "startDate");
      return (Criteria) this;
    }

    public Criteria andStartDateLessThan(Date value) {
      addCriterionForJDBCDate("start_date <", value, "startDate");
      return (Criteria) this;
    }

    public Criteria andStartDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("start_date <=", value, "startDate");
      return (Criteria) this;
    }

    public Criteria andStartDateIn(List<Date> values) {
      addCriterionForJDBCDate("start_date in", values, "startDate");
      return (Criteria) this;
    }

    public Criteria andStartDateNotIn(List<Date> values) {
      addCriterionForJDBCDate("start_date not in", values, "startDate");
      return (Criteria) this;
    }

    public Criteria andStartDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("start_date between", value1, value2, "startDate");
      return (Criteria) this;
    }

    public Criteria andStartDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("start_date not between", value1, value2, "startDate");
      return (Criteria) this;
    }

    public Criteria andEndDateIsNull() {
      addCriterion("end_date is null");
      return (Criteria) this;
    }

    public Criteria andEndDateIsNotNull() {
      addCriterion("end_date is not null");
      return (Criteria) this;
    }

    public Criteria andEndDateEqualTo(Date value) {
      addCriterionForJDBCDate("end_date =", value, "endDate");
      return (Criteria) this;
    }

    public Criteria andEndDateNotEqualTo(Date value) {
      addCriterionForJDBCDate("end_date <>", value, "endDate");
      return (Criteria) this;
    }

    public Criteria andEndDateGreaterThan(Date value) {
      addCriterionForJDBCDate("end_date >", value, "endDate");
      return (Criteria) this;
    }

    public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("end_date >=", value, "endDate");
      return (Criteria) this;
    }

    public Criteria andEndDateLessThan(Date value) {
      addCriterionForJDBCDate("end_date <", value, "endDate");
      return (Criteria) this;
    }

    public Criteria andEndDateLessThanOrEqualTo(Date value) {
      addCriterionForJDBCDate("end_date <=", value, "endDate");
      return (Criteria) this;
    }

    public Criteria andEndDateIn(List<Date> values) {
      addCriterionForJDBCDate("end_date in", values, "endDate");
      return (Criteria) this;
    }

    public Criteria andEndDateNotIn(List<Date> values) {
      addCriterionForJDBCDate("end_date not in", values, "endDate");
      return (Criteria) this;
    }

    public Criteria andEndDateBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
      return (Criteria) this;
    }

    public Criteria andEndDateNotBetween(Date value1, Date value2) {
      addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdIsNull() {
      addCriterion("default_assignee_user_id is null");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdIsNotNull() {
      addCriterion("default_assignee_user_id is not null");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdEqualTo(Long value) {
      addCriterion("default_assignee_user_id =", value, "defaultAssigneeUserId");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdNotEqualTo(Long value) {
      addCriterion("default_assignee_user_id <>", value, "defaultAssigneeUserId");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdGreaterThan(Long value) {
      addCriterion("default_assignee_user_id >", value, "defaultAssigneeUserId");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdGreaterThanOrEqualTo(Long value) {
      addCriterion("default_assignee_user_id >=", value, "defaultAssigneeUserId");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdLessThan(Long value) {
      addCriterion("default_assignee_user_id <", value, "defaultAssigneeUserId");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdLessThanOrEqualTo(Long value) {
      addCriterion("default_assignee_user_id <=", value, "defaultAssigneeUserId");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdIn(List<Long> values) {
      addCriterion("default_assignee_user_id in", values, "defaultAssigneeUserId");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdNotIn(List<Long> values) {
      addCriterion("default_assignee_user_id not in", values, "defaultAssigneeUserId");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdBetween(Long value1, Long value2) {
      addCriterion("default_assignee_user_id between", value1, value2, "defaultAssigneeUserId");
      return (Criteria) this;
    }

    public Criteria andDefaultAssigneeUserIdNotBetween(Long value1, Long value2) {
      addCriterion("default_assignee_user_id not between", value1, value2, "defaultAssigneeUserId");
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
