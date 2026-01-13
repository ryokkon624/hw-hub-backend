package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItemAttachment;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItemAttachmentExample;
import java.util.List;

public interface TShoppingItemAttachmentMapper {
  int deleteByPrimaryKey(Long shoppingItemAttachmentId);

  int insert(TShoppingItemAttachment row);

  int insertSelective(TShoppingItemAttachment row);

  List<TShoppingItemAttachment> selectByExample(TShoppingItemAttachmentExample example);

  TShoppingItemAttachment selectByPrimaryKey(Long shoppingItemAttachmentId);

  int updateByPrimaryKeySelective(TShoppingItemAttachment row);

  int updateByPrimaryKey(TShoppingItemAttachment row);
}
