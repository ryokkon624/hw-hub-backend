package com.hwhub.backend.infrastructure.mybatis.repository;

import com.hwhub.backend.domain.model.AnnouncementModel;
import com.hwhub.backend.domain.repository.AnnouncementRepository;
import com.hwhub.backend.infrastructure.mybatis.converter.AnnouncementConverter;
import com.hwhub.backend.infrastructure.mybatis.custom.mapper.AnnouncementCustomMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/** アナウンスバナーの MyBatis リポジトリ実装。 */
@Repository
@RequiredArgsConstructor
public class MyBatisAnnouncementRepository implements AnnouncementRepository {

  private final AnnouncementCustomMapper mapper;

  @Override
  public List<AnnouncementModel> findActiveAt(LocalDateTime now) {
    return mapper.findActiveAt(now).stream().map(AnnouncementConverter::toModel).toList();
  }
}
