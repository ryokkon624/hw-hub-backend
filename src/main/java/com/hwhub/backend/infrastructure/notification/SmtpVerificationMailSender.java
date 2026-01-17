package com.hwhub.backend.infrastructure.notification;

import com.hwhub.backend.domain.notification.VerificationMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
    prefix = "hwhub.auth.email-verification",
    name = "send-mail",
    havingValue = "true")
@Component
@RequiredArgsConstructor
public class SmtpVerificationMailSender implements VerificationMailSender {

  private final JavaMailSender mailSender;

  @Value("${hwhub.mail.from}")
  private String from;

  @Override
  public void sendVerificationMail(
      String toEmail, String displayName, String verifyUrl, String locale) {

    // TODO: 件名・本文はまずは最小（後でi18nテンプレにする。アカウント作成時にlocalを選択しているのでそれを使える）
    String subject = "[HwHub] Please verify your email";
    String body =
        "Hi "
            + (displayName == null ? "" : displayName)
            + "\n\n"
            + "Please verify your email by clicking the link below:\n"
            + verifyUrl
            + "\n\n"
            + "If you did not request this, you can ignore this email.\n";

    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom(from);
    msg.setTo(toEmail);
    msg.setSubject(subject);
    msg.setText(body);

    mailSender.send(msg);
  }
}
