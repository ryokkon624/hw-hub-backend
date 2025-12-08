package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.ShoppingItemHistorySuggestionModel;
import com.hwhub.backend.domain.model.ShoppingItemModel;
import java.util.List;
import java.util.Optional;

public interface ShoppingItemRepository {

  /** Household 内の全アイテムを取得 */
  List<ShoppingItemModel> findByHouseholdId(long householdId);

  Optional<ShoppingItemModel> findById(long shoppingItemId);

  ShoppingItemModel insert(ShoppingItemModel model, long userId, String program);

  ShoppingItemModel update(ShoppingItemModel model, long userId, String program);

  /**
   * 買い物履歴サジェスト（name + storeType ごとに集約）
   *
   * @param householdId 世帯ID
   * @param keyword 検索ワード
   * @param storeType 購入場所種別(m_code:0010)
   * @param limit 検索上限
   * @return 買い物アイテムの履歴を返す
   */
  List<ShoppingItemHistorySuggestionModel> findHistorySuggestions(
      Long householdId, String keyword, String storeType, int limit);
}
