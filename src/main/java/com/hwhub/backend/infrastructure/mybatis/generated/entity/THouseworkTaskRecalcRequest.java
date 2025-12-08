package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class THouseworkTaskRecalcRequest {
    private Long requestId;

    private Long houseworkId;

    private String recalcRequestStatus;

    private Integer retryCount;

    private String lastErrorMessage;

    private Long createUserId;

    private String createProgram;

    private Date createdAt;

    private Long updateUserId;

    private String updateProgram;

    private Date updatedAt;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getHouseworkId() {
        return houseworkId;
    }

    public void setHouseworkId(Long houseworkId) {
        this.houseworkId = houseworkId;
    }

    public String getRecalcRequestStatus() {
        return recalcRequestStatus;
    }

    public void setRecalcRequestStatus(String recalcRequestStatus) {
        this.recalcRequestStatus = recalcRequestStatus == null ? null : recalcRequestStatus.trim();
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage == null ? null : lastErrorMessage.trim();
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