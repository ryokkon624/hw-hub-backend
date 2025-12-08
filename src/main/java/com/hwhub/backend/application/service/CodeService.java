package com.hwhub.backend.application.service;

import com.hwhub.backend.domain.model.CodeModel;
import com.hwhub.backend.domain.repository.CodeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeService {

  private final CodeRepository codeRepository;

  public List<CodeModel> listCodes(String codeType) {
    if (codeType == null || codeType.isBlank()) {
      return codeRepository.findAll();
    }
    return codeRepository.findByCodeType(codeType);
  }
}
