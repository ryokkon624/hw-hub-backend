package com.hwhub.backend.presentation.rest.shopping.dto;

import com.hwhub.backend.domain.model.ShoppingItemModel;
import java.util.List;
import lombok.Value;

@Value
public class ShoppingItemListResponse {

  List<ShoppingItemDto> items;

  public static ShoppingItemListResponse fromModelList(List<ShoppingItemModel> models) {

    return new ShoppingItemListResponse(models.stream().map(ShoppingItemDto::fromModel).toList());
  }
}
