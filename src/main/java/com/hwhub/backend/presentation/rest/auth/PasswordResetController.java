package com.hwhub.backend.presentation.rest.auth;

import com.hwhub.backend.application.service.PasswordResetService;
import com.hwhub.backend.presentation.rest.auth.dto.ConfirmResetRequest;
import com.hwhub.backend.presentation.rest.auth.dto.RequestResetRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/password-reset")
public class PasswordResetController {

  private final PasswordResetService passwordResetService;

  /** パスワードリセットメールを要求する。 情報漏えい防止のため、存在しないメールでも常に 204 を返す。 */
  @PostMapping("/request")
  public ResponseEntity<Void> requestReset(@Valid @RequestBody RequestResetRequest request) {
    passwordResetService.requestReset(request.email());
    return ResponseEntity.noContent().build();
  }

  /** パスワードリセットを確定する。 token が無効/期限切れの場合は適切な errorCode を返す。 */
  @PostMapping("/confirm")
  public ResponseEntity<Void> confirmReset(@Valid @RequestBody ConfirmResetRequest request) {
    passwordResetService.confirmReset(request.token(), request.newPassword());
    return ResponseEntity.noContent().build();
  }
}
