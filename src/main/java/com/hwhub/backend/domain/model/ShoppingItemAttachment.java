package com.hwhub.backend.domain.model;

import lombok.Getter;

@Getter
public class ShoppingItemAttachment {

  private Long id;
  private Long shoppingItemId;
  private String fileKey;
  private String fileName;
  private String mimeType;
  private Integer sortOrder;
  private String imageUrl;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param id 買い物アイテム添付ファイルID
   * @param shoppingItemId 買い物アイテムID
   * @param fileKey ファイルストレージキー
   * @param fileName ファイル名
   * @param mimeType MIMEタイプ
   * @param sortOrder 表示順
   * @param imageUrl 画像のURL
   */
  private ShoppingItemAttachment(
      Long id,
      Long shoppingItemId,
      String fileKey,
      String fileName,
      String mimeType,
      Integer sortOrder,
      String imageUrl) {
    this.id = id;
    this.shoppingItemId = shoppingItemId;
    this.fileKey = fileKey;
    this.fileName = fileName;
    this.mimeType = mimeType;
    this.sortOrder = sortOrder;
    this.imageUrl = imageUrl;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param id 買い物アイテム添付ファイルID
   * @param shoppingItemId 買い物アイテムID
   * @param fileKey ファイルストレージキー
   * @param fileName ファイル名
   * @param mimeType MIMEタイプ
   * @param sortOrder 表示順
   * @return インスタンスを返す。
   */
  public static ShoppingItemAttachment reconstruct(
      Long id,
      Long shoppingItemId,
      String fileKey,
      String fileName,
      String mimeType,
      Integer sortOrder) {
    return new ShoppingItemAttachment(
        id, shoppingItemId, fileKey, fileName, mimeType, sortOrder, null);
  }

  public static ShoppingItemAttachment create(
      Long shoppingItemId, String fileKey, String fileName, String mimeType, Integer sortOrder) {
    return new ShoppingItemAttachment(
        null, shoppingItemId, fileKey, fileName, mimeType, sortOrder, null);
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
