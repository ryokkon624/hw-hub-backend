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

  /**
   * 全件取得する（作成日時降順）。
   *
   * @return アナウンスリスト
   */
  List<MAnnouncement> findAll();

  /**
   * 更新する（WHOカラムを手動更新）。
   *
   * @param entity 更新エンティティ
   * @param operatorUserId 操作者ユーザーID
   * @param program 操作プログラム
   */
  void update(
      @Param("entity") MAnnouncement entity,
      @Param("operatorUserId") Long operatorUserId,
      @Param("program") String program);
}
