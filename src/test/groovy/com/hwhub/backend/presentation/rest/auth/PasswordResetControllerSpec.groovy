package com.hwhub.backend.presentation.rest.auth

import com.hwhub.backend.application.service.PasswordResetService
import com.hwhub.backend.presentation.rest.auth.dto.ConfirmResetRequest
import com.hwhub.backend.presentation.rest.auth.dto.RequestResetRequest
import org.springframework.http.HttpStatus
import spock.lang.Specification

class PasswordResetControllerSpec extends Specification {

    PasswordResetService passwordResetService = Mock()
    PasswordResetController controller = new PasswordResetController(passwordResetService)

    def "requestResetはサービスを呼び出し204を返す"() {
        given:
        def request = new RequestResetRequest("test@example.com")

        when:
        def response = controller.requestReset(request)

        then:
        1 * passwordResetService.requestReset("test@example.com")
        response.statusCode == HttpStatus.NO_CONTENT
    }

    def "confirmResetはサービスを呼び出し204を返す"() {
        given:
        def request = new ConfirmResetRequest("token-123", "newPass")

        when:
        def response = controller.confirmReset(request)

        then:
        1 * passwordResetService.confirmReset("token-123", "newPass")
        response.statusCode == HttpStatus.NO_CONTENT
    }
}
