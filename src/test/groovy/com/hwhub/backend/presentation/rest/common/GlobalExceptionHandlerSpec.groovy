// src/test/groovy/com/hwhub/backend/presentation/rest/common/GlobalExceptionHandlerSpec.groovy
package com.hwhub.backend.presentation.rest.common

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Path
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.BindingResult
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
    // その他の想定外の Exception
    // ============================================

    def "handleException は 500 と INTERNAL_SERVER_ERROR を返す"() {
        given:
        def ex = new RuntimeException("something bad")

        when:
        def response = handler.handleException(ex)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
        response.body != null
        response.body.errorCode == "INTERNAL_SERVER_ERROR"
        response.body.message == "Unexpected error occurred."
    }
}
