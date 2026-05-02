package com.hwhub.backend.config;

import com.hwhub.backend.security.CurrentUserIdArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC のカスタム設定クラス。
 *
 * <p>{@link CurrentUserIdArgumentResolver} を登録し、コントローラーメソッドの {@code @CurrentUserId Long userId}
 * パラメーターへの自動注入を有効化する。
 *
 * <p>CORS設定は {@link CorsConfig} で管理しているため、このクラスには含めない。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new CurrentUserIdArgumentResolver());
  }
}
