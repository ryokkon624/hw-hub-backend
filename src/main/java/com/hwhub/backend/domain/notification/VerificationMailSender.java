package com.hwhub.backend.domain.notification;

import org.jspecify.annotations.NonNull;

public interface VerificationMailSender {
  void sendVerificationMail(
      @NonNull String toEmail, String displayName, @NonNull String verifyUrl, String locale);
}
