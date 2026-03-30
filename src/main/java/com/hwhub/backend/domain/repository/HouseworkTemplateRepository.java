package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.HouseworkTemplate.HouseworkTemplateModel;
import java.util.List;

public interface HouseworkTemplateRepository {

  List<HouseworkTemplateModel> findAll();
}
