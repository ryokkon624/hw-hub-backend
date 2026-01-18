package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.UserEmailVerificationModel;
import com.hwhub.backend.domain.repository.UserEmailVerificationRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.UserEmailVerificationConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.UserEmailVerificationCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserEmailVerification;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.TUserEmailVerificationMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisUserEmailVerificationRepository implements UserEmailVerificationRepository {

  private final UserEmailVerificationCustomMapper customMapper;
  private final TUserEmailVerificationMapper mapper;

  @Override
  public Long insert(UserEmailVerificationModel model, Long createUserId, String createProgram) {
    TUserEmailVerification entity = UserEmailVerificationConverter.toEntity(model);
    entity.setCreateUserId(createUserId);
    entity.setCreateProgram(createProgram);
    entity.setUpdateUserId(createUserId);
    entity.setUpdateProgram(createProgram);

    mapper.insertSelective(entity);

    return entity.getUserEmailVerificationId();
  }

  @Override
  public int countRequestedSince(Long userId, LocalDateTime since) {
    return customMapper.countRequestedSince(userId, since);
  }

  @Override
  public Optional<LocalDateTime> findLatestRequestedAt(Long userId) {
    return customMapper.findLatestRequestedAt(userId);
  }

  @Override
  public Optional<UserEmailVerificationModel> findUsableByTokenHash(
      byte[] tokenHash, LocalDateTime now) {
    return customMapper.findUsableByTokenHash(tokenHash, now);
  }

  @Override
  public void markUsed(
      Long userEmailVerificationId, LocalDateTime usedAt, Long updateUserId, String updateProgram) {
    customMapper.markUsed(userEmailVerificationId, usedAt, updateUserId, updateProgram);
  }
}
