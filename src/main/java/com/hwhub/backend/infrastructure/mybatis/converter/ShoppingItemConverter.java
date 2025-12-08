package com.hwhub.backend.infrastructure.mybatis.converter;

import static com.hwhub.backend.infrastructure.mybatis.converter.DateConverter.toDate;

import com.hwhub.backend.domain.model.ShoppingItemModel;
import com.hwhub.backend.infrastructure.mybatis.custom.entity.ShoppingItemWithImageEntity;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItem;

public class ShoppingItemConverter {

  private ShoppingItemConverter() {}

  public static ShoppingItemModel toModel(ShoppingItemWithImageEntity entity) {
    if (entity == null) {
      return null;
    }

    return ShoppingItemModel.reconstruct(
        entity.getShoppingItemId(),
        entity.getHouseholdId(),
        entity.getName(),
        entity.getMemo(),
        entity.getStoreType(),
        entity.getStatus(),
        entity.getFavorite(),
        DateConverter.toLocalDate(entity.getPurchasedAt()),
        DateConverter.toLocalDateTime(entity.getCreatedAt()),
        entity.getHasImage());
  }

  public static ShoppingItemModel toModel(TShoppingItem entity) {
    if (entity == null) {
      return null;
    }

    return ShoppingItemModel.reconstruct(
        entity.getShoppingItemId(),
        entity.getHouseholdId(),
        entity.getName(),
        entity.getMemo(),
        entity.getStoreType(),
        entity.getStatus(),
        entity.getFavorite(),
        DateConverter.toLocalDate(entity.getPurchasedAt()),
        DateConverter.toLocalDateTime(entity.getCreatedAt()),
        false);
  }

  public static TShoppingItem toEntity(ShoppingItemModel model) {
    if (model == null) {
      return null;
    }

    TShoppingItem entity = new TShoppingItem();
    entity.setShoppingItemId(model.getShoppingItemId());
    entity.setHouseholdId(model.getHouseholdId());
    entity.setName(model.getName());
    entity.setMemo(model.getMemo());
    entity.setStoreType(model.getStoreType());
    entity.setStatus(model.getStatus());
    entity.setFavorite(model.getFavorite());
    entity.setPurchasedAt(toDate(model.getPurchasedAt()));
    // createdAtはセットしない

    return entity;
  }
}
