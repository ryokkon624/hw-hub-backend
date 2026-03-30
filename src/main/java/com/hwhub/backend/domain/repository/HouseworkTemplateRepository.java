package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.houseworktemplate.HouseworkTemplateModel;
import java.util.List;

public interface HouseworkTemplateRepository {

  List<HouseworkTemplateModel> findAll();
}
