package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseworkModel;
import java.util.List;

public interface HouseworkRepository {

  /**
   * 家事1件取得
   */
  HouseworkModel findByHouseworkId(Long houseworkId);

  /**
   * 家事一覧取得
   *
   * @param householdId 世帯ID
   */
  List<HouseworkModel> findByHouseholdId(Long householdId);

  /**
   * 家事登録
   */
  HouseworkModel insert(HouseworkModel model, Long userId, String program);

  /**
   * 家事更新
   */
  HouseworkModel update(HouseworkModel model, Long userId, String program);

  /**
   * 家事削除（必要なら）
   */
  void delete(Long houseworkId);

  void clearAssignee(Long householdId, Long userId, Long loginUserId, String program);
}
