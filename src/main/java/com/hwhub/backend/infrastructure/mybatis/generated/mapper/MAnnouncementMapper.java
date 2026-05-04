package com.hwhub.backend.infrastructure.mybatis.generated.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MAnnouncement;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MAnnouncementExample;
import java.util.List;

public interface MAnnouncementMapper {
    int deleteByPrimaryKey(Long announcementId);

    int insert(MAnnouncement row);

    int insertSelective(MAnnouncement row);

    List<MAnnouncement> selectByExampleWithBLOBs(MAnnouncementExample example);

    List<MAnnouncement> selectByExample(MAnnouncementExample example);

    MAnnouncement selectByPrimaryKey(Long announcementId);

    int updateByPrimaryKeySelective(MAnnouncement row);

    int updateByPrimaryKeyWithBLOBs(MAnnouncement row);

    int updateByPrimaryKey(MAnnouncement row);
}