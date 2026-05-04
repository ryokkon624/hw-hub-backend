package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class THouseworkTaskAssignmentHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public THouseworkTaskAssignmentHistoryExample() {
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

        public Criteria andHouseworkTaskAssignmentHistoryIdIsNull() {
            addCriterion("housework_task_assignment_history_id is null");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdIsNotNull() {
            addCriterion("housework_task_assignment_history_id is not null");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdEqualTo(Long value) {
            addCriterion("housework_task_assignment_history_id =", value, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdNotEqualTo(Long value) {
            addCriterion("housework_task_assignment_history_id <>", value, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdGreaterThan(Long value) {
            addCriterion("housework_task_assignment_history_id >", value, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("housework_task_assignment_history_id >=", value, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdLessThan(Long value) {
            addCriterion("housework_task_assignment_history_id <", value, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdLessThanOrEqualTo(Long value) {
            addCriterion("housework_task_assignment_history_id <=", value, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdIn(List<Long> values) {
            addCriterion("housework_task_assignment_history_id in", values, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdNotIn(List<Long> values) {
            addCriterion("housework_task_assignment_history_id not in", values, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdBetween(Long value1, Long value2) {
            addCriterion("housework_task_assignment_history_id between", value1, value2, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTaskAssignmentHistoryIdNotBetween(Long value1, Long value2) {
            addCriterion("housework_task_assignment_history_id not between", value1, value2, "houseworkTaskAssignmentHistoryId");
            return (Criteria) this;
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

        public Criteria andFromAssigneeUserIdIsNull() {
            addCriterion("from_assignee_user_id is null");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdIsNotNull() {
            addCriterion("from_assignee_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdEqualTo(Long value) {
            addCriterion("from_assignee_user_id =", value, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdNotEqualTo(Long value) {
            addCriterion("from_assignee_user_id <>", value, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdGreaterThan(Long value) {
            addCriterion("from_assignee_user_id >", value, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_assignee_user_id >=", value, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdLessThan(Long value) {
            addCriterion("from_assignee_user_id <", value, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdLessThanOrEqualTo(Long value) {
            addCriterion("from_assignee_user_id <=", value, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdIn(List<Long> values) {
            addCriterion("from_assignee_user_id in", values, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdNotIn(List<Long> values) {
            addCriterion("from_assignee_user_id not in", values, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdBetween(Long value1, Long value2) {
            addCriterion("from_assignee_user_id between", value1, value2, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andFromAssigneeUserIdNotBetween(Long value1, Long value2) {
            addCriterion("from_assignee_user_id not between", value1, value2, "fromAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdIsNull() {
            addCriterion("to_assignee_user_id is null");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdIsNotNull() {
            addCriterion("to_assignee_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdEqualTo(Long value) {
            addCriterion("to_assignee_user_id =", value, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdNotEqualTo(Long value) {
            addCriterion("to_assignee_user_id <>", value, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdGreaterThan(Long value) {
            addCriterion("to_assignee_user_id >", value, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("to_assignee_user_id >=", value, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdLessThan(Long value) {
            addCriterion("to_assignee_user_id <", value, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdLessThanOrEqualTo(Long value) {
            addCriterion("to_assignee_user_id <=", value, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdIn(List<Long> values) {
            addCriterion("to_assignee_user_id in", values, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdNotIn(List<Long> values) {
            addCriterion("to_assignee_user_id not in", values, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdBetween(Long value1, Long value2) {
            addCriterion("to_assignee_user_id between", value1, value2, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andToAssigneeUserIdNotBetween(Long value1, Long value2) {
            addCriterion("to_assignee_user_id not between", value1, value2, "toAssigneeUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdIsNull() {
            addCriterion("operated_user_id is null");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdIsNotNull() {
            addCriterion("operated_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdEqualTo(Long value) {
            addCriterion("operated_user_id =", value, "operatedUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdNotEqualTo(Long value) {
            addCriterion("operated_user_id <>", value, "operatedUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdGreaterThan(Long value) {
            addCriterion("operated_user_id >", value, "operatedUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("operated_user_id >=", value, "operatedUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdLessThan(Long value) {
            addCriterion("operated_user_id <", value, "operatedUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdLessThanOrEqualTo(Long value) {
            addCriterion("operated_user_id <=", value, "operatedUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdIn(List<Long> values) {
            addCriterion("operated_user_id in", values, "operatedUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdNotIn(List<Long> values) {
            addCriterion("operated_user_id not in", values, "operatedUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdBetween(Long value1, Long value2) {
            addCriterion("operated_user_id between", value1, value2, "operatedUserId");
            return (Criteria) this;
        }

        public Criteria andOperatedUserIdNotBetween(Long value1, Long value2) {
            addCriterion("operated_user_id not between", value1, value2, "operatedUserId");
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

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andChangedAtIsNull() {
            addCriterion("changed_at is null");
            return (Criteria) this;
        }

        public Criteria andChangedAtIsNotNull() {
            addCriterion("changed_at is not null");
            return (Criteria) this;
        }

        public Criteria andChangedAtEqualTo(Date value) {
            addCriterion("changed_at =", value, "changedAt");
            return (Criteria) this;
        }

        public Criteria andChangedAtNotEqualTo(Date value) {
            addCriterion("changed_at <>", value, "changedAt");
            return (Criteria) this;
        }

        public Criteria andChangedAtGreaterThan(Date value) {
            addCriterion("changed_at >", value, "changedAt");
            return (Criteria) this;
        }

        public Criteria andChangedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("changed_at >=", value, "changedAt");
            return (Criteria) this;
        }

        public Criteria andChangedAtLessThan(Date value) {
            addCriterion("changed_at <", value, "changedAt");
            return (Criteria) this;
        }

        public Criteria andChangedAtLessThanOrEqualTo(Date value) {
            addCriterion("changed_at <=", value, "changedAt");
            return (Criteria) this;
        }

        public Criteria andChangedAtIn(List<Date> values) {
            addCriterion("changed_at in", values, "changedAt");
            return (Criteria) this;
        }

        public Criteria andChangedAtNotIn(List<Date> values) {
            addCriterion("changed_at not in", values, "changedAt");
            return (Criteria) this;
        }

        public Criteria andChangedAtBetween(Date value1, Date value2) {
            addCriterion("changed_at between", value1, value2, "changedAt");
            return (Criteria) this;
        }

        public Criteria andChangedAtNotBetween(Date value1, Date value2) {
            addCriterion("changed_at not between", value1, value2, "changedAt");
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