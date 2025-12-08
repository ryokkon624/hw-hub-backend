package com.hwhub.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI houseworkHubOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("HouseworkHub API")
                .description("家事管理アプリ HouseworkHub のバックエンドAPI仕様")
                .version("v1.0.0"));
  }
}
