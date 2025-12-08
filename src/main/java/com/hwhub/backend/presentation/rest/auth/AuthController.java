package com.hwhub.backend.presentation.rest.auth;

import com.hwhub.backend.application.service.AuthService;
import com.hwhub.backend.domain.model.UserModel;
import com.hwhub.backend.presentation.rest.auth.dto.LoginRequest;
import com.hwhub.backend.presentation.rest.auth.dto.LoginResponse;
import com.hwhub.backend.presentation.rest.auth.dto.LoginUserDto;
import com.hwhub.backend.presentation.rest.auth.dto.RegisterRequest;
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
  public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {

    UserModel model =
        UserModel.create(
            request.email(), request.password(), request.displayName(), request.locale());

    var info = authService.register(model);
    LoginResponse response = new LoginResponse(info.token(), LoginUserDto.fromModel(info.user()));
    return ResponseEntity.ok(response);
  }
}
