package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class MHouseworkTemplate {
  private Long houseworkTemplateId;

  private String nameJa;

  private String nameEn;

  private String nameEs;

  private String descriptionJa;

  private String descriptionEn;

  private String descriptionEs;

  private String recommendationJa;

  private String recommendationEn;

  private String recommendationEs;

  private String category;

  private String recurrenceType;

  private Integer weeklyDays;

  private Integer dayOfMonth;

  private Integer nthWeek;

  private Integer weekday;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

  public Long getHouseworkTemplateId() {
    return houseworkTemplateId;
  }

  public void setHouseworkTemplateId(Long houseworkTemplateId) {
    this.houseworkTemplateId = houseworkTemplateId;
  }

  public String getNameJa() {
    return nameJa;
  }

  public void setNameJa(String nameJa) {
    this.nameJa = nameJa == null ? null : nameJa.trim();
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn == null ? null : nameEn.trim();
  }

  public String getNameEs() {
    return nameEs;
  }

  public void setNameEs(String nameEs) {
    this.nameEs = nameEs == null ? null : nameEs.trim();
  }

  public String getDescriptionJa() {
    return descriptionJa;
  }

  public void setDescriptionJa(String descriptionJa) {
    this.descriptionJa = descriptionJa == null ? null : descriptionJa.trim();
  }

  public String getDescriptionEn() {
    return descriptionEn;
  }

  public void setDescriptionEn(String descriptionEn) {
    this.descriptionEn = descriptionEn == null ? null : descriptionEn.trim();
  }

  public String getDescriptionEs() {
    return descriptionEs;
  }

  public void setDescriptionEs(String descriptionEs) {
    this.descriptionEs = descriptionEs == null ? null : descriptionEs.trim();
  }

  public String getRecommendationJa() {
    return recommendationJa;
  }

  public void setRecommendationJa(String recommendationJa) {
    this.recommendationJa = recommendationJa == null ? null : recommendationJa.trim();
  }

  public String getRecommendationEn() {
    return recommendationEn;
  }

  public void setRecommendationEn(String recommendationEn) {
    this.recommendationEn = recommendationEn == null ? null : recommendationEn.trim();
  }

  public String getRecommendationEs() {
    return recommendationEs;
  }

  public void setRecommendationEs(String recommendationEs) {
    this.recommendationEs = recommendationEs == null ? null : recommendationEs.trim();
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category == null ? null : category.trim();
  }

  public String getRecurrenceType() {
    return recurrenceType;
  }

  public void setRecurrenceType(String recurrenceType) {
    this.recurrenceType = recurrenceType == null ? null : recurrenceType.trim();
  }

  public Integer getWeeklyDays() {
    return weeklyDays;
  }

  public void setWeeklyDays(Integer weeklyDays) {
    this.weeklyDays = weeklyDays;
  }

  public Integer getDayOfMonth() {
    return dayOfMonth;
  }

  public void setDayOfMonth(Integer dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public Integer getNthWeek() {
    return nthWeek;
  }

  public void setNthWeek(Integer nthWeek) {
    this.nthWeek = nthWeek;
  }

  public Integer getWeekday() {
    return weekday;
  }

  public void setWeekday(Integer weekday) {
    this.weekday = weekday;
  }

  public Long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public String getCreateProgram() {
    return createProgram;
  }

  public void setCreateProgram(String createProgram) {
    this.createProgram = createProgram == null ? null : createProgram.trim();
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Long getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(Long updateUserId) {
    this.updateUserId = updateUserId;
  }

  public String getUpdateProgram() {
    return updateProgram;
  }

  public void setUpdateProgram(String updateProgram) {
    this.updateProgram = updateProgram == null ? null : updateProgram.trim();
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
