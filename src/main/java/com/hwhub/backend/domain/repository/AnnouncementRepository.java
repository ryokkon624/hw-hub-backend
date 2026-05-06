package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.AnnouncementModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/** アナウンスバナーのリポジトリインターフェース。 */
public interface AnnouncementRepository {

  /**
   * 指定日時に有効なアナウンス一覧を取得する。
   *
   * @param now 基準日時
   * @return 有効期間内のアナウンスリスト（start_at <= now < end_at）
   */
  List<AnnouncementModel> findActiveAt(LocalDateTime now);

  /**
   * 全件取得する。
   *
   * @return アナウンスリスト
   */
  List<AnnouncementModel> findAll();

  /**
   * IDで取得する。
   *
   * @param id アナウンスID
   * @return アナウンス（存在しない場合は空）
   */
  Optional<AnnouncementModel> findById(Long id);

  /**
   * 新規登録する。
   *
   * @param model アナウンスモデル
   * @param operatorUserId 操作者ユーザーID
   * @param program 操作プログラム
   * @return 登録後のアナウンスモデル
   */
  AnnouncementModel insert(AnnouncementModel model, Long operatorUserId, String program);

  /**
   * 更新する。
   *
   * @param model アナウンスモデル
   * @param operatorUserId 操作者ユーザーID
   * @param program 操作プログラム
   */
  void update(AnnouncementModel model, Long operatorUserId, String program);

  /**
   * 削除する。
   *
   * @param id アナウンスID
   * @param operatorUserId 操作者ユーザーID
   * @param program 操作プログラム
   */
  void delete(Long id, Long operatorUserId, String program);
}
