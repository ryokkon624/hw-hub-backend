package com.hwhub.backend.infrastructure.mybatis.generated.entity;

import java.util.Date;

public class TShoppingItem {
  private Long shoppingItemId;

  private Long householdId;

  private String name;

  private String memo;

  private String storeType;

  private String status;

  private String favorite;

  private Date purchasedAt;

  private Long createUserId;

  private String createProgram;

  private Date createdAt;

  private Long updateUserId;

  private String updateProgram;

  private Date updatedAt;

  public Long getShoppingItemId() {
    return shoppingItemId;
  }

  public void setShoppingItemId(Long shoppingItemId) {
    this.shoppingItemId = shoppingItemId;
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

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo == null ? null : memo.trim();
  }

  public String getStoreType() {
    return storeType;
  }

  public void setStoreType(String storeType) {
    this.storeType = storeType == null ? null : storeType.trim();
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status == null ? null : status.trim();
  }

  public String getFavorite() {
    return favorite;
  }

  public void setFavorite(String favorite) {
    this.favorite = favorite == null ? null : favorite.trim();
  }

  public Date getPurchasedAt() {
    return purchasedAt;
  }

  public void setPurchasedAt(Date purchasedAt) {
    this.purchasedAt = purchasedAt;
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
