package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserPasswordReset;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserPasswordResetExample;
import java.util.List;

public interface TUserPasswordResetMapper {
  int deleteByPrimaryKey(Long userPasswordResetId);

  int insert(TUserPasswordReset row);

  int insertSelective(TUserPasswordReset row);

  List<TUserPasswordReset> selectByExampleWithBLOBs(TUserPasswordResetExample example);

  List<TUserPasswordReset> selectByExample(TUserPasswordResetExample example);

  TUserPasswordReset selectByPrimaryKey(Long userPasswordResetId);

  int updateByPrimaryKeySelective(TUserPasswordReset row);

  int updateByPrimaryKeyWithBLOBs(TUserPasswordReset row);

  int updateByPrimaryKey(TUserPasswordReset row);
}
