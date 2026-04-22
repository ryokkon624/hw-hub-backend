package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.ShoppingItemHistorySuggestionModel;
import com.hwhub.backend.domain.model.ShoppingItemModel;
import com.hwhub.backend.domain.repository.ShoppingItemRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.ShoppingItemConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.entity.ShoppingItemWithImageEntity;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.ShoppingItemCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.ShoppingItemHistoryCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItem;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.TShoppingItemMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisShoppingItemRepository implements ShoppingItemRepository {

  private final ShoppingItemCustomMapper customMapper;
  private final TShoppingItemMapper mapper;
  private final ShoppingItemHistoryCustomMapper historyCustomMapper;

  @Override
  public List<ShoppingItemModel> findByHouseholdId(long householdId) {
    List<ShoppingItemWithImageEntity> list = customMapper.selectByHouseholdId(householdId);

    return list.stream().map(ShoppingItemConverter::toModel).toList();
  }

  @Override
  public List<ShoppingItemModel> findFavoritesByHouseholdId(long householdId) {
    List<ShoppingItemWithImageEntity> list = customMapper.selectFavoritesByHouseholdId(householdId);

    return list.stream().map(ShoppingItemConverter::toModel).toList();
  }

  @Override
  public Optional<ShoppingItemModel> findById(long shoppingItemId) {
    TShoppingItem entity = mapper.selectByPrimaryKey(shoppingItemId);
    if (entity == null) {
      return Optional.empty();
    }
    return Optional.of(ShoppingItemConverter.toModel(entity));
  }

  @Override
  public ShoppingItemModel insert(ShoppingItemModel model, long userId, String program) {
    TShoppingItem entity = ShoppingItemConverter.toEntity(model);
    entity.setCreateUserId(userId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);
    mapper.insertSelective(entity);

    return this.findById(entity.getShoppingItemId()).orElse(null);
  }

  @Override
  public ShoppingItemModel update(ShoppingItemModel model, long userId, String program) {
    TShoppingItem entity = ShoppingItemConverter.toEntity(model);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);
    customMapper.update(model, userId, program);

    return this.findById(model.getShoppingItemId()).orElse(null);
  }

  @Override
  public void deleteById(long shoppingItemId) {
    mapper.deleteByPrimaryKey(shoppingItemId);
  }

  @Override
  public List<ShoppingItemHistorySuggestionModel> findHistorySuggestions(
      Long householdId, String keyword, String storeType, int limit) {
    return historyCustomMapper.selectHistorySuggestions(householdId, keyword, storeType, limit);
  }
}
