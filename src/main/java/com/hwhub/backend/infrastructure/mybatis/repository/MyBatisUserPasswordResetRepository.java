package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.UserPasswordResetModel;
import com.hwhub.backend.domain.repository.UserPasswordResetRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.UserPasswordResetConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.UserPasswordResetCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserPasswordReset;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.TUserPasswordResetMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisUserPasswordResetRepository implements UserPasswordResetRepository {

  private final UserPasswordResetCustomMapper customMapper;
  private final TUserPasswordResetMapper mapper;

  @Override
  public Optional<LocalDateTime> findLatestRequestedAt(Long userId) {
    return customMapper.findLatestRequestedAt(userId);
  }

  @Override
  public Optional<UserPasswordResetModel> findUsableByTokenHash(
      byte[] tokenHash, LocalDateTime now) {

    Optional<TUserPasswordReset> entity = customMapper.findUsableByTokenHash(tokenHash, now);

    return entity.map(UserPasswordResetConverter::toModel);
  }

  @Override
  public Long insert(UserPasswordResetModel model, Long createUserId, String createProgram) {
    TUserPasswordReset entity = UserPasswordResetConverter.toEntity(model);
    entity.setCreateUserId(createUserId);
    entity.setCreateProgram(createProgram);
    entity.setUpdateUserId(createUserId);
    entity.setUpdateProgram(createProgram);

    mapper.insertSelective(entity);

    return entity.getUserPasswordResetId();
  }

  @Override
  public int markUsedIfUnused(
      Long userPasswordResetId, LocalDateTime usedAt, Long updateUserId, String updateProgram) {
    return customMapper.markUsedIfUnused(userPasswordResetId, usedAt, updateUserId, updateProgram);
  }

  @Override
  public int countRequestedOnDate(Long userId, LocalDateTime start, LocalDateTime end) {
    return customMapper.countRequestedOnDate(userId, start, end);
  }
}
