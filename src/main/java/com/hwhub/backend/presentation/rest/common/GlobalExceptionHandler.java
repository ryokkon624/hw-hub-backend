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
import org.springframework.security.authentication.BadCredentialsException;
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
    // ログイン系だけ判定
    if ("Invalid email or password".equals(ex.getMessage())) {
      ErrorResponse body =
          ErrorResponse.of("AUTH_INVALID_CREDENTIALS", "Invalid email or password.");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
    ErrorResponse body = ErrorResponse.of("BAD_REQUEST", ex.getMessage());
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

    ErrorResponse body = ErrorResponse.of("NOT_FOUND",
        ex.getMessage() != null ? ex.getMessage() : "Resource not found.");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
    // セキュリティ上の理由で、理由は伏せて統一メッセージにする
    ErrorResponse body = ErrorResponse.of("AUTH_INVALID_CREDENTIALS", "Invalid email or password.");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
  }

  /** 既に使われているemailアドレスで登録しようとした場合 */
  @ExceptionHandler(EmailAlreadyUsedException.class)
  public ResponseEntity<ErrorResponse> handleEmailAlreadyUsed(EmailAlreadyUsedException ex) {

    ErrorResponse body = ErrorResponse.of("EMAIL_ALREADY_USED", ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(body); // 409
  }

  @ExceptionHandler(EmailVerificationTokenInvalidException.class)
  public ResponseEntity<ErrorResponse> handleTokenInvalid(
      EmailVerificationTokenInvalidException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_VERIFICATION_TOKEN_INVALID", ex.getMessage());
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(EmailVerificationCooldownException.class)
  public ResponseEntity<ErrorResponse> handleCooldown(EmailVerificationCooldownException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_VERIFICATION_COOLDOWN", ex.getMessage());
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(body);
  }

  @ExceptionHandler(EmailVerificationTooManyRequestsException.class)
  public ResponseEntity<ErrorResponse> handleTooMany(EmailVerificationTooManyRequestsException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_VERIFICATION_LIMIT_EXCEEDED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(body);
  }

  @ExceptionHandler(EmailAlreadyVerifiedException.class)
  public ResponseEntity<ErrorResponse> handleAlreadyVerified(EmailAlreadyVerifiedException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_ALREADY_VERIFIED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
  }

  @ExceptionHandler(EmailNotVerifiedException.class)
  public ResponseEntity<ErrorResponse> hundleNotVerified(EmailNotVerifiedException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_NOT_VERIFIED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
  }

  @ExceptionHandler(PasswordResetTokenInvalidException.class)
  public ResponseEntity<ErrorResponse> handleTokenInvalid(PasswordResetTokenInvalidException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_TOKEN_INVALID", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(PasswordResetTokenExpiredException.class)
  public ResponseEntity<ErrorResponse> handleTokenExpired(PasswordResetTokenExpiredException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_TOKEN_EXPIRED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(PasswordResetCooldownException.class)
  public ResponseEntity<ErrorResponse> handleCooldown(PasswordResetCooldownException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_COOLDOWN", ex.getMessage());
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(body);
  }

  @ExceptionHandler(PasswordResetLimitExceededException.class)
  public ResponseEntity<ErrorResponse> handleLimitExceeded(PasswordResetLimitExceededException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_LIMIT_EXCEEDED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(body);
  }

  @ExceptionHandler(CurrentPasswordInvalidException.class)
  public ResponseEntity<ErrorResponse> handleCurrentPasswordInvalidException(
      CurrentPasswordInvalidException ex) {
    ErrorResponse body = ErrorResponse.of("CURRENT_PASSWORD_INVALID", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(PasswordSameAsOldException.class)
  public ResponseEntity<ErrorResponse> handleSameAsOldException(PasswordSameAsOldException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_SAME_AS_OLD", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(PasswordPolicyViolationException.class)
  public ResponseEntity<ErrorResponse> handlePolicyViolationException(
      PasswordPolicyViolationException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_TOO_WEAK", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(PasswordResetDisabledException.class)
  public ResponseEntity<ErrorResponse> handlePasswordResetDisabled(
      PasswordResetDisabledException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_DISABLED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
  }

  @ExceptionHandler(OAuthStateMismatchException.class)
  public ResponseEntity<ErrorResponse> handleOAuthStateMismatch(OAuthStateMismatchException ex) {
    ErrorResponse body = ErrorResponse.of("OAUTH_STATE_MISMATCH", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(OAuthIdTokenInvalidException.class)
  public ResponseEntity<ErrorResponse> handleOAuthIdTokenInvalid(OAuthIdTokenInvalidException ex) {
    ErrorResponse body = ErrorResponse.of("OAUTH_ID_TOKEN_INVALID", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(OAuthEmailNotVerifiedException.class)
  public ResponseEntity<ErrorResponse> handleOAuthEmailNotVerified(
      OAuthEmailNotVerifiedException ex) {
    ErrorResponse body = ErrorResponse.of("OAUTH_EMAIL_NOT_VERIFIED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(OAuthEmailAlreadyRegisteredException.class)
  public ResponseEntity<ErrorResponse> handleOAuthEmailAlreadyRegistered(
      OAuthEmailAlreadyRegisteredException ex) {
    ErrorResponse body = ErrorResponse.of("OAUTH_EMAIL_ALREADY_REGISTERED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(GoogleAccountAlreadyLinkedException.class)
  public ResponseEntity<ErrorResponse> handleGoogleAccountAlreadyLinked(
      GoogleAccountAlreadyLinkedException ex) {
    ErrorResponse body = ErrorResponse.of("GOOGLE_ALREADY_LINKED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
  }

  @ExceptionHandler(GoogleSubAlreadyUsedException.class)
  public ResponseEntity<ErrorResponse> handleGoogleSubAlreadyUsed(
      GoogleSubAlreadyUsedException ex) {
    ErrorResponse body = ErrorResponse.of("GOOGLE_SUB_ALREADY_USED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
  }

  @ExceptionHandler(PasswordLoginNotAllowedException.class)
  public ResponseEntity<ErrorResponse> handlePasswordLoginNotAllowed(
      PasswordLoginNotAllowedException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_LOGIN_NOT_ALLOWED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /** 最後の砦：想定していない例外 */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest req) {
    log.error("Unhandled exception: method={}, path={}", req.getMethod(), req.getRequestURI(), ex);

    ErrorResponse body = ErrorResponse.of("INTERNAL_SERVER_ERROR", "Unexpected error occurred.");

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
