package com.hwhub.backend.presentation.rest.shopping.dto;

import com.hwhub.backend.domain.model.ShoppingItemModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ShoppingItemDto {

  Long shoppingItemId;
  Long householdId;
  String name;
  String memo;
  String storeType;
  String status;
  String favorite;
  LocalDate purchasedAt;
  LocalDateTime createdAt;
  boolean hasImage;

  public static ShoppingItemDto fromModel(ShoppingItemModel model) {
    ShoppingItemDto dto = new ShoppingItemDto();

    dto.setShoppingItemId(model.getShoppingItemId());
    dto.setHouseholdId(model.getHouseholdId());
    dto.setName(model.getName());
    dto.setMemo(model.getMemo());
    dto.setStoreType(model.getStoreType());
    dto.setStatus(model.getStatus());
    dto.setFavorite(model.getFavorite());
    dto.setPurchasedAt(model.getPurchasedAt());
    dto.setCreatedAt(model.getCreatedAt());
    dto.setHasImage(model.getHasImage());

    return dto;
  }
}
