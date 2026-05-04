package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserEmailVerification;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserEmailVerificationExample;
import java.util.List;

public interface TUserEmailVerificationMapper {
    int deleteByPrimaryKey(Long userEmailVerificationId);

    int insert(TUserEmailVerification row);

    int insertSelective(TUserEmailVerification row);

    List<TUserEmailVerification> selectByExampleWithBLOBs(TUserEmailVerificationExample example);

    List<TUserEmailVerification> selectByExample(TUserEmailVerificationExample example);

    TUserEmailVerification selectByPrimaryKey(Long userEmailVerificationId);

    int updateByPrimaryKeySelective(TUserEmailVerification row);

    int updateByPrimaryKeyWithBLOBs(TUserEmailVerification row);

    int updateByPrimaryKey(TUserEmailVerification row);
}