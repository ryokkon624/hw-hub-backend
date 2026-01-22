package com.hwhub.backend.domain.notification;

public interface PasswordResetMailSender {
  void sendPasswordResetMail(String toEmail, String displayName, String resetUrl, String locale);
}
