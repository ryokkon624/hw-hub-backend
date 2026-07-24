package com.hwhub.backend.domain.oauth.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleUserInfo {
  private String sub;
  private String email;

  @JsonProperty("email_verified")
  private Boolean emailVerified;

  private String name;

  @JsonProperty("picture")
  private String picture;

  private String aud;
  private String azp;
  private String iss;
}
