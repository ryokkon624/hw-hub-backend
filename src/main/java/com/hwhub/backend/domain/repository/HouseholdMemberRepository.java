package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseholdMemberModel;
import java.util.List;
import java.util.Map;

public interface HouseholdMemberRepository {
  List<HouseholdMemberModel> findActiveByHouseholdId(Long householdId);

  /**
   * 複数世帯のアクティブメンバー数を一括取得する。
   *
   * @param householdIds 世帯IDのリスト
   * @return 世帯ID → アクティブメンバー数のMap
   */
  Map<Long, Integer> countActiveMembersByHouseholdIds(List<Long> householdIds);

  boolean existsActiveByHouseholdIdAndUserId(Long householdId, Long userId);

  void insert(HouseholdMemberModel model, Long userId, String program);

  HouseholdMemberModel findById(Long householdId, Long userId);

  void update(HouseholdMemberModel model, Long userId, String program);

  void deleteByHouseholdId(Long householdId);

  void deleteByUserId(Long userId);
}
