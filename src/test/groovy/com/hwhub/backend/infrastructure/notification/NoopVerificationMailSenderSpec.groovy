package com.hwhub.backend.infrastructure.notification

import spock.lang.Specification

class NoopVerificationMailSenderSpec extends Specification {

    def "sendVerificationMail does nothing"() {
        given:
        def sender = new NoopVerificationMailSender()
        
        when:
        sender.sendVerificationMail("to@example.com", "Name", "http://example.com", "ja")
        
        then:
        noExceptionThrown()
    }
}
