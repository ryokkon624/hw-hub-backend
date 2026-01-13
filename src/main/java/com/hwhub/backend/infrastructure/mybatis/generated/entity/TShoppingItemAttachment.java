package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class TShoppingItemAttachment {
  private Long shoppingItemAttachmentId;

  private Long shoppingItemId;

  private String fileKey;

  private String fileName;

  private String mimeType;

  private Integer sortOrder;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

  public Long getShoppingItemAttachmentId() {
    return shoppingItemAttachmentId;
  }

  public void setShoppingItemAttachmentId(Long shoppingItemAttachmentId) {
    this.shoppingItemAttachmentId = shoppingItemAttachmentId;
  }

  public Long getShoppingItemId() {
    return shoppingItemId;
  }

  public void setShoppingItemId(Long shoppingItemId) {
    this.shoppingItemId = shoppingItemId;
  }

  public String getFileKey() {
    return fileKey;
  }

  public void setFileKey(String fileKey) {
    this.fileKey = fileKey == null ? null : fileKey.trim();
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName == null ? null : fileName.trim();
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType == null ? null : mimeType.trim();
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
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
