package com.hwhub.backend.domain.oauth.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleTokenResponse {
  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("expires_in")
  private Long expiresIn;

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("scope")
  private String scope;

  @JsonProperty("id_token")
  private String idToken;
}
