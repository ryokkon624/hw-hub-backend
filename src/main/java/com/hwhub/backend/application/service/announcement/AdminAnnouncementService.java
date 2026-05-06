package com.hwhub.backend.application.service.announcement;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.AnnouncementModel;
import com.hwhub.backend.domain.repository.AnnouncementRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** アナウンスマスタメンテのアプリケーションサービス。 */
@Service
@RequiredArgsConstructor
public class AdminAnnouncementService {

  private final AnnouncementRepository announcementRepository;

  /**
   * アナウンス全件を取得する。
   *
   * @return アナウンスサマリーリスト
   */
  @Transactional(readOnly = true)
  public List<AnnouncementSummary> getAll() {
    return announcementRepository.findAll().stream().map(AnnouncementSummary::from).toList();
  }

  /**
   * 指定IDのアナウンスを取得する。
   *
   * @param id アナウンスID
   * @return アナウンスサマリー
   * @throws IllegalArgumentException 存在しないIDを指定した場合
   */
  @Transactional(readOnly = true)
  public AnnouncementSummary getById(Long id) {
    AnnouncementModel model =
        announcementRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Announcement not found: " + id));
    return AnnouncementSummary.from(model);
  }

  /**
   * アナウンスを新規登録する。
   *
   * @param model アナウンスモデル
   * @param operatorUserId 操作者ユーザーID
   * @return 登録後のアナウンスサマリー
   */
  @Transactional
  public AnnouncementSummary create(AnnouncementModel model, Long operatorUserId) {
    AnnouncementModel saved =
        announcementRepository.insert(model, operatorUserId, ProgramType.ONL_ADM_ANN.getCode());
    return AnnouncementSummary.from(saved);
  }

  /**
   * アナウンスを更新する。
   *
   * @param model アナウンスモデル
   * @param operatorUserId 操作者ユーザーID
   */
  @Transactional
  public void update(AnnouncementModel model, Long operatorUserId) {
    announcementRepository.update(model, operatorUserId, ProgramType.ONL_ADM_ANN.getCode());
  }

  /**
   * アナウンスを削除する。
   *
   * @param id アナウンスID
   * @param operatorUserId 操作者ユーザーID
   */
  @Transactional
  public void delete(Long id, Long operatorUserId) {
    announcementRepository.delete(id, operatorUserId, ProgramType.ONL_ADM_ANN.getCode());
  }
}
