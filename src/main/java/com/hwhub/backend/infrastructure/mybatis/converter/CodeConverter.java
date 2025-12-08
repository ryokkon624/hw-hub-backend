package com.hwhub.backend.infrastructure.mybatis.converter;

import com.hwhub.backend.domain.model.CodeModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MCode;

public final class CodeConverter {

  private CodeConverter() {}

  public static CodeModel toModel(MCode entity) {
    if (entity == null) {
      return null;
    }

    return CodeModel.reconstruct(
        entity.getCodeType(),
        entity.getCodeTypeName(),
        entity.getCodeTypeNameEn(),
        entity.getCodeValue(),
        entity.getName(),
        entity.getDisplayNameJa(),
        entity.getDisplayNameEn(),
        entity.getDisplayNameEs(),
        entity.getRemarks(),
        entity.getDisplayOrder());
  }
}
