package com.hwhub.backend.domain.model;

import com.hwhub.backend.domain.enums.FavoriteFlag;
import com.hwhub.backend.domain.enums.ShoppingItemStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

/** 買い物アイテムModel */
@Getter
public class ShoppingItemModel {
  private final Long shoppingItemId;
  private final Long householdId;
  private String name;
  private String memo;
  private String storeType;
  private String status;
  private String favorite;
  private LocalDate purchasedAt;
  private LocalDateTime createdAt;
  private Boolean hasImage;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param shoppingItemId 買い物アイテムID
   * @param householdId 世帯ID
   * @param name 品名
   * @param memo メモ
   * @param storeType 購入場所種別
   * @param status ステータス
   * @param favorite お気に入り
   * @param purchasedAt 購入完了日
   * @param createdAt 作成日時
   * @param hasImage 画像を持っているか
   */
  private ShoppingItemModel(
      Long shoppingItemId,
      Long householdId,
      String name,
      String memo,
      String storeType,
      String status,
      String favorite,
      LocalDate purchasedAt,
      LocalDateTime createdAt,
      Boolean hasImage) {
    this.shoppingItemId = shoppingItemId;
    this.householdId = householdId;
    this.name = name;
    this.memo = memo;
    this.storeType = storeType;
    this.status = status;
    this.favorite = favorite;
    this.purchasedAt = purchasedAt;
    this.createdAt = createdAt;
    this.hasImage = hasImage;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param shoppingItemId 買い物アイテムID
   * @param householdId 世帯ID
   * @param name 品名
   * @param memo メモ
   * @param storeType 購入場所種別
   * @param status ステータス
   * @param favorite お気に入り
   * @param purchasedAt 購入完了日
   * @param createdAt 作成日時
   * @param hasImage 画像を持っているか
   * @return インスタンスを返す。
   */
  public static ShoppingItemModel reconstruct(
      Long shoppingItemId,
      Long householdId,
      String name,
      String memo,
      String storeType,
      String status,
      String favorite,
      LocalDate purchasedAt,
      LocalDateTime createdAt,
      Boolean hasImage) {
    return new ShoppingItemModel(
        shoppingItemId,
        householdId,
        name,
        memo,
        storeType,
        status,
        favorite,
        purchasedAt,
        createdAt,
        hasImage);
  }

  /**
   * 買い物アイテムを生成する。
   *
   * @param householdId 世帯ID
   * @param name 品名
   * @param memo メモ
   * @param storeType 購入場所種別
   * @return インスタンスを返す。
   */
  public static ShoppingItemModel create(
      Long householdId, String name, String memo, String storeType) {
    return new ShoppingItemModel(
        null,
        householdId,
        name,
        memo,
        storeType,
        ShoppingItemStatus.NOT_PURCHASED.getCode(),
        FavoriteFlag.NORMAL.getCode(),
        null,
        null,
        false);
  }

  public void notPurcased() {
    this.status = ShoppingItemStatus.NOT_PURCHASED.getCode();
    this.purchasedAt = null;
  }

  public void inBasket() {
    this.status = ShoppingItemStatus.IN_BASKET.getCode();
    this.purchasedAt = null;
  }

  public void purchased() {
    this.status = ShoppingItemStatus.PURCHASED.getCode();
    this.purchasedAt = LocalDate.now();
  }

  public void favorite() {
    this.favorite = FavoriteFlag.FAVORITE.getCode();
  }

  public void clearFavorite() {
    this.favorite = FavoriteFlag.NORMAL.getCode();
  }

  public void update(String name, String memo, String storeType) {
    this.name = name;
    this.memo = memo;
    this.storeType = storeType;
  }
}
