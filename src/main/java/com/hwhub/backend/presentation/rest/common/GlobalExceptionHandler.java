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

  /**
   * DTO(@RequestBody) の Bean Validation エラーをハンドルします。 例: @Valid を付けたリクエストボディのバリデーション失敗時。
   *
   * @param ex MethodArgumentNotValidException
   * @return 400 Bad Request とエラー詳細
   */
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
   * @RequestParam や @PathVariable などの Bean Validation エラーをハンドルします。 例: @Positive Long id などの制約違反時。
   *
   * @param ex ConstraintViolationException
   * @return 400 Bad Request とエラー詳細
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
    String path =
        violation.getPropertyPath() != null ? violation.getPropertyPath().toString() : null;
    String message = violation.getMessage();
    return new FieldErrorDetail(path, message);
  }

  /**
   * 不正な引数（IllegalArgumentException）をハンドルします。 サービス層での業務バリデーション失敗時などにスローされます。
   *
   * @param ex IllegalArgumentException
   * @return 400 Bad Request (ログイン失敗時は 401 Unauthorized)
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

  /**
   * 認可エラー（AccessDeniedException）をハンドルします。 権限不足の操作を試みた場合にスローされます。
   *
   * @param ex AccessDeniedException
   * @return 403 Forbidden
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {

    ErrorResponse body =
        ErrorResponse.of("FORBIDDEN", "You are not allowed to access this resource.");

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
  }

  /**
   * リソース未検出（ResourceNotFoundException）をハンドルします。 指定されたIDのデータが存在しない場合などにスローされます。
   *
   * @param ex ResourceNotFoundException
   * @return 404 Not Found
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {

    ErrorResponse body =
        ErrorResponse.of(
            "NOT_FOUND", ex.getMessage() != null ? ex.getMessage() : "Resource not found.");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  /**
   * 認証失敗（BadCredentialsException）をハンドルします。 ログイン時のメールアドレスまたはパスワードの不一致時にスローされます。
   *
   * @param ex BadCredentialsException
   * @return 401 Unauthorized
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
    // セキュリティ上の理由で、理由は伏せて統一メッセージにする
    ErrorResponse body = ErrorResponse.of("AUTH_INVALID_CREDENTIALS", "Invalid email or password.");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
  }

  /**
   * メールアドレス重複（EmailAlreadyUsedException）をハンドルします。 既に登録されているメールアドレスで新規登録を試みた場合にスローされます。
   *
   * @param ex EmailAlreadyUsedException
   * @return 409 Conflict
   */
  @ExceptionHandler(EmailAlreadyUsedException.class)
  public ResponseEntity<ErrorResponse> handleEmailAlreadyUsed(EmailAlreadyUsedException ex) {

    ErrorResponse body = ErrorResponse.of("EMAIL_ALREADY_USED", ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(body); // 409
  }

  /**
   * メール認証トークン不正（EmailVerificationTokenInvalidException）をハンドルします。
   *
   * @param ex EmailVerificationTokenInvalidException
   * @return 400 Bad Request
   */
  @ExceptionHandler(EmailVerificationTokenInvalidException.class)
  public ResponseEntity<ErrorResponse> handleTokenInvalid(
      EmailVerificationTokenInvalidException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_VERIFICATION_TOKEN_INVALID", ex.getMessage());
    return ResponseEntity.badRequest().body(body);
  }

  /**
   * メール送信クールダウン（EmailVerificationCooldownException）をハンドルします。 短期間の連続送信を制限します。
   *
   * @param ex EmailVerificationCooldownException
   * @return 429 Too Many Requests
   */
  @ExceptionHandler(EmailVerificationCooldownException.class)
  public ResponseEntity<ErrorResponse> handleCooldown(EmailVerificationCooldownException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_VERIFICATION_COOLDOWN", ex.getMessage());
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(body);
  }

  /**
   * メール送信回数制限（EmailVerificationTooManyRequestsException）をハンドルします。
   *
   * @param ex EmailVerificationTooManyRequestsException
   * @return 429 Too Many Requests
   */
  @ExceptionHandler(EmailVerificationTooManyRequestsException.class)
  public ResponseEntity<ErrorResponse> handleTooMany(EmailVerificationTooManyRequestsException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_VERIFICATION_LIMIT_EXCEEDED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(body);
  }

  /**
   * 認証済みエラー（EmailAlreadyVerifiedException）をハンドルします。
   *
   * @param ex EmailAlreadyVerifiedException
   * @return 409 Conflict
   */
  @ExceptionHandler(EmailAlreadyVerifiedException.class)
  public ResponseEntity<ErrorResponse> handleAlreadyVerified(EmailAlreadyVerifiedException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_ALREADY_VERIFIED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
  }

  /**
   * 未認証エラー（EmailNotVerifiedException）をハンドルします。
   *
   * @param ex EmailNotVerifiedException
   * @return 403 Forbidden
   */
  @ExceptionHandler(EmailNotVerifiedException.class)
  public ResponseEntity<ErrorResponse> hundleNotVerified(EmailNotVerifiedException ex) {
    ErrorResponse body = ErrorResponse.of("EMAIL_NOT_VERIFIED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
  }

  /**
   * パスワードリセットトークン不正（PasswordResetTokenInvalidException）をハンドルします。
   *
   * @param ex PasswordResetTokenInvalidException
   * @return 400 Bad Request
   */
  @ExceptionHandler(PasswordResetTokenInvalidException.class)
  public ResponseEntity<ErrorResponse> handleTokenInvalid(PasswordResetTokenInvalidException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_TOKEN_INVALID", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * パスワードリセットトークン期限切れ（PasswordResetTokenExpiredException）をハンドルします。
   *
   * @param ex PasswordResetTokenExpiredException
   * @return 400 Bad Request
   */
  @ExceptionHandler(PasswordResetTokenExpiredException.class)
  public ResponseEntity<ErrorResponse> handleTokenExpired(PasswordResetTokenExpiredException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_TOKEN_EXPIRED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * パスワードリセットメール送信クールダウン（PasswordResetCooldownException）をハンドルします。
   *
   * @param ex PasswordResetCooldownException
   * @return 429 Too Many Requests
   */
  @ExceptionHandler(PasswordResetCooldownException.class)
  public ResponseEntity<ErrorResponse> handleCooldown(PasswordResetCooldownException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_COOLDOWN", ex.getMessage());
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(body);
  }

  /**
   * パスワードリセット回数制限（PasswordResetLimitExceededException）をハンドルします。
   *
   * @param ex PasswordResetLimitExceededException
   * @return 429 Too Many Requests
   */
  @ExceptionHandler(PasswordResetLimitExceededException.class)
  public ResponseEntity<ErrorResponse> handleLimitExceeded(PasswordResetLimitExceededException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_LIMIT_EXCEEDED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(body);
  }

  /**
   * 現在のパスワード不一致（CurrentPasswordInvalidException）をハンドルします。
   *
   * @param ex CurrentPasswordInvalidException
   * @return 400 Bad Request
   */
  @ExceptionHandler(CurrentPasswordInvalidException.class)
  public ResponseEntity<ErrorResponse> handleCurrentPasswordInvalidException(
      CurrentPasswordInvalidException ex) {
    ErrorResponse body = ErrorResponse.of("CURRENT_PASSWORD_INVALID", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * 旧パスワード同一エラー（PasswordSameAsOldException）をハンドルします。
   *
   * @param ex PasswordSameAsOldException
   * @return 400 Bad Request
   */
  @ExceptionHandler(PasswordSameAsOldException.class)
  public ResponseEntity<ErrorResponse> handleSameAsOldException(PasswordSameAsOldException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_SAME_AS_OLD", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * パスワードポリシー違反（PasswordPolicyViolationException）をハンドルします。
   *
   * @param ex PasswordPolicyViolationException
   * @return 400 Bad Request
   */
  @ExceptionHandler(PasswordPolicyViolationException.class)
  public ResponseEntity<ErrorResponse> handlePolicyViolationException(
      PasswordPolicyViolationException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_TOO_WEAK", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * パスワードリセット無効（PasswordResetDisabledException）をハンドルします。
   *
   * @param ex PasswordResetDisabledException
   * @return 403 Forbidden
   */
  @ExceptionHandler(PasswordResetDisabledException.class)
  public ResponseEntity<ErrorResponse> handlePasswordResetDisabled(
      PasswordResetDisabledException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_RESET_DISABLED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
  }

  /**
   * OAuth state不一致（OAuthStateMismatchException）をハンドルします。
   *
   * @param ex OAuthStateMismatchException
   * @return 400 Bad Request
   */
  @ExceptionHandler(OAuthStateMismatchException.class)
  public ResponseEntity<ErrorResponse> handleOAuthStateMismatch(OAuthStateMismatchException ex) {
    ErrorResponse body = ErrorResponse.of("OAUTH_STATE_MISMATCH", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * OAuth IDトークン不正（OAuthIdTokenInvalidException）をハンドルします。
   *
   * @param ex OAuthIdTokenInvalidException
   * @return 400 Bad Request
   */
  @ExceptionHandler(OAuthIdTokenInvalidException.class)
  public ResponseEntity<ErrorResponse> handleOAuthIdTokenInvalid(OAuthIdTokenInvalidException ex) {
    ErrorResponse body = ErrorResponse.of("OAUTH_ID_TOKEN_INVALID", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * OAuthメール未認証（OAuthEmailNotVerifiedException）をハンドルします。
   *
   * @param ex OAuthEmailNotVerifiedException
   * @return 400 Bad Request
   */
  @ExceptionHandler(OAuthEmailNotVerifiedException.class)
  public ResponseEntity<ErrorResponse> handleOAuthEmailNotVerified(
      OAuthEmailNotVerifiedException ex) {
    ErrorResponse body = ErrorResponse.of("OAUTH_EMAIL_NOT_VERIFIED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * OAuthメール既登録（OAuthEmailAlreadyRegisteredException）をハンドルします。
   *
   * @param ex OAuthEmailAlreadyRegisteredException
   * @return 400 Bad Request
   */
  @ExceptionHandler(OAuthEmailAlreadyRegisteredException.class)
  public ResponseEntity<ErrorResponse> handleOAuthEmailAlreadyRegistered(
      OAuthEmailAlreadyRegisteredException ex) {
    ErrorResponse body = ErrorResponse.of("OAUTH_EMAIL_ALREADY_REGISTERED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * Google連携済みエラー（GoogleAccountAlreadyLinkedException）をハンドルします。
   *
   * @param ex GoogleAccountAlreadyLinkedException
   * @return 409 Conflict
   */
  @ExceptionHandler(GoogleAccountAlreadyLinkedException.class)
  public ResponseEntity<ErrorResponse> handleGoogleAccountAlreadyLinked(
      GoogleAccountAlreadyLinkedException ex) {
    ErrorResponse body = ErrorResponse.of("GOOGLE_ALREADY_LINKED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
  }

  /**
   * Google sub既使用エラー（GoogleSubAlreadyUsedException）をハンドルします。
   *
   * @param ex GoogleSubAlreadyUsedException
   * @return 409 Conflict
   */
  @ExceptionHandler(GoogleSubAlreadyUsedException.class)
  public ResponseEntity<ErrorResponse> handleGoogleSubAlreadyUsed(
      GoogleSubAlreadyUsedException ex) {
    ErrorResponse body = ErrorResponse.of("GOOGLE_SUB_ALREADY_USED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
  }

  /**
   * パスワードログイン不可（PasswordLoginNotAllowedException）をハンドルします。
   *
   * @param ex PasswordLoginNotAllowedException
   * @return 400 Bad Request
   */
  @ExceptionHandler(PasswordLoginNotAllowedException.class)
  public ResponseEntity<ErrorResponse> handlePasswordLoginNotAllowed(
      PasswordLoginNotAllowedException ex) {
    ErrorResponse body = ErrorResponse.of("PASSWORD_LOGIN_NOT_ALLOWED", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  /**
   * 予期しない例外をハンドルします。 上記のいずれのハンドラにも該当しない例外が発生した場合に呼び出されます。
   *
   * @param ex Exception
   * @param req HttpServletRequest
   * @return 500 Internal Server Error
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest req) {
    log.error("Unhandled exception: method={}, path={}", req.getMethod(), req.getRequestURI(), ex);

    ErrorResponse body = ErrorResponse.of("INTERNAL_SERVER_ERROR", "Unexpected error occurred.");

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }
}
