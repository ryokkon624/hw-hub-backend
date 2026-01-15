package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.domain.repository.UserRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.UserConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.UserHouseholdCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUser;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MUserExample;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.MUserMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisUserRepository implements UserRepository {

  private final MUserMapper mapper;
  private final UserHouseholdCustomMapper userHouseholdCustomMapper;

  private static final String AUTH_PROVIDER_LOCAL = "LOCAL";

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
    entity.setAuthProvider(AUTH_PROVIDER_LOCAL);
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
}
