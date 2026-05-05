package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.AnnouncementModel;
import java.time.LocalDateTime;
import java.util.List;

/** アナウンスバナーのリポジトリインターフェース。 */
public interface AnnouncementRepository {

  /**
   * 指定日時に有効なアナウンス一覧を取得する。
   *
   * @param now 基準日時
   * @return 有効期間内のアナウンスリスト（start_at <= now < end_at）
   */
  List<AnnouncementModel> findActiveAt(LocalDateTime now);
}
