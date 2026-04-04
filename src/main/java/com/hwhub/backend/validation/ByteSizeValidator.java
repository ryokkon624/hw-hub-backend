package com.hwhub.backend.validation;

import com.hwhub.backend.validation.annotation.ByteSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;

public class ByteSizeValidator implements ConstraintValidator<ByteSize, String> {

  private int max;

  @Override
  public void initialize(ByteSize constraintAnnotation) {
    this.max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    int byteLength = value.getBytes(StandardCharsets.UTF_8).length;
    return byteLength <= max;
  }
}
