package com.hwhub.backend.presentation.rest.common;

import com.hwhub.backend.presentation.rest.common.ErrorResponse.FieldErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /** DTO(@RequestBody) の Bean Validation エラー 例: @Valid HouseworkRequest */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {

    List<FieldErrorDetail> details =
        ex.getBindingResult().getFieldErrors().stream().map(this::toFieldErrorDetail).toList();

    ErrorResponse body =
        ErrorResponse.of("VALIDATION_ERROR", "Request validation failed.", details);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  private FieldErrorDetail toFieldErrorDetail(FieldError fieldError) {
    String field = fieldError.getField();
    String message = fieldError.getDefaultMessage();
    return new FieldErrorDetail(field, message);
  }

  /**
   * @RequestParam, @PathVariable などの Bean Validation エラー 例: @RequestParam @Positive Long
   * householdId
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {

    List<FieldErrorDetail> details =
        ex.getConstraintViolations().stream().map(this::toFieldErrorDetail).toList();

    ErrorResponse body =
        ErrorResponse.of("VALIDATION_ERROR", "Request validation failed.", details);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  private FieldErrorDetail toFieldErrorDetail(ConstraintViolation<?> violation) {
    // propertyPath: "list.householdId" みたいな形式になることが多い
    String path =
        violation.getPropertyPath() != null ? violation.getPropertyPath().toString() : null;
    String message = violation.getMessage();
    return new FieldErrorDetail(path, message);
  }

  /**
   * 不正なリクエスト（サービス層で IllegalArgumentException を投げた場合など） 例: enumのfromCode失敗, defaultAssigneeUserId が
   * household メンバーではない etc.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

    ErrorResponse body =
        ErrorResponse.of(
            "BAD_REQUEST", ex.getMessage() != null ? ex.getMessage() : "Invalid request.");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /** 認可エラー (Spring Security または自前で AccessDeniedException を投げた場合) */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {

    ErrorResponse body =
        ErrorResponse.of("FORBIDDEN", "You are not allowed to access this resource.");

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
  }

  /** リソースが見つからない場合（Optional.empty で orElseThrow したときなど） 好みで独自 NotFoundException を作ってもOK */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {

    ErrorResponse body =
        ErrorResponse.of(
            "NOT_FOUND", ex.getMessage() != null ? ex.getMessage() : "Resource not found.");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  /** 既に使われているemailアドレスで登録しようとした場合 */
  @ExceptionHandler(EmailAlreadyUsedException.class)
  public ResponseEntity<ErrorResponse> handleEmailAlreadyUsed(EmailAlreadyUsedException ex) {

    ErrorResponse body = ErrorResponse.of("EMAIL_ALREADY_USED", ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(body); // 409
  }

  /** 最後の砦：想定していない例外 */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest req) {
    log.error("Unhandled exception: method={}, path={}", req.getMethod(), req.getRequestURI(), ex);

    ErrorResponse body = ErrorResponse.of("INTERNAL_SERVER_ERROR", "Unexpected error occurred.");

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
