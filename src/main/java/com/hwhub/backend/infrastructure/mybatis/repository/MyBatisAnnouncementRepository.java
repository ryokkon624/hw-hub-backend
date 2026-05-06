package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.AnnouncementModel;
import com.hwhub.backend.domain.repository.AnnouncementRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.AnnouncementConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.AnnouncementCustomMapper;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MAnnouncement;
import com.hwhub.backend.infrastructure.mybatis.generated.mapper.MAnnouncementMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/** アナウンスバナーの MyBatis リポジトリ実装。 */
@Repository
@RequiredArgsConstructor
public class MyBatisAnnouncementRepository implements AnnouncementRepository {

  private final AnnouncementCustomMapper customMapper;
  private final MAnnouncementMapper mapper;

  @Override
  public List<AnnouncementModel> findActiveAt(LocalDateTime now) {
    return customMapper.findActiveAt(now).stream().map(AnnouncementConverter::toModel).toList();
  }

  @Override
  public List<AnnouncementModel> findAll() {
    return customMapper.findAll().stream().map(AnnouncementConverter::toModel).toList();
  }

  @Override
  public Optional<AnnouncementModel> findById(Long id) {
    MAnnouncement entity = mapper.selectByPrimaryKey(id);
    return Optional.ofNullable(AnnouncementConverter.toModel(entity));
  }

  @Override
  public AnnouncementModel insert(AnnouncementModel model, Long operatorUserId, String program) {
    MAnnouncement entity = AnnouncementConverter.toEntity(model);
    entity.setCreateUserId(operatorUserId);
    entity.setCreateProgram(program);
    entity.setUpdateUserId(operatorUserId);
    entity.setUpdateProgram(program);
    mapper.insertSelective(entity);
    return AnnouncementConverter.toModel(entity);
  }

  @Override
  public void update(AnnouncementModel model, Long operatorUserId, String program) {
    MAnnouncement entity = AnnouncementConverter.toEntity(model);
    customMapper.update(entity, operatorUserId, program);
  }

  @Override
  public void delete(Long id, Long operatorUserId, String program) {
    mapper.deleteByPrimaryKey(id);
  }
}
