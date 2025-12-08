package com.hwhub.backend.presentation.rest.common;

import java.util.List;

public record ErrorResponse(String errorCode, String message, List<FieldErrorDetail> details) {
  public static ErrorResponse of(String errorCode, String message) {
    return new ErrorResponse(errorCode, message, List.of());
  }

  public static ErrorResponse of(String errorCode, String message, List<FieldErrorDetail> details) {
    return new ErrorResponse(errorCode, message, details);
  }

  public record FieldErrorDetail(String field, String message) {}
}
