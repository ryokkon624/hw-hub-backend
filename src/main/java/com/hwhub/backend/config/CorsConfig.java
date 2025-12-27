package com.hwhub.backend.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConfigurationProperties(prefix = "hwhub.cors")
@Getter
@Setter
public class CorsConfig implements WebMvcConfigurer {

  // デフォルトlocalhost。ymlのallowed-originsが注入される。
  private List<String> allowedOrigins = List.of("http://localhost:5173");

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/api/**")
        .allowedOrigins(allowedOrigins.toArray(new String[0]))
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        .allowedHeaders("Authorization", "Content-Type", "Accept")
        .exposedHeaders("Authorization")
        .allowCredentials(false)
        .maxAge(3600);
  }
}
