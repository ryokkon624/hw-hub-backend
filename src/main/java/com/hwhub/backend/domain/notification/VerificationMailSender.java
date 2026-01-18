package com.hwhub.backend.domain.notification;

public interface VerificationMailSender {
  void sendVerificationMail(String toEmail, String displayName, String verifyUrl, String locale);
}
