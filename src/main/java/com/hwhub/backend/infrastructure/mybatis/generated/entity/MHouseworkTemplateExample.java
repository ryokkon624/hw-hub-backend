package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MHouseworkTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MHouseworkTemplateExample() {
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

        public Criteria andHouseworkTemplateIdIsNull() {
            addCriterion("housework_template_id is null");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdIsNotNull() {
            addCriterion("housework_template_id is not null");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdEqualTo(Long value) {
            addCriterion("housework_template_id =", value, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdNotEqualTo(Long value) {
            addCriterion("housework_template_id <>", value, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdGreaterThan(Long value) {
            addCriterion("housework_template_id >", value, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdGreaterThanOrEqualTo(Long value) {
            addCriterion("housework_template_id >=", value, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdLessThan(Long value) {
            addCriterion("housework_template_id <", value, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdLessThanOrEqualTo(Long value) {
            addCriterion("housework_template_id <=", value, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdIn(List<Long> values) {
            addCriterion("housework_template_id in", values, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdNotIn(List<Long> values) {
            addCriterion("housework_template_id not in", values, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdBetween(Long value1, Long value2) {
            addCriterion("housework_template_id between", value1, value2, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andHouseworkTemplateIdNotBetween(Long value1, Long value2) {
            addCriterion("housework_template_id not between", value1, value2, "houseworkTemplateId");
            return (Criteria) this;
        }

        public Criteria andNameJaIsNull() {
            addCriterion("name_ja is null");
            return (Criteria) this;
        }

        public Criteria andNameJaIsNotNull() {
            addCriterion("name_ja is not null");
            return (Criteria) this;
        }

        public Criteria andNameJaEqualTo(String value) {
            addCriterion("name_ja =", value, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaNotEqualTo(String value) {
            addCriterion("name_ja <>", value, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaGreaterThan(String value) {
            addCriterion("name_ja >", value, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaGreaterThanOrEqualTo(String value) {
            addCriterion("name_ja >=", value, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaLessThan(String value) {
            addCriterion("name_ja <", value, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaLessThanOrEqualTo(String value) {
            addCriterion("name_ja <=", value, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaLike(String value) {
            addCriterion("name_ja like", value, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaNotLike(String value) {
            addCriterion("name_ja not like", value, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaIn(List<String> values) {
            addCriterion("name_ja in", values, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaNotIn(List<String> values) {
            addCriterion("name_ja not in", values, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaBetween(String value1, String value2) {
            addCriterion("name_ja between", value1, value2, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameJaNotBetween(String value1, String value2) {
            addCriterion("name_ja not between", value1, value2, "nameJa");
            return (Criteria) this;
        }

        public Criteria andNameEnIsNull() {
            addCriterion("name_en is null");
            return (Criteria) this;
        }

        public Criteria andNameEnIsNotNull() {
            addCriterion("name_en is not null");
            return (Criteria) this;
        }

        public Criteria andNameEnEqualTo(String value) {
            addCriterion("name_en =", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotEqualTo(String value) {
            addCriterion("name_en <>", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnGreaterThan(String value) {
            addCriterion("name_en >", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("name_en >=", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnLessThan(String value) {
            addCriterion("name_en <", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnLessThanOrEqualTo(String value) {
            addCriterion("name_en <=", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnLike(String value) {
            addCriterion("name_en like", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotLike(String value) {
            addCriterion("name_en not like", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnIn(List<String> values) {
            addCriterion("name_en in", values, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotIn(List<String> values) {
            addCriterion("name_en not in", values, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnBetween(String value1, String value2) {
            addCriterion("name_en between", value1, value2, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotBetween(String value1, String value2) {
            addCriterion("name_en not between", value1, value2, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEsIsNull() {
            addCriterion("name_es is null");
            return (Criteria) this;
        }

        public Criteria andNameEsIsNotNull() {
            addCriterion("name_es is not null");
            return (Criteria) this;
        }

        public Criteria andNameEsEqualTo(String value) {
            addCriterion("name_es =", value, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsNotEqualTo(String value) {
            addCriterion("name_es <>", value, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsGreaterThan(String value) {
            addCriterion("name_es >", value, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsGreaterThanOrEqualTo(String value) {
            addCriterion("name_es >=", value, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsLessThan(String value) {
            addCriterion("name_es <", value, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsLessThanOrEqualTo(String value) {
            addCriterion("name_es <=", value, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsLike(String value) {
            addCriterion("name_es like", value, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsNotLike(String value) {
            addCriterion("name_es not like", value, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsIn(List<String> values) {
            addCriterion("name_es in", values, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsNotIn(List<String> values) {
            addCriterion("name_es not in", values, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsBetween(String value1, String value2) {
            addCriterion("name_es between", value1, value2, "nameEs");
            return (Criteria) this;
        }

        public Criteria andNameEsNotBetween(String value1, String value2) {
            addCriterion("name_es not between", value1, value2, "nameEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaIsNull() {
            addCriterion("description_ja is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaIsNotNull() {
            addCriterion("description_ja is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaEqualTo(String value) {
            addCriterion("description_ja =", value, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaNotEqualTo(String value) {
            addCriterion("description_ja <>", value, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaGreaterThan(String value) {
            addCriterion("description_ja >", value, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaGreaterThanOrEqualTo(String value) {
            addCriterion("description_ja >=", value, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaLessThan(String value) {
            addCriterion("description_ja <", value, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaLessThanOrEqualTo(String value) {
            addCriterion("description_ja <=", value, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaLike(String value) {
            addCriterion("description_ja like", value, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaNotLike(String value) {
            addCriterion("description_ja not like", value, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaIn(List<String> values) {
            addCriterion("description_ja in", values, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaNotIn(List<String> values) {
            addCriterion("description_ja not in", values, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaBetween(String value1, String value2) {
            addCriterion("description_ja between", value1, value2, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionJaNotBetween(String value1, String value2) {
            addCriterion("description_ja not between", value1, value2, "descriptionJa");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnIsNull() {
            addCriterion("description_en is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnIsNotNull() {
            addCriterion("description_en is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnEqualTo(String value) {
            addCriterion("description_en =", value, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnNotEqualTo(String value) {
            addCriterion("description_en <>", value, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnGreaterThan(String value) {
            addCriterion("description_en >", value, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnGreaterThanOrEqualTo(String value) {
            addCriterion("description_en >=", value, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnLessThan(String value) {
            addCriterion("description_en <", value, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnLessThanOrEqualTo(String value) {
            addCriterion("description_en <=", value, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnLike(String value) {
            addCriterion("description_en like", value, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnNotLike(String value) {
            addCriterion("description_en not like", value, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnIn(List<String> values) {
            addCriterion("description_en in", values, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnNotIn(List<String> values) {
            addCriterion("description_en not in", values, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnBetween(String value1, String value2) {
            addCriterion("description_en between", value1, value2, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEnNotBetween(String value1, String value2) {
            addCriterion("description_en not between", value1, value2, "descriptionEn");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsIsNull() {
            addCriterion("description_es is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsIsNotNull() {
            addCriterion("description_es is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsEqualTo(String value) {
            addCriterion("description_es =", value, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsNotEqualTo(String value) {
            addCriterion("description_es <>", value, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsGreaterThan(String value) {
            addCriterion("description_es >", value, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsGreaterThanOrEqualTo(String value) {
            addCriterion("description_es >=", value, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsLessThan(String value) {
            addCriterion("description_es <", value, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsLessThanOrEqualTo(String value) {
            addCriterion("description_es <=", value, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsLike(String value) {
            addCriterion("description_es like", value, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsNotLike(String value) {
            addCriterion("description_es not like", value, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsIn(List<String> values) {
            addCriterion("description_es in", values, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsNotIn(List<String> values) {
            addCriterion("description_es not in", values, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsBetween(String value1, String value2) {
            addCriterion("description_es between", value1, value2, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andDescriptionEsNotBetween(String value1, String value2) {
            addCriterion("description_es not between", value1, value2, "descriptionEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaIsNull() {
            addCriterion("recommendation_ja is null");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaIsNotNull() {
            addCriterion("recommendation_ja is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaEqualTo(String value) {
            addCriterion("recommendation_ja =", value, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaNotEqualTo(String value) {
            addCriterion("recommendation_ja <>", value, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaGreaterThan(String value) {
            addCriterion("recommendation_ja >", value, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaGreaterThanOrEqualTo(String value) {
            addCriterion("recommendation_ja >=", value, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaLessThan(String value) {
            addCriterion("recommendation_ja <", value, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaLessThanOrEqualTo(String value) {
            addCriterion("recommendation_ja <=", value, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaLike(String value) {
            addCriterion("recommendation_ja like", value, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaNotLike(String value) {
            addCriterion("recommendation_ja not like", value, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaIn(List<String> values) {
            addCriterion("recommendation_ja in", values, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaNotIn(List<String> values) {
            addCriterion("recommendation_ja not in", values, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaBetween(String value1, String value2) {
            addCriterion("recommendation_ja between", value1, value2, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationJaNotBetween(String value1, String value2) {
            addCriterion("recommendation_ja not between", value1, value2, "recommendationJa");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnIsNull() {
            addCriterion("recommendation_en is null");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnIsNotNull() {
            addCriterion("recommendation_en is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnEqualTo(String value) {
            addCriterion("recommendation_en =", value, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnNotEqualTo(String value) {
            addCriterion("recommendation_en <>", value, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnGreaterThan(String value) {
            addCriterion("recommendation_en >", value, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnGreaterThanOrEqualTo(String value) {
            addCriterion("recommendation_en >=", value, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnLessThan(String value) {
            addCriterion("recommendation_en <", value, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnLessThanOrEqualTo(String value) {
            addCriterion("recommendation_en <=", value, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnLike(String value) {
            addCriterion("recommendation_en like", value, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnNotLike(String value) {
            addCriterion("recommendation_en not like", value, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnIn(List<String> values) {
            addCriterion("recommendation_en in", values, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnNotIn(List<String> values) {
            addCriterion("recommendation_en not in", values, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnBetween(String value1, String value2) {
            addCriterion("recommendation_en between", value1, value2, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEnNotBetween(String value1, String value2) {
            addCriterion("recommendation_en not between", value1, value2, "recommendationEn");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsIsNull() {
            addCriterion("recommendation_es is null");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsIsNotNull() {
            addCriterion("recommendation_es is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsEqualTo(String value) {
            addCriterion("recommendation_es =", value, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsNotEqualTo(String value) {
            addCriterion("recommendation_es <>", value, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsGreaterThan(String value) {
            addCriterion("recommendation_es >", value, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsGreaterThanOrEqualTo(String value) {
            addCriterion("recommendation_es >=", value, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsLessThan(String value) {
            addCriterion("recommendation_es <", value, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsLessThanOrEqualTo(String value) {
            addCriterion("recommendation_es <=", value, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsLike(String value) {
            addCriterion("recommendation_es like", value, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsNotLike(String value) {
            addCriterion("recommendation_es not like", value, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsIn(List<String> values) {
            addCriterion("recommendation_es in", values, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsNotIn(List<String> values) {
            addCriterion("recommendation_es not in", values, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsBetween(String value1, String value2) {
            addCriterion("recommendation_es between", value1, value2, "recommendationEs");
            return (Criteria) this;
        }

        public Criteria andRecommendationEsNotBetween(String value1, String value2) {
            addCriterion("recommendation_es not between", value1, value2, "recommendationEs");
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