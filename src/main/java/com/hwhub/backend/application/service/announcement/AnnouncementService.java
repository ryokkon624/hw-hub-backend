package com.hwhub.backend.application.service.announcement;

import com.hwhub.backend.domain.repository.AnnouncementRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** アナウンスバナーのアプリケーションサービス。 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

  private final AnnouncementRepository announcementRepository;

  /**
   * 指定日時に有効なアナウンス一覧を取得する。
   *
   * @param now 基準日時
   * @return 有効なアナウンスのサマリーリスト
   */
  public List<AnnouncementSummary> getActiveAnnouncements(LocalDateTime now) {
    return announcementRepository.findActiveAt(now).stream()
        .map(AnnouncementSummary::from)
        .toList();
  }
}
