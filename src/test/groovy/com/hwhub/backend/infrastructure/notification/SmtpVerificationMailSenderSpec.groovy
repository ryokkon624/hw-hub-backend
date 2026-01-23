package com.hwhub.backend.infrastructure.notification

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

    def "sendVerificationMailはMimeMessageを送信する"() {
        given:
        def toEmail = "user@example.com"
        def displayName = "Hanako"
        def verifyUrl = "http://localhost/verify?token=abc"
        def locale = "ja"

        def mimeMessage = Mock(jakarta.mail.internet.MimeMessage)

        when:
        sender.sendVerificationMail(toEmail, displayName, verifyUrl, locale)

        then:
        1 * javaMailSender.createMimeMessage() >> mimeMessage
        1 * javaMailSender.send(mimeMessage)
        1 * mimeMessage.setFrom(_ as jakarta.mail.internet.InternetAddress) 
        1 * mimeMessage.setRecipient(jakarta.mail.Message.RecipientType.TO, _ as jakarta.mail.internet.InternetAddress)
        1 * mimeMessage.setSubject("[Housework Hub] Please verify your email", "UTF-8")
        1 * mimeMessage.setContent(_ as jakarta.mail.internet.MimeMultipart)
    }

    def "sendVerificationMailはMessagingException発生時にRuntimeExceptionをthrowする"() {
        given:
        def mimeMessage = Mock(jakarta.mail.internet.MimeMessage)

        when:
        sender.sendVerificationMail("fail@example.com", "FailUser", "url", "en")

        then:
        1 * javaMailSender.createMimeMessage() >> mimeMessage
        1 * mimeMessage.setFrom(_) >> { throw new jakarta.mail.MessagingException("fail") }
        def e = thrown(RuntimeException)
        e.message == "Failed to send verification email"
    }
}
