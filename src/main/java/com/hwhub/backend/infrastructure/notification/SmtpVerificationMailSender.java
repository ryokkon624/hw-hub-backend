package com.hwhub.backend.infrastructure.notification;

import com.hwhub.backend.domain.notification.VerificationMailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
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
      @NonNull String toEmail, String displayName, @NonNull String verifyUrl, String locale) {

    String subject = "[Housework Hub] Please verify your email";
    String htmlContent = buildHtmlContent(displayName, verifyUrl);

    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setFrom(Objects.requireNonNull(from));
      helper.setTo(toEmail);
      helper.setSubject(subject);
      helper.setText(Objects.requireNonNull(htmlContent), true); // true = isHtml

      mailSender.send(message);
    } catch (MessagingException e) {
      log.error("Failed to send verification email to {}", toEmail, e);
      throw new RuntimeException("Failed to send verification email", e);
    }
  }

  private String buildHtmlContent(String displayName, String verifyUrl) {
    String name = displayName == null || displayName.isEmpty() ? "User" : displayName;

    return """
        <!DOCTYPE html>
        <html>
        <head>
          <meta charset="utf-8">
          <style>
            body { font-family: sans-serif; background-color: #f8fafc; margin: 0; padding: 0; }
            .container { max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1); }
            .header { background-color: #059669; padding: 20px; text-align: center; }
            .header h1 { color: #ffffff; margin: 0; font-size: 24px; }
            .content { padding: 32px; color: #1e293b; line-height: 1.6; }
            .button-container { text-align: center; margin: 32px 0; }
            .button { background-color: #059669; color: #ffffff; padding: 12px 24px; text-decoration: none; border-radius: 6px; font-weight: bold; display: inline-block; }
            .footer { background-color: #f1f5f9; padding: 16px; text-align: center; color: #64748b; font-size: 12px; }
          </style>
        </head>
        <body>
          <div style="padding: 40px 0;">
            <div class="container">
              <div class="header">
                <h1>Housework Hub</h1>
              </div>
              <div class="content">
                <p>Hi %s,</p>
                <p>Thank you for signing up for Housework Hub! To complete your registration, please verify your email address by clicking the button below.</p>
                <div class="button-container">
                  <a href="%s" class="button">Verify Email</a>
                </div>
                <p>If you did not create an account, no further action is required.</p>
              </div>
              <div class="footer">
                &copy; Housework Hub
              </div>
            </div>
          </div>
        </body>
        </html>
        """
        .formatted(name, verifyUrl);
  }
}
