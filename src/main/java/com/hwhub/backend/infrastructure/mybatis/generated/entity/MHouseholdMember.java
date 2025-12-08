package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class MHouseholdMember {
    private Long householdId;

    private Long userId;

    private String nickname;

    private String status;

    private Long createUserId;

    private String createProgram;

    private Date createdAt;

    private Long updateUserId;

    private String updateProgram;

    private Date updatedAt;

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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