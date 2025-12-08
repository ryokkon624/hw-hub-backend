package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class MCode {
    private String codeType;

    private String codeValue;

    private String codeTypeName;

    private String codeTypeNameEn;

    private String name;

    private String displayNameJa;

    private String displayNameEn;

    private String displayNameEs;

    private String remarks;

    private String displayOrder;

    private Long createUserId;

    private String createProgram;

    private Date createdAt;

    private Long updateUserId;

    private String updateProgram;

    private Date updatedAt;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    public String getCodeTypeName() {
        return codeTypeName;
    }

    public void setCodeTypeName(String codeTypeName) {
        this.codeTypeName = codeTypeName == null ? null : codeTypeName.trim();
    }

    public String getCodeTypeNameEn() {
        return codeTypeNameEn;
    }

    public void setCodeTypeNameEn(String codeTypeNameEn) {
        this.codeTypeNameEn = codeTypeNameEn == null ? null : codeTypeNameEn.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDisplayNameJa() {
        return displayNameJa;
    }

    public void setDisplayNameJa(String displayNameJa) {
        this.displayNameJa = displayNameJa == null ? null : displayNameJa.trim();
    }

    public String getDisplayNameEn() {
        return displayNameEn;
    }

    public void setDisplayNameEn(String displayNameEn) {
        this.displayNameEn = displayNameEn == null ? null : displayNameEn.trim();
    }

    public String getDisplayNameEs() {
        return displayNameEs;
    }

    public void setDisplayNameEs(String displayNameEs) {
        this.displayNameEs = displayNameEs == null ? null : displayNameEs.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder == null ? null : displayOrder.trim();
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