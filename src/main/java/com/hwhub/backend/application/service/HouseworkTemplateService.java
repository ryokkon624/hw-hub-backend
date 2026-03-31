package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.enums.ProgramType;
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateId;
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

  /**
   * 家事テンプレート全件取得する。
   *
   * @return 家事テンプレート全件
   */
  @Transactional(readOnly = true)
  public List<HouseworkTemplateModel> findAll() {
    return repository.findAll();
  }

  /**
   * 家事テンプレートを新規登録する。
   *
   * @param model 家事テンプレートモデル
   * @param operatorUserId 操作者ユーザーID
   * @return 新規登録された家事テンプレートモデル
   */
  @Transactional
  public HouseworkTemplateModel create(HouseworkTemplateModel model, Long operatorUserId) {
    return repository.insert(model, operatorUserId, ProgramType.ONL_ADM_HW_TP.getCode());
  }

  /**
   * 家事テンプレートを更新する。
   *
   * @param model 家事テンプレートモデル
   * @param operatorUserId 操作者ユーザーID
   */
  @Transactional
  public void update(HouseworkTemplateModel model, Long operatorUserId) {
    repository.update(model, operatorUserId, ProgramType.ONL_ADM_HW_TP.getCode());
  }

  /**
   * 家事テンプレートを削除する。
   *
   * @param id 家事テンプレートID
   * @param operatorUserId 操作者ユーザーID
   */
  @Transactional
  public void delete(HouseworkTemplateId id, Long operatorUserId) {
    repository.delete(id, operatorUserId, ProgramType.ONL_ADM_USR.getCode());
  }
}
