package com.hwhub.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "hwhub.deep-link")
public class DeepLinkProperties {
  /** iOS Team ID + Bundle ID (例: ABCDE12345.com.hwhub.app) */
  private String iosAppId;

  /** Android package name (例: com.hwhub.app) */
  private String androidPackageName;

  /** Android アプリ署名の SHA-256 フィンガープリント (コロン区切り) */
  private String androidSha256CertFingerprint;
}
