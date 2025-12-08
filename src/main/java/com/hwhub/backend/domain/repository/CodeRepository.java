package com.hwhub.backend.domain.repository;

import com.hwhub.backend.domain.model.CodeModel;
import java.util.List;

public interface CodeRepository {

  List<CodeModel> findAll();

  List<CodeModel> findByCodeType(String codeType);
}
