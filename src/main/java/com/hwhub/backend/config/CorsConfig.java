package com.hwhub.backend.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@ConfigurationProperties(prefix = "hwhub.cors")
@Getter
@Setter
public class CorsConfig {

  // デフォルトlocalhost。ymlのallowed-originsが注入される。
  private List<String> allowedOrigins = List.of("http://localhost:5173");

  /**
   * Spring Security の CorsFilter に注入される CORS 設定。
   *
   * <p>WebMvcConfigurer#addCorsMappings は Spring MVC レイヤーの設定であり、 Spring Security の
   * CorsFilter（FilterChain の 5 番目）には届かない。 CorsConfigurationSource を Bean 登録することで
   * http.cors(Customizer.withDefaults()) が自動で拾い、 Security レイヤーでも正しく CORS が処理される。
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    // /api/** 用
    CorsConfiguration apiConfig = new CorsConfiguration();
    apiConfig.setAllowedOrigins(allowedOrigins);
    apiConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    apiConfig.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
    apiConfig.setExposedHeaders(List.of("Authorization"));
    apiConfig.setAllowCredentials(true);
    apiConfig.setMaxAge(3600L);
    source.registerCorsConfiguration("/api/**", apiConfig);

    // /actuator/** 用（認証不要・GETのみ）
    // Axios インターセプターが全リクエストに Authorization ヘッダーを付けるため
    // allowedHeaders に Authorization を含める必要がある
    CorsConfiguration actuatorConfig = new CorsConfiguration();
    actuatorConfig.setAllowedOrigins(allowedOrigins);
    actuatorConfig.setAllowedMethods(List.of("GET", "OPTIONS"));
    actuatorConfig.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
    actuatorConfig.setAllowCredentials(false);
    actuatorConfig.setMaxAge(3600L);
    source.registerCorsConfiguration("/actuator/**", actuatorConfig);

    return source;
  }
}
