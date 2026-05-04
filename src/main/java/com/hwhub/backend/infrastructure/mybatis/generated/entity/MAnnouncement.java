package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class MAnnouncement {
    private Long announcementId;

    private String titleJa;

    private String titleEn;

    private String titleEs;

    private String status;

    private Date publishStartAt;

    private Date publishEndAt;

    private Long createUserId;

    private String createProgram;

    private Date createdAt;

    private Long updateUserId;

    private String updateProgram;

    private Date updatedAt;

    private String bodyJa;

    private String bodyEn;

    private String bodyEs;

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitleJa() {
        return titleJa;
    }

    public void setTitleJa(String titleJa) {
        this.titleJa = titleJa == null ? null : titleJa.trim();
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn == null ? null : titleEn.trim();
    }

    public String getTitleEs() {
        return titleEs;
    }

    public void setTitleEs(String titleEs) {
        this.titleEs = titleEs == null ? null : titleEs.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getPublishStartAt() {
        return publishStartAt;
    }

    public void setPublishStartAt(Date publishStartAt) {
        this.publishStartAt = publishStartAt;
    }

    public Date getPublishEndAt() {
        return publishEndAt;
    }

    public void setPublishEndAt(Date publishEndAt) {
        this.publishEndAt = publishEndAt;
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

    public String getBodyJa() {
        return bodyJa;
    }

    public void setBodyJa(String bodyJa) {
        this.bodyJa = bodyJa == null ? null : bodyJa.trim();
    }

    public String getBodyEn() {
        return bodyEn;
    }

    public void setBodyEn(String bodyEn) {
        this.bodyEn = bodyEn == null ? null : bodyEn.trim();
    }

    public String getBodyEs() {
        return bodyEs;
    }

    public void setBodyEs(String bodyEs) {
        this.bodyEs = bodyEs == null ? null : bodyEs.trim();
    }
}