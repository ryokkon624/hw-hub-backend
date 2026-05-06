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
   * @return アナウンスモデルリスト
   */
  @Transactional(readOnly = true)
  public List<AnnouncementModel> getAll() {
    return announcementRepository.findAll();
  }

  /**
   * 指定IDのアナウンスを取得する。
   *
   * @param id アナウンスID
   * @return アナウンスモデル
   * @throws IllegalArgumentException 存在しないIDを指定した場合
   */
  @Transactional(readOnly = true)
  public AnnouncementModel getById(Long id) {
    return announcementRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Announcement not found"));
  }

  /**
   * アナウンスを新規登録する。
   *
   * @param model アナウンスモデル
   * @param operatorUserId 操作者ユーザーID
   * @return 登録後のアナウンスモデル
   */
  @Transactional
  public AnnouncementModel create(AnnouncementModel model, Long operatorUserId) {
    return announcementRepository.insert(model, operatorUserId, ProgramType.ONL_ADM_ANN.getCode());
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
