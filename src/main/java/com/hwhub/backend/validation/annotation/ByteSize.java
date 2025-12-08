package com.hwhub.backend.validation.annotation;

import com.hwhub.backend.validation.ByteSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ByteSizeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteSize {

  String message() default "byte length must be <= {max}";

  int max();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
