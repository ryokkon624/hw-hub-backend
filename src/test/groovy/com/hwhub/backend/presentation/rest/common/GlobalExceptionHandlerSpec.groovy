// src/test/groovy/com/hwhub/backend/presentation/rest/common/GlobalExceptionHandlerSpec.groovy
package com.hwhub.backend.presentation.rest.common

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Path
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import spock.lang.Specification

class GlobalExceptionHandlerSpec extends Specification {

    GlobalExceptionHandler handler = new GlobalExceptionHandler()

    // ============================================
    // MethodArgumentNotValidException
    // ============================================

    def "handleMethodArgumentNotValid は 400 と VALIDATION_ERROR を返す"() {
        given:
        // 本物の BindingResult を作る
        def target = new Object()
        def bindingResult = new BeanPropertyBindingResult(target, "request")
        bindingResult.addError(new FieldError("houseworkRequest", "name", "must not be blank"))

        // 本物の MethodArgumentNotValidException に BindingResult を渡す
        def ex = new MethodArgumentNotValidException(null, bindingResult)

        when:
        def response = handler.handleMethodArgumentNotValid(ex)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body != null
        response.body.errorCode == "VALIDATION_ERROR"
        response.body.message == "Request validation failed."
        response.body.details.size() == 1
    }

    // ============================================
    // ConstraintViolationException
    // ============================================

    def "handleConstraintViolation は 400 と VALIDATION_ERROR を返す"() {
        given:
        ConstraintViolation<?> violation = Mock()
        Path path = Mock()
        path.toString() >> "list.householdId"
        violation.getPropertyPath() >> path
        violation.getMessage() >> "must be positive"

        def ex = new ConstraintViolationException("validation error", [violation] as Set)

        when:
        def response = handler.handleConstraintViolation(ex)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body != null
        response.body.errorCode == "VALIDATION_ERROR"
        response.body.message == "Request validation failed."
        response.body.details.size() == 1
    }

    // ============================================
    // IllegalArgumentException
    // ============================================

    def "handleIllegalArgument は 400 と BAD_REQUEST を返す"() {
        given:
        def ex = new IllegalArgumentException("bad request param")

        when:
        def response = handler.handleIllegalArgument(ex)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body != null
        response.body.errorCode == "BAD_REQUEST"
        response.body.message == "bad request param"
    }

    // ============================================
    // AccessDeniedException
    // ============================================

    def "handleAccessDenied は 403 と FORBIDDEN を返す"() {
        given:
        def ex = new AccessDeniedException("forbidden")

        when:
        def response = handler.handleAccessDenied(ex)

        then:
        response.statusCode == HttpStatus.FORBIDDEN
        response.body != null
        response.body.errorCode == "FORBIDDEN"
        response.body.message == "You are not allowed to access this resource."
    }

    // ============================================
    // ResourceNotFoundException
    // ============================================

    def "handleResourceNotFound は 404 と NOT_FOUND を返す"() {
        given:
        def ex = new ResourceNotFoundException("User not found")

        when:
        def response = handler.handleResourceNotFound(ex)

        then:
        response.statusCode == HttpStatus.NOT_FOUND
        response.body != null
        response.body.errorCode == "NOT_FOUND"
        response.body.message == "User not found"
    }

    // ============================================
    // EmailAlreadyUsedException
    // ============================================

    def "handleEmailAlreadyUsed は 409 と EMAIL_ALREADY_USED を返す"() {
        given:
        def ex = new EmailAlreadyUsedException("email already used")

        when:
        def response = handler.handleEmailAlreadyUsed(ex)

        then:
        response.statusCode == HttpStatus.CONFLICT
        response.body != null
        response.body.errorCode == "EMAIL_ALREADY_USED"
        response.body.message == ex.message
    }

    // ============================================
    // EmailVerificationTokenInvalidException
    // ============================================

    def "handleTokenInvalid は 400 と EMAIL_VERIFICATION_TOKEN_INVALID を返す"() {
        given:
        def ex = new EmailVerificationTokenInvalidException()

        when:
        def response = handler.handleTokenInvalid(ex)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body != null
        response.body.errorCode == "EMAIL_VERIFICATION_TOKEN_INVALID"
    }

    // ============================================
    // EmailVerificationCooldownException
    // ============================================

    def "handleCooldown は 429 と EMAIL_VERIFICATION_COOLDOWN を返す"() {
        given:
        def ex = new EmailVerificationCooldownException()

        when:
        def response = handler.handleCooldown(ex)

        then:
        response.statusCode == HttpStatus.TOO_MANY_REQUESTS
        response.body != null
        response.body.errorCode == "EMAIL_VERIFICATION_COOLDOWN"
    }

    // ============================================
    // EmailVerificationTooManyRequestsException
    // ============================================

    def "handleTooMany は 429 と EMAIL_VERIFICATION_TOO_MANY_REQUESTS を返す"() {
        given:
        def ex = new EmailVerificationTooManyRequestsException()

        when:
        def response = handler.handleTooMany(ex)

        then:
        response.statusCode == HttpStatus.TOO_MANY_REQUESTS
        response.body != null
        response.body.errorCode == "EMAIL_VERIFICATION_LIMIT_EXCEEDED"
    }

