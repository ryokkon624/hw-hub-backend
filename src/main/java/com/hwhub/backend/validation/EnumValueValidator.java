package com.hwhub.backend.validation;

import com.hwhub.backend.domain.enums.CodeEnum;
import com.hwhub.backend.validation.annotation.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

  private Class<? extends Enum<?>> enumClass;

  @Override
  public void initialize(EnumValue annotation) {
    this.enumClass = annotation.enumClass();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // 必須かどうかは @NotBlank などで別途制御
    }

    Object[] enumConstants = enumClass.getEnumConstants();
    if (enumConstants == null) {
      return false;
    }

    for (Object constant : enumConstants) {
      // CodeEnum を実装している場合は code でチェック
      if (constant instanceof CodeEnum codeEnum) {
        if (value.equals(codeEnum.getCode())) {
          return true;
        }
      } else {
        // fallback: enum.name() と比較（CodeEnumじゃないenum用）
        if (value.equals(((Enum<?>) constant).name())) {
          return true;
        }
      }
    }
    return false;
  }
}
