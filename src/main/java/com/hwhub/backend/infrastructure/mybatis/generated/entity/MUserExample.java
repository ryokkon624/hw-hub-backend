package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MUserExample() {
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

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andPasswordHashIsNull() {
            addCriterion("password_hash is null");
            return (Criteria) this;
        }

        public Criteria andPasswordHashIsNotNull() {
            addCriterion("password_hash is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordHashEqualTo(String value) {
            addCriterion("password_hash =", value, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashNotEqualTo(String value) {
            addCriterion("password_hash <>", value, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashGreaterThan(String value) {
            addCriterion("password_hash >", value, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashGreaterThanOrEqualTo(String value) {
            addCriterion("password_hash >=", value, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashLessThan(String value) {
            addCriterion("password_hash <", value, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashLessThanOrEqualTo(String value) {
            addCriterion("password_hash <=", value, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashLike(String value) {
            addCriterion("password_hash like", value, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashNotLike(String value) {
            addCriterion("password_hash not like", value, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashIn(List<String> values) {
            addCriterion("password_hash in", values, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashNotIn(List<String> values) {
            addCriterion("password_hash not in", values, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashBetween(String value1, String value2) {
            addCriterion("password_hash between", value1, value2, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andPasswordHashNotBetween(String value1, String value2) {
            addCriterion("password_hash not between", value1, value2, "passwordHash");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIsNull() {
            addCriterion("auth_provider is null");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIsNotNull() {
            addCriterion("auth_provider is not null");
            return (Criteria) this;
        }

        public Criteria andAuthProviderEqualTo(String value) {
            addCriterion("auth_provider =", value, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderNotEqualTo(String value) {
            addCriterion("auth_provider <>", value, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderGreaterThan(String value) {
            addCriterion("auth_provider >", value, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderGreaterThanOrEqualTo(String value) {
            addCriterion("auth_provider >=", value, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderLessThan(String value) {
            addCriterion("auth_provider <", value, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderLessThanOrEqualTo(String value) {
            addCriterion("auth_provider <=", value, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderLike(String value) {
            addCriterion("auth_provider like", value, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderNotLike(String value) {
            addCriterion("auth_provider not like", value, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIn(List<String> values) {
            addCriterion("auth_provider in", values, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderNotIn(List<String> values) {
            addCriterion("auth_provider not in", values, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderBetween(String value1, String value2) {
            addCriterion("auth_provider between", value1, value2, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderNotBetween(String value1, String value2) {
            addCriterion("auth_provider not between", value1, value2, "authProvider");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdIsNull() {
            addCriterion("auth_provider_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdIsNotNull() {
            addCriterion("auth_provider_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdEqualTo(String value) {
            addCriterion("auth_provider_id =", value, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdNotEqualTo(String value) {
            addCriterion("auth_provider_id <>", value, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdGreaterThan(String value) {
            addCriterion("auth_provider_id >", value, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdGreaterThanOrEqualTo(String value) {
            addCriterion("auth_provider_id >=", value, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdLessThan(String value) {
            addCriterion("auth_provider_id <", value, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdLessThanOrEqualTo(String value) {
            addCriterion("auth_provider_id <=", value, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdLike(String value) {
            addCriterion("auth_provider_id like", value, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdNotLike(String value) {
            addCriterion("auth_provider_id not like", value, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdIn(List<String> values) {
            addCriterion("auth_provider_id in", values, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdNotIn(List<String> values) {
            addCriterion("auth_provider_id not in", values, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdBetween(String value1, String value2) {
            addCriterion("auth_provider_id between", value1, value2, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andAuthProviderIdNotBetween(String value1, String value2) {
            addCriterion("auth_provider_id not between", value1, value2, "authProviderId");
            return (Criteria) this;
        }

        public Criteria andDisplayNameIsNull() {
            addCriterion("display_name is null");
            return (Criteria) this;
        }

        public Criteria andDisplayNameIsNotNull() {
            addCriterion("display_name is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayNameEqualTo(String value) {
            addCriterion("display_name =", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameNotEqualTo(String value) {
            addCriterion("display_name <>", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameGreaterThan(String value) {
            addCriterion("display_name >", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameGreaterThanOrEqualTo(String value) {
            addCriterion("display_name >=", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameLessThan(String value) {
            addCriterion("display_name <", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameLessThanOrEqualTo(String value) {
            addCriterion("display_name <=", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameLike(String value) {
            addCriterion("display_name like", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameNotLike(String value) {
            addCriterion("display_name not like", value, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameIn(List<String> values) {
            addCriterion("display_name in", values, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameNotIn(List<String> values) {
            addCriterion("display_name not in", values, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameBetween(String value1, String value2) {
            addCriterion("display_name between", value1, value2, "displayName");
            return (Criteria) this;
        }

        public Criteria andDisplayNameNotBetween(String value1, String value2) {
            addCriterion("display_name not between", value1, value2, "displayName");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyIsNull() {
            addCriterion("profile_image_key is null");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyIsNotNull() {
            addCriterion("profile_image_key is not null");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyEqualTo(String value) {
            addCriterion("profile_image_key =", value, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyNotEqualTo(String value) {
            addCriterion("profile_image_key <>", value, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyGreaterThan(String value) {
            addCriterion("profile_image_key >", value, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyGreaterThanOrEqualTo(String value) {
            addCriterion("profile_image_key >=", value, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyLessThan(String value) {
            addCriterion("profile_image_key <", value, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyLessThanOrEqualTo(String value) {
            addCriterion("profile_image_key <=", value, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyLike(String value) {
            addCriterion("profile_image_key like", value, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyNotLike(String value) {
            addCriterion("profile_image_key not like", value, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyIn(List<String> values) {
            addCriterion("profile_image_key in", values, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyNotIn(List<String> values) {
            addCriterion("profile_image_key not in", values, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyBetween(String value1, String value2) {
            addCriterion("profile_image_key between", value1, value2, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andProfileImageKeyNotBetween(String value1, String value2) {
            addCriterion("profile_image_key not between", value1, value2, "profileImageKey");
            return (Criteria) this;
        }

        public Criteria andLocaleIsNull() {
            addCriterion("`locale` is null");
            return (Criteria) this;
        }

        public Criteria andLocaleIsNotNull() {
            addCriterion("`locale` is not null");
            return (Criteria) this;
        }

        public Criteria andLocaleEqualTo(String value) {
            addCriterion("`locale` =", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleNotEqualTo(String value) {
            addCriterion("`locale` <>", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleGreaterThan(String value) {
            addCriterion("`locale` >", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleGreaterThanOrEqualTo(String value) {
            addCriterion("`locale` >=", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleLessThan(String value) {
            addCriterion("`locale` <", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleLessThanOrEqualTo(String value) {
            addCriterion("`locale` <=", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleLike(String value) {
            addCriterion("`locale` like", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleNotLike(String value) {
            addCriterion("`locale` not like", value, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleIn(List<String> values) {
            addCriterion("`locale` in", values, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleNotIn(List<String> values) {
            addCriterion("`locale` not in", values, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleBetween(String value1, String value2) {
            addCriterion("`locale` between", value1, value2, "locale");
            return (Criteria) this;
        }

        public Criteria andLocaleNotBetween(String value1, String value2) {
            addCriterion("`locale` not between", value1, value2, "locale");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNull() {
            addCriterion("is_active is null");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNotNull() {
            addCriterion("is_active is not null");
            return (Criteria) this;
        }

        public Criteria andIsActiveEqualTo(Boolean value) {
            addCriterion("is_active =", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotEqualTo(Boolean value) {
            addCriterion("is_active <>", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThan(Boolean value) {
            addCriterion("is_active >", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_active >=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThan(Boolean value) {
            addCriterion("is_active <", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThanOrEqualTo(Boolean value) {
            addCriterion("is_active <=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveIn(List<Boolean> values) {
            addCriterion("is_active in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotIn(List<Boolean> values) {
            addCriterion("is_active not in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveBetween(Boolean value1, Boolean value2) {
            addCriterion("is_active between", value1, value2, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_active not between", value1, value2, "isActive");
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