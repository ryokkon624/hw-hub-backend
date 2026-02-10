package com.hwhub.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hwhub.auth.google")
@Getter
@Setter
public class GoogleOAuthProperties {
  private boolean enabled = false;
  private String clientId;
  private String clientSecret;
  private String redirectUri;
  private String frontBaseUrl;
  private String successRedirectPath;
  private String failureRedirectPath;
  private String scope;
  private long stateTtlSeconds = 600;
  private String oauthStateSecret;
  private String stateCookieName;
  private boolean stateCookieSecure = false;
  private String googleLinkSuccessRedirectPath;
  private String googleLinkFailureRedirectPath;
}
