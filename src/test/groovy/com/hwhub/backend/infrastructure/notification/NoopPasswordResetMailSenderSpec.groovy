package com.hwhub.backend.infrastructure.notification

import spock.lang.Specification

class NoopPasswordResetMailSenderSpec extends Specification {

    NoopPasswordResetMailSender sender = new NoopPasswordResetMailSender()

    def "sendPasswordResetMailはエラーなしで実行される"() {
        when:
        sender.sendPasswordResetMail("user@example.com", "User", "url", "en")

        then:
        noExceptionThrown()
    }
}
