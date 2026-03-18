package com.hwhub.backend.infrastructure.notification;

import com.hwhub.backend.domain.notification.VerificationMailSender;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
    prefix = "hwhub.auth.email-verification",
    name = "send-mail",
    havingValue = "false",
    matchIfMissing = true)
@Component
public class NoopVerificationMailSender implements VerificationMailSender {

  @Override
  public void sendVerificationMail(
      @NonNull String toEmail, String displayName, @NonNull String verifyUrl, String locale) {
    /* do nothing */
  }
}
