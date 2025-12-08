package com.hwhub.backend.presentation.rest.shopping.dto;

import com.hwhub.backend.domain.enums.FavoriteFlag;
import com.hwhub.backend.domain.enums.PurchaseLocationType;
import com.hwhub.backend.validation.annotation.ByteSize;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateShoppingItemRequest {

  @NotBlank
  @ByteSize(max = 100)
  private String name;

  @ByteSize(max = 255)
  private String memo;

  @NotBlank
  @EnumValue(enumClass = PurchaseLocationType.class)
  private String storeType; // m_code:0010

  @EnumValue(enumClass = FavoriteFlag.class)
  private String favorite;

  private Long sourceShoppingItemId;
}
