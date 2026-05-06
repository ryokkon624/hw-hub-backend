package com.hwhub.backend.presentation.rest.admin.announcement;

import com.hwhub.backend.application.service.announcement.AdminAnnouncementService;
import com.hwhub.backend.application.service.announcement.AnnouncementSummary;
import com.hwhub.backend.domain.enums.Permission;
import com.hwhub.backend.domain.model.AnnouncementModel;
import com.hwhub.backend.presentation.rest.admin.announcement.dto.AdminAnnouncementRequest;
import com.hwhub.backend.presentation.rest.admin.announcement.dto.AdminAnnouncementResponse;
import com.hwhub.backend.security.CurrentUserId;
import com.hwhub.backend.security.RequiresPermission;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** アナウンスマスタメンテのAPIコントローラ。 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/announcements")
public class AdminAnnouncementController {

  private final AdminAnnouncementService service;

  /** 一覧取得 */
  @RequiresPermission(Permission.ANNOUNCEMENT_MANAGEMENT)
  @GetMapping
  public List<AdminAnnouncementResponse> getAll() {
    return service.getAll().stream().map(AdminAnnouncementResponse::from).toList();
  }

  /** ID指定取得 */
  @RequiresPermission(Permission.ANNOUNCEMENT_MANAGEMENT)
  @GetMapping("/{id}")
  public AdminAnnouncementResponse getById(@PathVariable("id") Long id) {
    AnnouncementSummary summary = service.getById(id);
    return AdminAnnouncementResponse.from(summary);
  }

  /** 新規登録 */
  @RequiresPermission(Permission.ANNOUNCEMENT_MANAGEMENT)
  @PostMapping
  public AdminAnnouncementResponse create(
      @RequestBody @Valid AdminAnnouncementRequest request, @CurrentUserId Long operatorUserId) {
    AnnouncementModel model = request.toModel();
    return AdminAnnouncementResponse.from(service.create(model, operatorUserId));
  }

  /** 更新 */
  @RequiresPermission(Permission.ANNOUNCEMENT_MANAGEMENT)
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(
      @PathVariable("id") Long id,
      @RequestBody @Valid AdminAnnouncementRequest request,
      @CurrentUserId Long operatorUserId) {
    AnnouncementModel model = request.toModel(id);
    service.update(model, operatorUserId);
  }

  /** 削除 */
  @RequiresPermission(Permission.ANNOUNCEMENT_MANAGEMENT)
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id, @CurrentUserId Long operatorUserId) {
    service.delete(id, operatorUserId);
  }
}
