package com.hwhub.backend.domain.model;

import java.util.Objects;
import lombok.Getter;

@Getter
public class HouseholdModel {
  private Long householdId;
  private String name;
  private Long ownerUserId;

  /**
   * 全プロパティを引数に取るコンストラクタ。
   *
   * @param householdId 世帯ID
   * @param name 世帯名
   * @param ownerUserId 世帯所有者
   */
  private HouseholdModel(Long householdId, String name, Long ownerUserId) {
    this.householdId = householdId;
    this.name = name;
    this.ownerUserId = ownerUserId;
  }

  /**
   * 再構築・永続化用。infrastructure層からのみ呼び出されることを想定。
   *
   * @param householdId 世帯ID
   * @param name 世帯名
   * @param ownerUserId 世帯所有者
   * @return インスタンスを返す。
   */
  public static HouseholdModel reconstruct(Long householdId, String name, Long ownerUserId) {
    return new HouseholdModel(householdId, name, ownerUserId);
  }

    /**
     * 新規追加時のファクトリメソッド。
     *
     * @param name 世帯名
     * @param ownerUserId 世帯所有者
     * @return インスタンスを返す。
     */
  public static HouseholdModel create(String name, Long ownerUserId) {
    return new HouseholdModel(null, name, ownerUserId);
  }

  public boolean isOwner(Long userId) {
    return Objects.equals(this.ownerUserId, userId);
  }

  public void changeName(String name) {
    this.name = name;
  }
}
