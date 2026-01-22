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

@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final JwtProperties jwtProperties;

  private Key getSigningKey() {
    // secret を元に署名キー作成
    return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
  }

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

  // トークンが正しいかチェック
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  // トークンから userId を取り出す
  public Long getUserIdFromToken(String token) {
    return Long.parseLong(parseClaims(token).getSubject());
  }

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
