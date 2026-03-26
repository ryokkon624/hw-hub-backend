package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.domain.model.inquiry.AdminInquirySearchCondition;
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
}
