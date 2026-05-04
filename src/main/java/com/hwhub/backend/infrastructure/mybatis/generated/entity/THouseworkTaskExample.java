package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class THouseworkTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public THouseworkTaskExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andHouseworkTaskIdIsNull() {
            addCriterion("housework_task_id is null");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdIsNotNull() {
            addCriterion("housework_task_id is not null");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdEqualTo(Long value) {
            addCriterion("housework_task_id =", value, "houseworkTaskId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdNotEqualTo(Long value) {
            addCriterion("housework_task_id <>", value, "houseworkTaskId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdGreaterThan(Long value) {
            addCriterion("housework_task_id >", value, "houseworkTaskId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdGreaterThanOrEqualTo(Long value) {
            addCriterion("housework_task_id >=", value, "houseworkTaskId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdLessThan(Long value) {
            addCriterion("housework_task_id <", value, "houseworkTaskId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdLessThanOrEqualTo(Long value) {
            addCriterion("housework_task_id <=", value, "houseworkTaskId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdIn(List<Long> values) {
            addCriterion("housework_task_id in", values, "houseworkTaskId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdNotIn(List<Long> values) {
            addCriterion("housework_task_id not in", values, "houseworkTaskId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdBetween(Long value1, Long value2) {
            addCriterion("housework_task_id between", value1, value2, "houseworkTaskId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskIdNotBetween(Long value1, Long value2) {
            addCriterion("housework_task_id not between", value1, value2, "houseworkTaskId");
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

        public Criteria andTargetDateIsNull() {
            addCriterion("target_date is null");
            return (Criteria) this;
        }

        public Criteria andTargetDateIsNotNull() {
            addCriterion("target_date is not null");
            return (Criteria) this;
        }

        public Criteria andTargetDateEqualTo(Date value) {
            addCriterionForJDBCDate("target_date =", value, "targetDate");
            return (Criteria) this;
        }

        public Criteria andTargetDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("target_date <>", value, "targetDate");
            return (Criteria) this;
        }

        public Criteria andTargetDateGreaterThan(Date value) {
            addCriterionForJDBCDate("target_date >", value, "targetDate");
            return (Criteria) this;
        }

        public Criteria andTargetDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("target_date >=", value, "targetDate");
            return (Criteria) this;
        }

        public Criteria andTargetDateLessThan(Date value) {
            addCriterionForJDBCDate("target_date <", value, "targetDate");
            return (Criteria) this;
        }

        public Criteria andTargetDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("target_date <=", value, "targetDate");
            return (Criteria) this;
        }

        public Criteria andTargetDateIn(List<Date> values) {
            addCriterionForJDBCDate("target_date in", values, "targetDate");
            return (Criteria) this;
        }

        public Criteria andTargetDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("target_date not in", values, "targetDate");
            return (Criteria) this;
        }

        public Criteria andTargetDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("target_date between", value1, value2, "targetDate");
            return (Criteria) this;
        }

        public Criteria andTargetDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("target_date not between", value1, value2, "targetDate");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdIsNull() {
            addCriterion("assignee_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdIsNotNull() {
            addCriterion("assignee_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdEqualTo(Long value) {
            addCriterion("assignee_user_id =", value, "assigneeUserId");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdNotEqualTo(Long value) {
            addCriterion("assignee_user_id <>", value, "assigneeUserId");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdGreaterThan(Long value) {
            addCriterion("assignee_user_id >", value, "assigneeUserId");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("assignee_user_id >=", value, "assigneeUserId");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdLessThan(Long value) {
            addCriterion("assignee_user_id <", value, "assigneeUserId");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdLessThanOrEqualTo(Long value) {
            addCriterion("assignee_user_id <=", value, "assigneeUserId");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdIn(List<Long> values) {
            addCriterion("assignee_user_id in", values, "assigneeUserId");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdNotIn(List<Long> values) {
            addCriterion("assignee_user_id not in", values, "assigneeUserId");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdBetween(Long value1, Long value2) {
            addCriterion("assignee_user_id between", value1, value2, "assigneeUserId");
            return (Criteria) this;
        }

        public Criteria andAssigneeUserIdNotBetween(Long value1, Long value2) {
            addCriterion("assignee_user_id not between", value1, value2, "assigneeUserId");
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

        public Criteria andAssignReasonTypeIsNull() {
            addCriterion("assign_reason_type is null");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeIsNotNull() {
            addCriterion("assign_reason_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeEqualTo(String value) {
            addCriterion("assign_reason_type =", value, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeNotEqualTo(String value) {
            addCriterion("assign_reason_type <>", value, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeGreaterThan(String value) {
            addCriterion("assign_reason_type >", value, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeGreaterThanOrEqualTo(String value) {
            addCriterion("assign_reason_type >=", value, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeLessThan(String value) {
            addCriterion("assign_reason_type <", value, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeLessThanOrEqualTo(String value) {
            addCriterion("assign_reason_type <=", value, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeLike(String value) {
            addCriterion("assign_reason_type like", value, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeNotLike(String value) {
            addCriterion("assign_reason_type not like", value, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeIn(List<String> values) {
            addCriterion("assign_reason_type in", values, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeNotIn(List<String> values) {
            addCriterion("assign_reason_type not in", values, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeBetween(String value1, String value2) {
            addCriterion("assign_reason_type between", value1, value2, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andAssignReasonTypeNotBetween(String value1, String value2) {
            addCriterion("assign_reason_type not between", value1, value2, "assignReasonType");
            return (Criteria) this;
        }

        public Criteria andDoneAtIsNull() {
            addCriterion("done_at is null");
            return (Criteria) this;
        }

        public Criteria andDoneAtIsNotNull() {
            addCriterion("done_at is not null");
            return (Criteria) this;
        }

        public Criteria andDoneAtEqualTo(Date value) {
            addCriterionForJDBCDate("done_at =", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtNotEqualTo(Date value) {
            addCriterionForJDBCDate("done_at <>", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtGreaterThan(Date value) {
            addCriterionForJDBCDate("done_at >", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("done_at >=", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtLessThan(Date value) {
            addCriterionForJDBCDate("done_at <", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("done_at <=", value, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtIn(List<Date> values) {
            addCriterionForJDBCDate("done_at in", values, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtNotIn(List<Date> values) {
            addCriterionForJDBCDate("done_at not in", values, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("done_at between", value1, value2, "doneAt");
            return (Criteria) this;
        }

        public Criteria andDoneAtNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("done_at not between", value1, value2, "doneAt");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonIsNull() {
            addCriterion("skipped_reason is null");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonIsNotNull() {
            addCriterion("skipped_reason is not null");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonEqualTo(String value) {
            addCriterion("skipped_reason =", value, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonNotEqualTo(String value) {
            addCriterion("skipped_reason <>", value, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonGreaterThan(String value) {
            addCriterion("skipped_reason >", value, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonGreaterThanOrEqualTo(String value) {
            addCriterion("skipped_reason >=", value, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonLessThan(String value) {
            addCriterion("skipped_reason <", value, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonLessThanOrEqualTo(String value) {
            addCriterion("skipped_reason <=", value, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonLike(String value) {
            addCriterion("skipped_reason like", value, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonNotLike(String value) {
            addCriterion("skipped_reason not like", value, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonIn(List<String> values) {
            addCriterion("skipped_reason in", values, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonNotIn(List<String> values) {
            addCriterion("skipped_reason not in", values, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonBetween(String value1, String value2) {
            addCriterion("skipped_reason between", value1, value2, "skippedReason");
            return (Criteria) this;
        }

        public Criteria andSkippedReasonNotBetween(String value1, String value2) {
            addCriterion("skipped_reason not between", value1, value2, "skippedReason");
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