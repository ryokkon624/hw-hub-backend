package com.hwhub.backend.validation.annotation;

import com.hwhub.backend.validation.EnumValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {

  String message() default "must be one of enum values";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /** チェック対象の enum クラス */
  Class<? extends Enum<?>> enumClass();
}
