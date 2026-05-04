package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class TUserEmailVerification {
    private Long userEmailVerificationId;

    private Long userId;

    private Date expiresAt;

    private Date usedAt;

    private Date requestedAt;

    private Integer requestCount;

    private Long createUserId;

    private String createProgram;

    private Date createdAt;

    private Long updateUserId;

    private String updateProgram;

    private Date updatedAt;

    private byte[] tokenHash;

    public Long getUserEmailVerificationId() {
        return userEmailVerificationId;
    }

    public void setUserEmailVerificationId(Long userEmailVerificationId) {
        this.userEmailVerificationId = userEmailVerificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Date usedAt) {
        this.usedAt = usedAt;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
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

    public byte[] getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(byte[] tokenHash) {
        this.tokenHash = tokenHash;
    }
}