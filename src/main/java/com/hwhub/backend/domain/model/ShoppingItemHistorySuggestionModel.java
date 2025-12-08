package com.hwhub.backend.domain.model;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 買い物アイテムの履歴サジェスト用モデル name + storeType ごとに集約された代表レコード。 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShoppingItemHistorySuggestionModel {

  /** 添付を引き継ぐための「元アイテム」の shopping_item_id → 最新の purchased_at のレコードの ID */
  private Long sourceShoppingItemId;

  /** 品名 */
  private String name;

  /** メモ */
  private String memo;

  /** 購入場所種別（m_code:0010 code_value） */
  private String storeType;

  /** 最終購入日（最新 purchased_at） */
  private LocalDate lastPurchasedDate;

  /** 購入回数（同じ name + storeType のインスタンス数） */
  private Long purchaseCount;

  /** お気に入り */
  private String favorite;
}
