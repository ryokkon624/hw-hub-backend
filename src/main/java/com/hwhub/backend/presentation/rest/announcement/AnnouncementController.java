package com.hwhub.backend.presentation.rest.announcement;

import com.hwhub.backend.application.service.AnnouncementService;
import com.hwhub.backend.application.service.AnnouncementService.AnnouncementSummary;
import com.hwhub.backend.presentation.rest.announcement.dto.AnnouncementDto;
import com.hwhub.backend.presentation.rest.announcement.dto.AnnouncementListResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * アナウンスバナー関連のAPIコントローラ。
 *
 * <p>認証済みユーザー向けに有効なアナウンス情報を返す。
 */
@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

  private final AnnouncementService announcementService;

  /**
   * 現在有効なアナウンス一覧を取得する。
   *
   * @return 有効期間内のアナウンスリスト
   */
  @GetMapping("/active")
  public AnnouncementListResponse getActiveAnnouncements() {
    LocalDateTime now = LocalDateTime.now();
    List<AnnouncementSummary> summaries = announcementService.getActiveAnnouncements(now);
    List<AnnouncementDto> dtos = summaries.stream().map(AnnouncementDto::from).toList();
    return new AnnouncementListResponse(dtos);
  }
}
