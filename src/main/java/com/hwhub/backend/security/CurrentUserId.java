package com.hwhub.backend.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * コントローラーメソッドのパラメーターに付与することで、 JWTから取得した認証済みユーザーのIDを自動的に注入するアノテーション。
 *
 * <p>未認証の場合は {@link org.springframework.web.server.ResponseStatusException}(401) がスローされる。
 *
 * <pre>{@code
 * @GetMapping("/me/profile")
 * public UserProfileResponse getProfile(@CurrentUserId Long userId) { ... }
 * }</pre>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUserId {}
