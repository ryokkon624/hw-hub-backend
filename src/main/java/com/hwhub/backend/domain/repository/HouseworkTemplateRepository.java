package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateId;
import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel;
import java.util.List;

public interface HouseworkTemplateRepository {

  List<HouseworkTemplateModel> findAll();

  HouseworkTemplateModel insert(HouseworkTemplateModel model, Long operatorUserId, String program);

  void update(HouseworkTemplateModel model, Long operatorUserId, String program);

  void delete(HouseworkTemplateId id, Long operatorUserId, String program);
}
