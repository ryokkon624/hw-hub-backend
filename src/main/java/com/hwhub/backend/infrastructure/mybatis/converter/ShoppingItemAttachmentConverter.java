package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.ShoppingItemAttachment;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItemAttachment;

public final class ShoppingItemAttachmentConverter {

  private ShoppingItemAttachmentConverter() {}

  public static TShoppingItemAttachment toEntity(ShoppingItemAttachment model) {
    if (model == null) return null;
    TShoppingItemAttachment e = new TShoppingItemAttachment();
    e.setShoppingItemAttachmentId(model.getId());
    e.setShoppingItemId(model.getShoppingItemId());
    e.setFileKey(model.getFileKey());
    e.setFileName(model.getFileName());
    e.setMimeType(model.getMimeType());
    e.setSortOrder(model.getSortOrder());
    return e;
  }

  public static ShoppingItemAttachment toModel(TShoppingItemAttachment e) {
    if (e == null) return null;
    return ShoppingItemAttachment.reconstruct(
        e.getShoppingItemAttachmentId(),
        e.getShoppingItemId(),
        e.getFileKey(),
        e.getFileName(),
        e.getMimeType(),
        e.getSortOrder());
  }
}
