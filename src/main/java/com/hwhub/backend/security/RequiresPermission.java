package com.hwhub.backend.security;

import com.hwhub.backend.domain.enums.Permission;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermission {
  /** 必要なパーミッション（複数指定時はいずれか1つを持っていればOK） */
  Permission[] value();
}
