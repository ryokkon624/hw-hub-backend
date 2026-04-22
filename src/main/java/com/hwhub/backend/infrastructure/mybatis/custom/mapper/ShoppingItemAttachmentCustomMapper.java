package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShoppingItemAttachmentCustomMapper {

  /** 指定された買い物アイテムIDに紐づく添付画像を一括削除する */
  void deleteByShoppingItemId(@Param("shoppingItemId") Long shoppingItemId);
}
