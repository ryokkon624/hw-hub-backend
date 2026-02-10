package com.hwhub.backend.security;

import com.hwhub.backend.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * JWT（JSON Web Token）の生成・検証・解析を行うプロバイダ。
 *
 * <p>本コンポーネントはアプリケーションの認証基盤として機能し、 ログイン成功時のアクセストークン発行および API リクエスト時のトークン検証に利用される。
 *
 * <p>署名方式：
 *
 * <ul>
 *   <li>HMAC-SHA256（HS256）
 * </ul>
 *
 * <p>トークン構造：
 *
 * <pre>
 * Header:
 *   alg: HS256
 *
 * Payload:
 *   sub   : userId
 *   name  : displayName
 *   iat   : 発行時刻
 *   exp   : 有効期限
 * </pre>
 *
 * <p>注意：
 *
 * <ul>
 *   <li>秘密鍵は JwtProperties.secret から取得
 *   <li>トークン失効管理（passwordChangedAt 等）は別レイヤで実施
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final JwtProperties jwtProperties;

  private Key getSigningKey() {
    // secret を元に署名キー作成
    return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
  }

  /**
   * JWT アクセストークンを生成する。
   *
   * <p>主な用途：
   *
   * <ul>
   *   <li>ログイン成功時
   *   <li>OAuth ログイン成功時
   * </ul>
   *
   * <p>格納されるクレーム：
   *
   * <ul>
   *   <li>sub : ユーザーID
   *   <li>name : 表示名
   *   <li>iat : 発行時刻
   *   <li>exp : 有効期限
   * </ul>
   *
   * @param userId ユーザーID
   * @param displayName 表示名
   * @return 署名済み JWT
   */
  public String generateToken(Long userId, String displayName) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + jwtProperties.getExpiryMillis());

    return Jwts.builder()
        .setSubject(String.valueOf(userId)) // ← userId を sub に入れる
        .claim("name", displayName)
        .setIssuedAt(now)
        .setExpiration(expiry)
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * JWT の正当性を検証する。
   *
   * <p>検証内容：
   *
   * <ul>
   *   <li>署名一致
   *   <li>トークン形式
   *   <li>有効期限内か
   * </ul>
   *
   * <p>失敗時は例外を握りつぶして false を返す。
   *
   * @param token JWT
   * @return 有効な場合 true
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * JWT の subject（sub）からユーザーIDを取得する。
   *
   * <p>sub には userId が格納されている前提。
   *
   * @param token JWT
   * @return userId
   */
  public Long getUserIdFromToken(String token) {
    return Long.parseLong(parseClaims(token).getSubject());
  }

  /**
   * JWT の発行時刻（iat）を取得する。
   *
   * <p>用途例：
   *
   * <ul>
   *   <li>passwordChangedAt との比較によるトークン失効判定
   * </ul>
   *
   * @param token JWT
   * @return 発行日時
   */
  public Date getIssuedAtFromToken(String token) {
    return parseClaims(token).getIssuedAt();
  }

  private Claims parseClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
