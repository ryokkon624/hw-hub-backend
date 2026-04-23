package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.ShoppingItemModel;
import com.hwhub.backend.infrastructure.mybatis.custom.entity.ShoppingItemWithImageEntity;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TShoppingItem;
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

  /** 複数IDで買い物アイテムを一括取得 */
  List<TShoppingItem> selectByIds(@Param("ids") List<Long> ids);

  /** 複数の買い物アイテムのステータスを IN句 で一括更新 */
  void bulkUpdateStatus(
      @Param("ids") List<Long> ids,
      @Param("status") String status,
      @Param("userId") long userId,
      @Param("program") String program);
}
