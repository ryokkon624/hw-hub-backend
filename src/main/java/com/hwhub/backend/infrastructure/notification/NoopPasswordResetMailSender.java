package com.hwhub.backend.infrastructure.notification;

import com.hwhub.backend.domain.notification.PasswordResetMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnProperty(
    prefix = "hwhub.auth.password-reset",
    name = "send-mail",
    havingValue = "false",
    matchIfMissing = true)
@Component
public class NoopPasswordResetMailSender implements PasswordResetMailSender {

  @Override
  public void sendPasswordResetMail(
      @NonNull String toEmail, String displayName, @NonNull String resetUrl, String locale) {
    log.info("Password reset email not sent (disabled). To: {}, Reset URL: {}", toEmail, resetUrl);
  }
}
