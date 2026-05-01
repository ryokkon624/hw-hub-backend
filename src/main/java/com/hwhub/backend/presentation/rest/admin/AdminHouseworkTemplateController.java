package com.hwhub.backend.presentation.rest.admin;

import com.hwhub.backend.application.service.HouseworkTemplateService;
import com.hwhub.backend.domain.enums.Permission;
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateId;
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel;
import com.hwhub.backend.presentation.rest.admin.dto.AdminHouseworkTemplateRequest;
import com.hwhub.backend.presentation.rest.admin.dto.AdminHouseworkTemplateResponse;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/housework-templates")
public class AdminHouseworkTemplateController {

  private final HouseworkTemplateService service;

  /** 一覧取得 */
  @RequiresPermission(Permission.SYSTEM_TEMPLATE_MANAGEMENT)
  @GetMapping
  public List<AdminHouseworkTemplateResponse> getAll() {
    return service.findAll().stream().map(AdminHouseworkTemplateResponse::from).toList();
  }

  /** 新規登録 */
  @RequiresPermission(Permission.SYSTEM_TEMPLATE_MANAGEMENT)
  @PostMapping
  public AdminHouseworkTemplateResponse create(
      @RequestBody @Valid AdminHouseworkTemplateRequest request,
      @CurrentUserId Long operatorUserId) {
    HouseworkTemplateModel model = request.toModel();
    return AdminHouseworkTemplateResponse.from(service.create(model, operatorUserId));
  }

  /** 更新 */
  @RequiresPermission(Permission.SYSTEM_TEMPLATE_MANAGEMENT)
  @PutMapping("/{id}")
  public void update(
      @PathVariable("id") Long id,
      @RequestBody @Valid AdminHouseworkTemplateRequest request,
      @CurrentUserId Long operatorUserId) {
    HouseworkTemplateModel model = request.toModel(new HouseworkTemplateId(id));
    service.update(model, operatorUserId);
  }

  /** 削除 */
  @RequiresPermission(Permission.SYSTEM_TEMPLATE_MANAGEMENT)
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id, @CurrentUserId Long operatorUserId) {
    service.delete(new HouseworkTemplateId(id), operatorUserId);
  }
}
