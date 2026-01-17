package com.hwhub.backend.infrastructure.notification

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

class SmtpVerificationMailSenderSpec extends Specification {

    JavaMailSender javaMailSender = Mock()
    SmtpVerificationMailSender sender

    def setup() {
        sender = new SmtpVerificationMailSender(javaMailSender)
        ReflectionTestUtils.setField(sender, "from", "noreply@hwhub.com")
    }

    def "sendVerificationMail sends a SimpleMailMessage"() {
        given:
        def toEmail = "user@example.com"
        def displayName = "Hanako"
        def verifyUrl = "http://localhost/verify?token=abc"
        def locale = "ja"

        when:
        sender.sendVerificationMail(toEmail, displayName, verifyUrl, locale)

        then:
        1 * javaMailSender.send(_ as SimpleMailMessage) >> { SimpleMailMessage msg ->
            assert msg.from == "noreply@hwhub.com"
            assert msg.to[0] == toEmail
            assert msg.subject.contains("Please verify your email")
            assert msg.text.contains("Hi Hanako")
            assert msg.text.contains(verifyUrl)
        }
    }
}