    // ============================================
    // EmailAlreadyVerifiedException
    // ============================================

    def "handleAlreadyVerified は 409 と EMAIL_ALREADY_VERIFIED を返す"() {
        given:
        def ex = new EmailAlreadyVerifiedException()

        when:
        def response = handler.handleAlreadyVerified(ex)

        then:
        response.statusCode == HttpStatus.CONFLICT
        response.body != null
        response.body.errorCode == "EMAIL_ALREADY_VERIFIED"
    }

    // ============================================
    // EmailNotVerifiedException
    // ============================================

    def "hundleNotVerified は 403 と EMAIL_NOT_VERIFIED を返す"() {
        given:
        def ex = new EmailNotVerifiedException()

        when:
        def response = handler.hundleNotVerified(ex)

        then:
        response.statusCode == HttpStatus.FORBIDDEN
        response.body != null
        response.body.errorCode == "EMAIL_NOT_VERIFIED"
    }

    // ============================================
    // その他の想定外の Exception
    // ============================================

    def "handleException は 500 と INTERNAL_SERVER_ERROR を返す"() {
        given:
        def ex = new RuntimeException("something bad")
        def req = Mock(HttpServletRequest) {
            getMethod() >> "POST"
            getRequestURI() >> "/api/auth/login"
        }

        when:
        def response = handler.handleException(ex, req)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body != null
        response.body.errorCode == "INTERNAL_SERVER_ERROR"
        response.body.message == "Unexpected error occurred."
    }

    // ============================================
    // PasswordReset Exceptions
    // ============================================

    def "handleTokenInvalid (PasswordReset) は 400 と PASSWORD_RESET_TOKEN_INVALID を返す"() {
        given:
        def ex = new com.hwhub.backend.presentation.rest.common.PasswordResetTokenInvalidException()

        when:
        def response = handler.handleTokenInvalid(ex)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body != null
        response.body.errorCode == "PASSWORD_RESET_TOKEN_INVALID"
    }

    def "handleTokenExpired (PasswordReset) は 400 と PASSWORD_RESET_TOKEN_EXPIRED を返す"() {
        given:
        def ex = new com.hwhub.backend.presentation.rest.common.PasswordResetTokenExpiredException()

        when:
        def response = handler.handleTokenExpired(ex)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body != null
        response.body.errorCode == "PASSWORD_RESET_TOKEN_EXPIRED"
    }

    def "handleCooldown (PasswordReset) は 429 と PASSWORD_RESET_COOLDOWN を返す"() {
        given:
        def ex = new com.hwhub.backend.presentation.rest.common.PasswordResetCooldownException()

        when:
        def response = handler.handleCooldown(ex)

        then:
        response.statusCode == HttpStatus.TOO_MANY_REQUESTS
        response.body != null
        response.body.errorCode == "PASSWORD_RESET_COOLDOWN"
    }

    def "handleLimitExceeded (PasswordReset) は 429 と PASSWORD_RESET_LIMIT_EXCEEDED を返す"() {
        given:
        def ex = new com.hwhub.backend.presentation.rest.common.PasswordResetLimitExceededException()

        when:
        def response = handler.handleLimitExceeded(ex)

        then:
        response.statusCode == HttpStatus.TOO_MANY_REQUESTS
        response.body != null
        response.body.errorCode == "PASSWORD_RESET_LIMIT_EXCEEDED"
    }

    def "handlePasswordResetDisabled は 403 と PASSWORD_RESET_DISABLED を返す"() {
        given:
        def ex = new PasswordResetDisabledException()

        when:
        def response = handler.handlePasswordResetDisabled(ex)

        then:
        response.statusCode == HttpStatus.FORBIDDEN
        response.body != null
        response.body.errorCode == "PASSWORD_RESET_DISABLED"
    }

    // ============================================
    // Other Auth Exceptions
    // ============================================

    def "handleCurrentPasswordInvalidException は 400 と CURRENT_PASSWORD_INVALID を返す"() {
        given:
        def ex = new com.hwhub.backend.presentation.rest.common.CurrentPasswordInvalidException()

        when:
        def response = handler.handleCurrentPasswordInvalidException(ex)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body != null
        response.body.errorCode == "CURRENT_PASSWORD_INVALID"
    }

    def "handleSameAsOldException は 400 と PASSWORD_SAME_AS_OLD を返す"() {
        given:
        def ex = new com.hwhub.backend.presentation.rest.common.PasswordSameAsOldException()

        when:
        def response = handler.handleSameAsOldException(ex)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body != null
        response.body.errorCode == "PASSWORD_SAME_AS_OLD"
    }

    def "handlePolicyViolationException は 400 と PASSWORD_TOO_WEAK を返す"() {
        given:
        def ex = new com.hwhub.backend.presentation.rest.common.PasswordPolicyViolationException()

        when:
        def response = handler.handlePolicyViolationException(ex)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body != null
        response.body.errorCode == "PASSWORD_TOO_WEAK"
        response.body.message == "Password policy violation"
    }
}
