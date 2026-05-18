package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.HouseholdMemberModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseholdMemberCustomMapper {
  List<HouseholdMemberModel> findActiveByHouseholdId(Long householdId);

  /**
   * 複数世帯のアクティブメンバー数を一括取得する。
   *
   * @param householdIds 世帯IDのリスト
   * @return 世帯ID, count のリスト
   */
  List<HouseholdActiveMemberCount> countActiveMembersByHouseholdIds(
      @Param("householdIds") List<Long> householdIds);

  int countActiveByHouseholdIdAndUserId(
      @Param("householdId") Long householdId, @Param("userId") Long userId);

  void deleteByHouseholdId(@Param("householdId") Long householdId);

  void deleteByUserId(@Param("userId") Long userId);

  record HouseholdActiveMemberCount(Long householdId, int memberCount) {}
}
