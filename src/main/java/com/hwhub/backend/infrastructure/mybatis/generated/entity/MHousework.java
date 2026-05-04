package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class MHousework {
    private Long houseworkId;

    private Long householdId;

    private String name;

    private String description;

    private String category;

    private String recurrenceType;

    private Integer weeklyDays;

    private Integer dayOfMonth;

    private Integer nthWeek;

    private Integer weekday;

    private Date startDate;

    private Date endDate;

    private Long defaultAssigneeUserId;

    private Long createUserId;

    private String createProgram;

    private Date createdAt;

    private Long updateUserId;

    private String updateProgram;

    private Date updatedAt;

    public Long getHouseworkId() {
        return houseworkId;
    }

    public void setHouseworkId(Long houseworkId) {
        this.houseworkId = houseworkId;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getDefaultAssigneeUserId() {
        return defaultAssigneeUserId;
    }

    public void setDefaultAssigneeUserId(Long defaultAssigneeUserId) {
        this.defaultAssigneeUserId = defaultAssigneeUserId;
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