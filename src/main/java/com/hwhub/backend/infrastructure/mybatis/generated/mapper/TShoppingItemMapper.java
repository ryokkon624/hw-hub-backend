package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItem;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItemExample;
import java.util.List;

public interface TShoppingItemMapper {
    int deleteByPrimaryKey(Long shoppingItemId);

    int insert(TShoppingItem row);

    int insertSelective(TShoppingItem row);

    List<TShoppingItem> selectByExample(TShoppingItemExample example);

    TShoppingItem selectByPrimaryKey(Long shoppingItemId);

    int updateByPrimaryKeySelective(TShoppingItem row);

    int updateByPrimaryKey(TShoppingItem row);
}