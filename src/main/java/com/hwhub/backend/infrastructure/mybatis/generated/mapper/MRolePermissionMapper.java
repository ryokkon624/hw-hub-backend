package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MRolePermission;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MRolePermissionExample;
import java.util.List;

public interface MRolePermissionMapper {
    int deleteByPrimaryKey(Long rolePermissionId);

    int insert(MRolePermission row);

    int insertSelective(MRolePermission row);

    List<MRolePermission> selectByExample(MRolePermissionExample example);

    MRolePermission selectByPrimaryKey(Long rolePermissionId);

    int updateByPrimaryKeySelective(MRolePermission row);

    int updateByPrimaryKey(MRolePermission row);
}