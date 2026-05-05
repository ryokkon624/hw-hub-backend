package com.hwhub.backend.infrastructure.mybatis.custom.mapper;

import com.hwhub.backend.infrastructure.mybatis.generated.entity.MAnnouncement;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/** アナウンスバナーのカスタムMapperインターフェース。 */
public interface AnnouncementCustomMapper {

  /**
   * 指定日時に有効なアナウンス一覧を取得する。
   *
   * @param now 基準日時（start_at &lt;= now &lt; end_at）
   * @return 有効期間内のアナウンスリスト
   */
  List<MAnnouncement> findActiveAt(@Param("now") LocalDateTime now);
}
