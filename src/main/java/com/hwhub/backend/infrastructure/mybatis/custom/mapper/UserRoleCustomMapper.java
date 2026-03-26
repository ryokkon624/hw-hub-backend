package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserRole;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleCustomMapper {
  List<MUserRole> findByUserId(@Param("userId") long userId);

  void softDelete(
      @Param("userId") long userId,
      @Param("role") String role,
      @Param("operatorUserId") long operatorUserId,
      @Param("program") String program);

  boolean exists(@Param("userId") long userId, @Param("role") String role);

  List<String> findPermissionsByRoles(@Param("roles") List<String> roles);
}
