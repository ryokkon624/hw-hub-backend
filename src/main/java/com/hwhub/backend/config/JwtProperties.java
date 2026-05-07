// com/hwhub/backend/config/JwtProperties.java
package com.hwhub.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "hwhub.jwt")
public class JwtProperties {
  private String secret;
  private long expiryMillis;
  private long refreshExpiryMillis;
}
