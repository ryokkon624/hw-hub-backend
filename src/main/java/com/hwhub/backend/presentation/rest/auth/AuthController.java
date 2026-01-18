package com.hwhub.backend.presentation.rest.auth;

import com.hwhub.backend.application.service.AuthService;
import com.hwhub.backend.application.service.AuthService.RegisterInfo;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.presentation.rest.auth.dto.LoginRequest;
import com.hwhub.backend.presentation.rest.auth.dto.LoginResponse;
import com.hwhub.backend.presentation.rest.auth.dto.LoginUserDto;
import com.hwhub.backend.presentation.rest.auth.dto.RegisterRequest;
import com.hwhub.backend.presentation.rest.auth.dto.RegisterResponse;
import com.hwhub.backend.presentation.rest.auth.dto.ResendVerificationRequest;
import com.hwhub.backend.presentation.rest.auth.dto.VerifyEmailRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    var info = authService.login(request);
    LoginResponse response = new LoginResponse(info.token(), LoginUserDto.fromModel(info.user()));
    return ResponseEntity.ok(response);
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {

    UserModel model =
        UserModel.create(
            request.email(), request.password(), request.displayName(), request.locale());

    RegisterInfo info = authService.register(model);

    RegisterResponse response =
        new RegisterResponse(
            info.emailVerificationRequired(),
            info.token(),
            LoginUserDto.fromModel(info.user()),
            info.verificationExpiresAt() == null ? null : info.verificationExpiresAt().toString());

    return ResponseEntity.ok(response);
  }

  @PostMapping("/email-verification/verify")
  public ResponseEntity<Void> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
    authService.verifyEmail(request.token());
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/email-verification/resend")
  public ResponseEntity<Void> resendVerification(
      @Valid @RequestBody ResendVerificationRequest request) {
    authService.resendVerification(request.email());
    return ResponseEntity.noContent().build();
  }
}
