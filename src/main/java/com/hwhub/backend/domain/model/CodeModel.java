package com.hwhub.backend.domain.model;

import lombok.Getter;

@Getter
public class CodeModel {

  private String codeType;
  private String codeTypeName;
  private String codeTypeNameEn;
  private String codeValue;
  private String name;
  private String displayNameJa;
  private String displayNameEn;
  private String displayNameEs;
  private String remarks;
  private String displayOrder;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param codeType コード種別
   * @param codeTypeName コード種別名
   * @param codeTypeNameEn コード種別名(英語)
   * @param codeValue コード値
   * @param name 名称
   * @param displayNameJa 表示名(日本語)
   * @param displayNameEn 表示名(英語)
   * @param displayNameEs 表示名(スペイン語)
   * @param remarks 備考
   * @param displayOrder 表示順
   */
  private CodeModel(
      String codeType,
      String codeTypeName,
      String codeTypeNameEn,
      String codeValue,
      String name,
      String displayNameJa,
      String displayNameEn,
      String displayNameEs,
      String remarks,
      String displayOrder) {
    this.codeType = codeType;
    this.codeTypeName = codeTypeName;
    this.codeTypeNameEn = codeTypeNameEn;
    this.codeValue = codeValue;
    this.name = name;
    this.displayNameJa = displayNameJa;
    this.displayNameEn = displayNameEn;
    this.displayNameEs = displayNameEs;
    this.remarks = remarks;
    this.displayOrder = displayOrder;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param codeType コード種別
   * @param codeTypeName コード種別名
   * @param codeTypeNameEn コード種別名(英語)
   * @param codeValue コード値
   * @param name 名称
   * @param displayNameJa 表示名(日本語)
   * @param displayNameEn 表示名(英語)
   * @param displayNameEs 表示名(スペイン語)
   * @param remarks 備考
   * @param displayOrder 表示順
   * @return インスタンスを返す。
   */
  public static CodeModel reconstruct(
      String codeType,
      String codeTypeName,
      String codeTypeNameEn,
      String codeValue,
      String name,
      String displayNameJa,
      String displayNameEn,
      String displayNameEs,
      String remarks,
      String displayOrder) {
    return new CodeModel(
        codeType,
        codeTypeName,
        codeTypeNameEn,
        codeValue,
        name,
        displayNameJa,
        displayNameEn,
        displayNameEs,
        remarks,
        displayOrder);
  }
}
