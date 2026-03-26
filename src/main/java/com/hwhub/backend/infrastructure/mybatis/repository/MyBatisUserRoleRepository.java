package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.enums.UserRole;
import com.hwhub.backend.domain.model.UserRoleModel;
import com.hwhub.backend.domain.repository.UserRoleRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.UserRoleConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.UserRoleCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserRole;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.MUserRoleMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisUserRoleRepository implements UserRoleRepository {

  private final MUserRoleMapper mapper;
  private final UserRoleCustomMapper customMapper;

  @Override
  public List<UserRoleModel> findByUserId(Long userId) {
    return customMapper.findByUserId(userId).stream().map(UserRoleConverter::toModel).toList();
  }

  @Override
  public void insert(Long userId, UserRole role, Long operatorUserId, String program) {
    MUserRole entity = new MUserRole();
    entity.setUserId(userId);
    entity.setRole(role.getCode());
    entity.setCreateUserId(operatorUserId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(operatorUserId);
    entity.setUpdateProgram(program);
    mapper.insertSelective(entity);
  }

  @Override
  public void delete(Long userId, UserRole role, Long operatorUserId, String program) {
    customMapper.softDelete(userId, role.getCode(), operatorUserId, program);
  }

  @Override
  public boolean exists(Long userId, UserRole role) {
    return customMapper.exists(userId, role.getCode());
  }
}
