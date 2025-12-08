package com.hwhub.backend.infrastructure.mybatis.custom.entity;

import java.util.Date;
import lombok.Data;

@Data
public class ShoppingItemWithImageEntity {
  private Long shoppingItemId;
  private Long householdId;
  private String name;
  private String memo;
  private String storeType;
  private String status;
  private String favorite;
  private Date purchasedAt;
  private Date createdAt;
  private Boolean hasImage;
}
