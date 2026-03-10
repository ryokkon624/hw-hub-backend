package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.ShoppingItemModel;
import com.hwhub.backend.infrastructure.mybatis.custom.entity.ShoppingItemWithImageEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShoppingItemCustomMapper {

  /** 世帯IDごとの買い物アイテム一覧取得（添付有無フラグ付き） */
  List<ShoppingItemWithImageEntity> selectByHouseholdId(@Param("householdId") Long householdId);

  /** 世帯内のお気に入り買い物アイテム一覧（name, store_typeで最新に集約） */
  List<ShoppingItemWithImageEntity> selectFavoritesByHouseholdId(
      @Param("householdId") Long householdId);

  void update(
      @Param("item") ShoppingItemModel item,
      @Param("userId") Long userId,
      @Param("program") String program);
}
