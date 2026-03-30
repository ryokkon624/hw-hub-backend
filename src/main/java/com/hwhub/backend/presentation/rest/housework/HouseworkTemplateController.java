package com.hwhub.backend.presentation.rest.housework;

import com.hwhub.backend.application.service.HouseworkTemplateService;
import com.hwhub.backend.presentation.rest.housework.dto.HouseworkTemplateResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/housework-templates")
public class HouseworkTemplateController {

  private final HouseworkTemplateService service;

  /** 家事テンプレート全件取得（認証済みユーザーなら誰でも取得可） */
  @GetMapping
  public List<HouseworkTemplateResponse> getAll() {
    return service.findAll().stream().map(HouseworkTemplateResponse::from).toList();
  }
}
