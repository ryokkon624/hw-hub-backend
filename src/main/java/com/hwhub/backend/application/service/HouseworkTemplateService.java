package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel;
import com.hwhub.backend.domain.repository.HouseworkTemplateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 家事テンプレートに関するユースケース実行クラス。 */
@Service
@RequiredArgsConstructor
public class HouseworkTemplateService {

  private final HouseworkTemplateRepository repository;

  /** 家事テンプレート全件取得 */
  @Transactional(readOnly = true)
  public List<HouseworkTemplateModel> findAll() {
    return repository.findAll();
  }
}
