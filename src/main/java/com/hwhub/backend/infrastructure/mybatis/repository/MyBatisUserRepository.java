package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.enums.AuthProvider;
import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.DateConverter;
import com.hwhub.backend.infrastructure.mybatis.converter.UserConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.UserCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.UserHouseholdCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUser;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserExample;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.MUserMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisUserRepository implements UserRepository {

  private final MUserMapper mapper;
  private final UserCustomMapper customMapper;
  private final UserHouseholdCustomMapper userHouseholdCustomMapper;

  @Override
  public Optional<UserModel> findByEmail(String email) {
    MUserExample example = new MUserExample();
    example.createCriteria().andEmailEqualTo(email);

    var entities = mapper.selectByExample(example);
    if (entities == null || entities.isEmpty()) {
      return Optional.empty();
    }
    MUser e = entities.getFirst();

    return Optional.of(UserConverter.toModel(e));
  }

  @Override
  public List<HouseholdModel> findHouseholdsByUserId(Long userId) {
    return userHouseholdCustomMapper.selectHouseholdsByUserId(userId);
  }

  @Override
  public Optional<UserModel> findById(Long userId) {
    MUser entity = mapper.selectByPrimaryKey(userId);
    return Optional.ofNullable(entity).map(UserConverter::toModel);
  }

  @Override
  public void updateForEnduser(UserModel user, Long userId, String updateProgram) {
    MUser entity = mapper.selectByPrimaryKey(user.getUserId());
    if (entity == null) {
      return; // 呼び出し側で NotFound ハンドリング
    }

    MUser update = new MUser();
    // PK
    update.setUserId(userId);
    // 更新対象カラム
    update.setDisplayName(user.getDisplayName());
    update.setLocale(user.getLocale());
    update.setUpdateUserId(userId);
    update.setUpdateProgram(updateProgram);

    mapper.updateByPrimaryKeySelective(update);
  }

  @Override
  public void updateProfileImgKey(UserModel model, String program) {
    MUser update = new MUser();
    // PK
    update.setUserId(model.getUserId());
    // 更新対象カラム
    update.setProfileImageKey(model.getProfileImageKey());
    update.setUpdateUserId(model.getUserId());
    update.setUpdateProgram(program);

    mapper.updateByPrimaryKeySelective(update);
  }

  @Override
  public UserModel insert(UserModel model, Long userId, String program) {
    MUser entity = UserConverter.toEntity(model);
    entity.setCreateUserId(userId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(userId);
    entity.setUpdateProgram(program);

    mapper.insertSelective(entity);

    MUser inserted = mapper.selectByPrimaryKey(entity.getUserId());

    return UserConverter.toModel(inserted);
  }

  @Override
  public long countByEmail(String email) {
    MUserExample example = new MUserExample();
    example.createCriteria().andEmailEqualTo(email);

    return mapper.selectByExample(example).size();
  }

  @Override
  public void deactivate(Long userId, String program) {
    MUser update = new MUser();
    update.setUserId(userId);
    update.setIsActive(false);
    update.setUpdateUserId(userId);
    update.setUpdateProgram(program);

    mapper.updateByPrimaryKeySelective(update);
  }

  @Override
  public void updateForReactivation(UserModel user, Long userId, String program) {
    MUser update = new MUser();
    update.setUserId(user.getUserId());
    update.setPasswordHash(user.getPasswordHash());
    update.setDisplayName(user.getDisplayName());
    update.setLocale(user.getLocale());
    update.setProfileImageKey(user.getProfileImageKey());
    update.setIsActive(true);
    update.setUpdateUserId(userId);
    update.setUpdateProgram(program);

    mapper.updateByPrimaryKeySelective(update);
  }

  @Override
  public void markEmailVerified(
      Long userId, LocalDateTime verifiedAt, Long updateUserId, String programTypeCode) {
    MUser update = new MUser();
    update.setUserId(userId);
    update.setIsActive(true);
    update.setEmailVerifiedAt(DateConverter.toDate(verifiedAt));
    update.setUpdateUserId(updateUserId);
    update.setUpdateProgram(programTypeCode);

    mapper.updateByPrimaryKeySelective(update);
  }

  @Override
  public void updatePassword(UserModel model, Long updateUserId, String programTypeCode) {
    MUser update = new MUser();
    update.setUserId(model.getUserId());
    update.setPasswordHash(model.getPasswordHash());
    update.setPasswordChangedAt(DateConverter.toDate(model.getPasswordChangedAt()));
    update.setUpdateUserId(updateUserId);
    update.setUpdateProgram(programTypeCode);

    mapper.updateByPrimaryKeySelective(update);
  }

  @Override
  public Optional<LocalDateTime> findPasswordChangedAt(Long userId) {
    return customMapper.findPasswordChangedAt(userId);
  }

  @Override
  public Optional<UserModel> findByAuthProviderAndAuthProviderId(
      String provider, String providerId) {
    MUserExample example = new MUserExample();
    example.createCriteria().andAuthProviderEqualTo(provider).andAuthProviderIdEqualTo(providerId);

    var entities = mapper.selectByExample(example);
    if (entities == null || entities.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(UserConverter.toModel(entities.getFirst()));
  }

  @Override
  public void updateAuthProvider(
      Long userId, String provider, String providerId, Long updateUserId, String programTypeCode) {
    customMapper.updateAuthProvider(userId, provider, providerId, updateUserId, programTypeCode);
  }

  @Override
  public void linkGoogleAccount(
      Long userId, String googleSub, String email, String displayName, String programTypeCode) {
    customMapper.linkGoogleAccount(
        userId, AuthProvider.GOOGLE.getCode(), googleSub, email, displayName, programTypeCode);
  }

  @Override
  public boolean isNotificationEnabled(Long userId) {
    return this.findById(userId).map(UserModel::isNotificationEnabled).orElse(false);
  }

  @Override
  public boolean updateNotificationEnabled(
      Long userId, boolean enabled, Long operatorUserId, String program) {
    MUser update = new MUser();
    update.setUserId(userId);
    update.setNotificationEnabled(enabled);
    update.setUpdateUserId(operatorUserId);
    update.setUpdateProgram(program);

    mapper.updateByPrimaryKeySelective(update);
    return enabled;
  }

  @Override
  public List<UserModel> searchByEmail(String email) {
    return customMapper.searchByEmail(email).stream().map(UserConverter::toModel).toList();
  }
}
