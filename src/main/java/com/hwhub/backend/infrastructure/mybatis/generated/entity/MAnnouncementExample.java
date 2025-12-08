package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MAnnouncementExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MAnnouncementExample() {
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

        public Criteria andAnnouncementIdIsNull() {
            addCriterion("announcement_id is null");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdIsNotNull() {
            addCriterion("announcement_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdEqualTo(Long value) {
            addCriterion("announcement_id =", value, "announcementId");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdNotEqualTo(Long value) {
            addCriterion("announcement_id <>", value, "announcementId");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdGreaterThan(Long value) {
            addCriterion("announcement_id >", value, "announcementId");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdGreaterThanOrEqualTo(Long value) {
            addCriterion("announcement_id >=", value, "announcementId");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdLessThan(Long value) {
            addCriterion("announcement_id <", value, "announcementId");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdLessThanOrEqualTo(Long value) {
            addCriterion("announcement_id <=", value, "announcementId");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdIn(List<Long> values) {
            addCriterion("announcement_id in", values, "announcementId");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdNotIn(List<Long> values) {
            addCriterion("announcement_id not in", values, "announcementId");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdBetween(Long value1, Long value2) {
            addCriterion("announcement_id between", value1, value2, "announcementId");
            return (Criteria) this;
        }

        public Criteria andAnnouncementIdNotBetween(Long value1, Long value2) {
            addCriterion("announcement_id not between", value1, value2, "announcementId");
            return (Criteria) this;
        }

        public Criteria andTitleJaIsNull() {
            addCriterion("title_ja is null");
            return (Criteria) this;
        }

        public Criteria andTitleJaIsNotNull() {
            addCriterion("title_ja is not null");
            return (Criteria) this;
        }

        public Criteria andTitleJaEqualTo(String value) {
            addCriterion("title_ja =", value, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaNotEqualTo(String value) {
            addCriterion("title_ja <>", value, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaGreaterThan(String value) {
            addCriterion("title_ja >", value, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaGreaterThanOrEqualTo(String value) {
            addCriterion("title_ja >=", value, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaLessThan(String value) {
            addCriterion("title_ja <", value, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaLessThanOrEqualTo(String value) {
            addCriterion("title_ja <=", value, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaLike(String value) {
            addCriterion("title_ja like", value, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaNotLike(String value) {
            addCriterion("title_ja not like", value, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaIn(List<String> values) {
            addCriterion("title_ja in", values, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaNotIn(List<String> values) {
            addCriterion("title_ja not in", values, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaBetween(String value1, String value2) {
            addCriterion("title_ja between", value1, value2, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleJaNotBetween(String value1, String value2) {
            addCriterion("title_ja not between", value1, value2, "titleJa");
            return (Criteria) this;
        }

        public Criteria andTitleEnIsNull() {
            addCriterion("title_en is null");
            return (Criteria) this;
        }

        public Criteria andTitleEnIsNotNull() {
            addCriterion("title_en is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEnEqualTo(String value) {
            addCriterion("title_en =", value, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnNotEqualTo(String value) {
            addCriterion("title_en <>", value, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnGreaterThan(String value) {
            addCriterion("title_en >", value, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnGreaterThanOrEqualTo(String value) {
            addCriterion("title_en >=", value, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnLessThan(String value) {
            addCriterion("title_en <", value, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnLessThanOrEqualTo(String value) {
            addCriterion("title_en <=", value, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnLike(String value) {
            addCriterion("title_en like", value, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnNotLike(String value) {
            addCriterion("title_en not like", value, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnIn(List<String> values) {
            addCriterion("title_en in", values, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnNotIn(List<String> values) {
            addCriterion("title_en not in", values, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnBetween(String value1, String value2) {
            addCriterion("title_en between", value1, value2, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEnNotBetween(String value1, String value2) {
            addCriterion("title_en not between", value1, value2, "titleEn");
            return (Criteria) this;
        }

        public Criteria andTitleEsIsNull() {
            addCriterion("title_es is null");
            return (Criteria) this;
        }

        public Criteria andTitleEsIsNotNull() {
            addCriterion("title_es is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEsEqualTo(String value) {
            addCriterion("title_es =", value, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsNotEqualTo(String value) {
            addCriterion("title_es <>", value, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsGreaterThan(String value) {
            addCriterion("title_es >", value, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsGreaterThanOrEqualTo(String value) {
            addCriterion("title_es >=", value, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsLessThan(String value) {
            addCriterion("title_es <", value, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsLessThanOrEqualTo(String value) {
            addCriterion("title_es <=", value, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsLike(String value) {
            addCriterion("title_es like", value, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsNotLike(String value) {
            addCriterion("title_es not like", value, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsIn(List<String> values) {
            addCriterion("title_es in", values, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsNotIn(List<String> values) {
            addCriterion("title_es not in", values, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsBetween(String value1, String value2) {
            addCriterion("title_es between", value1, value2, "titleEs");
            return (Criteria) this;
        }

        public Criteria andTitleEsNotBetween(String value1, String value2) {
            addCriterion("title_es not between", value1, value2, "titleEs");
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

        public Criteria andPublishStartAtIsNull() {
            addCriterion("publish_start_at is null");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtIsNotNull() {
            addCriterion("publish_start_at is not null");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtEqualTo(Date value) {
            addCriterion("publish_start_at =", value, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtNotEqualTo(Date value) {
            addCriterion("publish_start_at <>", value, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtGreaterThan(Date value) {
            addCriterion("publish_start_at >", value, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtGreaterThanOrEqualTo(Date value) {
            addCriterion("publish_start_at >=", value, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtLessThan(Date value) {
            addCriterion("publish_start_at <", value, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtLessThanOrEqualTo(Date value) {
            addCriterion("publish_start_at <=", value, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtIn(List<Date> values) {
            addCriterion("publish_start_at in", values, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtNotIn(List<Date> values) {
            addCriterion("publish_start_at not in", values, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtBetween(Date value1, Date value2) {
            addCriterion("publish_start_at between", value1, value2, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishStartAtNotBetween(Date value1, Date value2) {
            addCriterion("publish_start_at not between", value1, value2, "publishStartAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtIsNull() {
            addCriterion("publish_end_at is null");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtIsNotNull() {
            addCriterion("publish_end_at is not null");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtEqualTo(Date value) {
            addCriterion("publish_end_at =", value, "publishEndAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtNotEqualTo(Date value) {
            addCriterion("publish_end_at <>", value, "publishEndAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtGreaterThan(Date value) {
            addCriterion("publish_end_at >", value, "publishEndAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtGreaterThanOrEqualTo(Date value) {
            addCriterion("publish_end_at >=", value, "publishEndAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtLessThan(Date value) {
            addCriterion("publish_end_at <", value, "publishEndAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtLessThanOrEqualTo(Date value) {
            addCriterion("publish_end_at <=", value, "publishEndAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtIn(List<Date> values) {
            addCriterion("publish_end_at in", values, "publishEndAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtNotIn(List<Date> values) {
            addCriterion("publish_end_at not in", values, "publishEndAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtBetween(Date value1, Date value2) {
            addCriterion("publish_end_at between", value1, value2, "publishEndAt");
            return (Criteria) this;
        }

        public Criteria andPublishEndAtNotBetween(Date value1, Date value2) {
            addCriterion("publish_end_at not between", value1, value2, "publishEndAt");
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