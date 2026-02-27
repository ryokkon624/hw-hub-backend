package com.hwhub.backend.domain.notification;

import org.springframework.lang.NonNull;

public interface PasswordResetMailSender {
  void sendPasswordResetMail(
      @NonNull String toEmail, String displayName, @NonNull String resetUrl, String locale);
}
