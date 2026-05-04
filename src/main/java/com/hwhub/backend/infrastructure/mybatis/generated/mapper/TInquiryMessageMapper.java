package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiryMessage;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiryMessageExample;
import java.util.List;

public interface TInquiryMessageMapper {
    int deleteByPrimaryKey(Long messageId);

    int insert(TInquiryMessage row);

    int insertSelective(TInquiryMessage row);

    List<TInquiryMessage> selectByExampleWithBLOBs(TInquiryMessageExample example);

    List<TInquiryMessage> selectByExample(TInquiryMessageExample example);

    TInquiryMessage selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(TInquiryMessage row);

    int updateByPrimaryKeyWithBLOBs(TInquiryMessage row);

    int updateByPrimaryKey(TInquiryMessage row);
}