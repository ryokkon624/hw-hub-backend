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

/**
 * JWT（Bearer Token）を検証し、認証済みユーザーを Spring Security の SecurityContext に設定するフィルタ。
 *
 * <p>処理概要：
 *
 * <ol>
 *   <li>Authorization ヘッダから "Bearer {token}" を取得
 *   <li>JwtProvider で署名・期限の検証（validateToken）
 *   <li>トークンの subject（sub）から userId を取得
 *   <li>必要に応じて「発行時刻(iat)」と「passwordChangedAt」を比較し、失効判定
 *   <li>有効なら Authentication を生成し、SecurityContext に格納
 * </ol>
 *
 * <p>principal の設計：
 *
 * <ul>
 *   <li>principal に userId.toString() を設定する（現在は UserDetails を使わない簡易方式）
 *   <li>権限（roles）は現時点で未使用のため空リストを設定する
 * </ul>
 *
 * <p>トークン失効（passwordChangedAt）：
 *
 * <ul>
 *   <li>パスワード変更時に passwordChangedAt を更新し、以降に発行された token のみを有効とする
 *   <li>token.iat &lt; passwordChangedAt の場合は無効として SecurityContext を設定しない
 * </ul>
 *
 * <p>除外パス：
 *
 * <ul>
 *   <li>ログイン/認証系の一部 API や OAuth コールバック等は JWT 検証対象外（shouldNotFilter）
 * </ul>
 *
 * <p>注意：
 *
 * <ul>
 *   <li>本フィルタは「認証情報をセットするだけ」で、401 を返す責務は持たない
 *   <li>認可（アクセス制御）は Spring Security の設定（SecurityFilterChain 等）側で行う
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  /**
   * リクエストごとに JWT を検証して SecurityContext を更新する。
   *
   * @param request HTTPリクエスト
   * @param response HTTPレスポンス
   * @param filterChain 次のフィルタ
   * @throws ServletException フィルタ処理の例外
   * @throws IOException I/O例外
   */
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

        boolean isValid = true;

        var opt = userRepository.findPasswordChangedAt(userId);
        if (opt.isPresent()) {
          Instant pwChangedInstant = opt.get().atZone(ZoneId.of("Asia/Tokyo")).toInstant();
          if (tokenIatInstant.isBefore(pwChangedInstant)) {
            isValid = false;
          }
        }

        if (isValid) {
          // userId を principal として Authentication を作成
          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(
                  userId.toString(), null, List.of() // いま役割(role)は使わないので空リスト
                  );

          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }

    filterChain.doFilter(request, response);
  }

  /**
   * JWT 検証を行わないパスを定義する。
   *
   * <p>例：
   *
   * <ul>
   *   <li>ログイン / パスワードリセットなどの public API
   *   <li>Google OAuth の start / callback
   * </ul>
   *
   * @param request HTTPリクエスト
   * @return true の場合はフィルタ処理をスキップする
   */
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();

    // JWT 検証不要
    if (path.startsWith("/api/auth/login")) return true;
    if (path.startsWith("/api/auth/email-verification/verify")) return true;
    if (path.startsWith("/api/auth/email-verification/resend")) return true;
    if (path.startsWith("/api/auth/password-reset/request")) return true;
    if (path.startsWith("/api/auth/password-reset/confirm")) return true;
    if (path.startsWith("/oauth/google")) return true;

    // それ以外のパスはフィルタ実行
    return false;
  }
}
