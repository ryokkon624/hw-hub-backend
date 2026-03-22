package com.hwhub.backend.config;

import com.hwhub.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(
            ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .authorizeHttpRequests(
            auth ->
                auth
                    // CORSプリフライト（OPTIONS）を全許可
                    .requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()
                    // actuator
                    .requestMatchers("/actuator/health", "/actuator/info")
                    .permitAll()
                    // 認証なしで叩けるAPI: swagger, auth, /api/auth/password-reset
                    .requestMatchers(
                        "/api/auth/login",
                        "/api/auth/email-verification/verify",
                        "/api/auth/email-verification/resend",
                        "/api/auth/password-reset/request",
                        "/api/auth/password-reset/confirm",
                        "/oauth/google/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/register")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/household-invitations/**")
                    .permitAll()
                    // protected
                    .requestMatchers("/api/admin/**")
                    .authenticated()
                    .requestMatchers("/api/users/me/roles")
                    .authenticated()
                    .requestMatchers("/api/**")
                    .authenticated()
                    // others
                    .anyRequest()
                    .permitAll());

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
