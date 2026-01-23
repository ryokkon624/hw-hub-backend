package com.hwhub.backend.infrastructure.notification

import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

class SmtpPasswordResetMailSenderSpec extends Specification {

    JavaMailSender mailSender = Mock()
    SmtpPasswordResetMailSender sender = new SmtpPasswordResetMailSender(mailSender)

    def setup() {
        sender.from = "noreply@hwhub.com"
    }

    def "sendPasswordResetMailはJavaMailSender経由でHTMLメールを送信する"() {
        given:
        MimeMessage mimeMessage = Mock()

        when:
        sender.sendPasswordResetMail("user@example.com", "Test User", "https://reset.url", "en")

        then:
        1 * mailSender.createMimeMessage() >> mimeMessage
        1 * mailSender.send(mimeMessage)
    }

    def "sendPasswordResetMailはMessagingException発生時にRuntimeExceptionをthrowする"() {
        given:
        MimeMessage mimeMessage = Mock()

        when:
        sender.sendPasswordResetMail("user@example.com", "Test User", "https://reset.url", "en")

        then:
        1 * mailSender.createMimeMessage() >> mimeMessage
        1 * mimeMessage.setFrom(_) >> { throw new MessagingException("fail") }
        def e = thrown(RuntimeException)
        e.message == "Failed to send password reset email"
        e.cause instanceof MessagingException
    }
}
