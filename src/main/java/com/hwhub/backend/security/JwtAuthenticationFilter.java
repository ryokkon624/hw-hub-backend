package com.hwhub.backend.security;

import com.hwhub.backend.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);

      if (jwtProvider.validateToken(token)) {
        Long userId = jwtProvider.getUserIdFromToken(token);

        Date issuedAt = jwtProvider.getIssuedAtFromToken(token);
        if (issuedAt == null) {
          filterChain.doFilter(request, response);
          return;
        }
        Instant tokenIatInstant = issuedAt.toInstant();

        userRepository
            .findPasswordChangedAt(userId)
            .ifPresentOrElse(
                passwordChangedAt -> {
                  Instant pwChangedInstant =
                      passwordChangedAt.atZone(ZoneId.of("Asia/Tokyo")).toInstant();

                  if (tokenIatInstant.isBefore(pwChangedInstant)) {
                    // 古いトークンなので認証しない
                    return;
                  }
                  // userId を principal として Authentication を作成
                  UsernamePasswordAuthenticationToken authentication =
                      new UsernamePasswordAuthenticationToken(
                          userId.toString(), null, List.of() // いま役割(role)は使わないので空リスト
                          );

                  authentication.setDetails(
                      new WebAuthenticationDetailsSource().buildDetails(request));

                  SecurityContextHolder.getContext().setAuthentication(authentication);
                },
                () -> {
                  // PasswordChangeedAtはnull許容のため認証OK
                });
      }
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();

    // ログインAPIは JWT 検証不要
    if (path.startsWith("/api/auth/login")) return true;
    if (path.startsWith("/api/auth/email-verification/verify")) return true;
    if (path.startsWith("/api/auth/email-verification/resend")) return true;
    if (path.startsWith("/api/auth/password-reset/request")) return true;
    if (path.startsWith("/api/auth/password-reset/confirm")) return true;

    // それ以外のパスはフィルタ実行
    return false;
  }
}
