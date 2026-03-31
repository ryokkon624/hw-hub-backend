package com.hwhub.backend.domain.model.houseworktemplate;

public record HouseworkTemplateId(long value) {
  public HouseworkTemplateId {
    if (value <= 0) throw new IllegalArgumentException("houseworkTemplateId must be positive");
  }
}
