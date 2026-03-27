package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.inquiry.AdminInquirySearchCondition;
import com.hwhub.backend.domain.model.inquiry.DailyInquiryMessage;
import com.hwhub.backend.domain.model.inquiry.DailyInquiryStatus;
import com.hwhub.backend.domain.model.inquiry.InquiryStatusSummary;
import com.hwhub.backend.infrastructure.mybatis.custom.entity.AdminInquiryEntity;
import com.hwhub.backend.infrastructure.mybatis.custom.entity.InquiryWithMessagesEntity;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiry;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InquiryCustomMapper {

  InquiryWithMessagesEntity findInquiryWithMessages(@Param("inquiryId") long inquiryId);

  List<TInquiry> findByUserId(@Param("userId") long userId);

  List<AdminInquiryEntity> findPendingStaff();

  List<AdminInquiryEntity> searchInquiries(AdminInquirySearchCondition condition);

  /** 日別・ステータス別の問い合わせ件数を集計する */
  List<DailyInquiryStatus> findDailyStats(@Param("days") int days);

  /** 日別・送信者タイプ別のメッセージ件数を集計する */
  List<DailyInquiryMessage> findMessageDailyStats(@Param("days") int days);

  /** ステータス別件数サマリーを取得する */
  InquiryStatusSummary findStatusSummary();
}
