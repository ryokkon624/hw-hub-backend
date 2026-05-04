package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiry;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiryExample;
import java.util.List;

public interface TInquiryMapper {
    int deleteByPrimaryKey(Long inquiryId);

    int insert(TInquiry row);

    int insertSelective(TInquiry row);

    List<TInquiry> selectByExample(TInquiryExample example);

    TInquiry selectByPrimaryKey(Long inquiryId);

    int updateByPrimaryKeySelective(TInquiry row);

    int updateByPrimaryKey(TInquiry row);
}