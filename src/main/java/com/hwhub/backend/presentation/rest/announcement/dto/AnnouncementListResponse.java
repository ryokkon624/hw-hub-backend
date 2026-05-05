package com.hwhub.backend.presentation.rest.announcement.dto;

import java.util.List;

/** アナウンスバナー一覧レスポンス。 */
public class AnnouncementListResponse {

  private List<AnnouncementDto> announcements;

  public AnnouncementListResponse() {}

  public AnnouncementListResponse(List<AnnouncementDto> announcements) {
    this.announcements = announcements;
  }

  public List<AnnouncementDto> getAnnouncements() {
    return announcements;
  }

  public void setAnnouncements(List<AnnouncementDto> announcements) {
    this.announcements = announcements;
  }
}
