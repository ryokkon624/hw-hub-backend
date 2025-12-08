package com.hwhub.backend.presentation.rest.code.dto;

import com.hwhub.backend.domain.model.CodeModel;
import lombok.Data;

@Data
public class CodeDto {

  private String codeType;
  private String codeTypeName;
  private String codeTypeNameEn;
  private String codeValue;
  private String name;
  private String displayNameJa;
  private String displayNameEn;
  private String displayNameEs;
  private String remarks;
  private String displayOrder;

  public static CodeDto from(CodeModel model) {
    CodeDto dto = new CodeDto();

    dto.setCodeType(model.getCodeType());
    dto.setCodeTypeName(model.getCodeTypeName());
    dto.setCodeTypeNameEn(model.getCodeTypeNameEn());
    dto.setCodeValue(model.getCodeValue());
    dto.setName(model.getName());
    dto.setDisplayNameJa(model.getDisplayNameJa());
    dto.setDisplayNameEn(model.getDisplayNameEn());
    dto.setDisplayNameEs(model.getDisplayNameEs());
    dto.setRemarks(model.getRemarks());
    dto.setDisplayOrder(model.getDisplayOrder());

    return dto;
  }
}
