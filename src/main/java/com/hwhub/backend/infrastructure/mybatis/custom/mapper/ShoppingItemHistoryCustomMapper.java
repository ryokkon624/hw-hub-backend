package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.ShoppingItemHistorySuggestionModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShoppingItemHistoryCustomMapper {

  List<ShoppingItemHistorySuggestionModel> selectHistorySuggestions(
      @Param("householdId") Long householdId,
      @Param("keyword") String keyword,
      @Param("storeType") String storeType,
      @Param("limit") int limit);
}
