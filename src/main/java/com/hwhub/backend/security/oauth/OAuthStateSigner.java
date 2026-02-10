package com.hwhub.backend.security.oauth;

import com.hwhub.backend.domain.enums.OAuthFlow;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * OAuth2 / OpenID Connect フローで利用する state パラメータを 署名付きトークンとして生成・検証するコンポーネント。
 *
 * <p>主な用途：
 *
 * <ul>
 *   <li>CSRF 攻撃防止
 *   <li>OAuth フロー識別（LOGIN / LINK など）
 *   <li>コールバック時の主体（subject）特定
 * </ul>
 *
 * <p>state の構造：
 *
 * <pre>
 * base64url(
 *   kind    |  フロー種別（例: LOGIN / LINK）
 *   subject |  ユーザー識別子（例: userId）
 *   exp     |  有効期限（epoch seconds）
 *   nonce   |  乱数
 * ) + "." + HMAC署名
 * </pre>
 *
 * <p>例：
 *
 * <pre>
 * LINK|123|1770256766|3fee00c7ee055a33
 * </pre>
 *
 * <p>署名方式：
 *
 * <ul>
 *   <li>HmacSHA256
 *   <li>Base64URL エンコード（paddingなし）
 * </ul>
 *
 * <p>セキュリティ特性：
 *
 * <ul>
 *   <li>改ざん検知（署名）
 *   <li>期限切れ検知（exp）
 *   <li>リプレイ耐性（nonce）
 * </ul>
 *
 * <p>後方互換：
 *
 * <ul>
 *   <li>旧形式：userId|exp|nonce も検証可能
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class OAuthStateSigner {

  private static final Base64.Decoder B64_URL_DEC = Base64.getUrlDecoder();

  private final Clock clock;

  /**
   * state トークンを生成する。
   *
   * <p>payload にフロー種別（kind）と主体（subject）を埋め込み、 HMAC-SHA256 で署名したうえで返却する。
   *
   * @param kind フロー種別（例: LOGIN / LINK）
   * @param subject 主体識別子（例: userId）
   * @param secret 署名シークレット
   * @param ttlSeconds 有効期限（秒）
   * @return 署名付き state トークン
   */
  public String generate(String kind, String subject, String secret, long ttlSeconds) {
    long now = Instant.now(clock).getEpochSecond();
    long exp = now + ttlSeconds;

    String nonce = Long.toHexString(Double.doubleToLongBits(Math.random()));

    // payload: kind|subject|exp|nonce
    String safeKind = kind == null ? "" : kind;
    String safeSubject = subject == null ? "" : subject;

    String payload = safeKind + "|" + safeSubject + "|" + exp + "|" + nonce;

    String sig = hmacSha256Base64Url(payload, secret);
    return base64Url(payload) + "." + sig;
  }

  /**
   * state トークンの正当性を検証する。
   *
   * <p>検証内容：
   *
   * <ul>
   *   <li>フォーマット妥当性
   *   <li>署名一致
   *   <li>有効期限内か
   * </ul>
   *
   * <p>署名が一致しない、または期限切れの場合は false を返す。
   *
   * @param state 検証対象 state
   * @param secret 署名シークレット
   * @return 検証結果（true: 有効）
   */
  public boolean verify(String state, String secret) {
    if (state == null || !state.contains(".")) return false;

    String[] parts = state.split("\\.", 2);
    String payloadB64 = parts[0];
    String sig = parts[1];

    String payload = new String(Base64.getUrlDecoder().decode(payloadB64), StandardCharsets.UTF_8);

    // 署名一致
    String expectedSig = hmacSha256Base64Url(payload, secret);
    if (!constantTimeEquals(sig, expectedSig)) return false;

    // payload parse
    String[] p = payload.split("\\|", -1);

    // kind|subject|exp|nonce (>=4)
    long exp;
    if (p.length >= 4) {
      exp = Long.parseLong(p[2]);
    } else if (p.length == 3) {
      exp = Long.parseLong(p[1]);
    } else {
      return false;
    }

    // 期限
    long now = Instant.now(clock).getEpochSecond();
    return now <= exp;
  }

  private String hmacSha256Base64Url(String data, String secret) {
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
      byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to sign state", e);
    }
  }

  private String base64Url(String s) {
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(s.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * state から用途（purpose）を抽出する。
   *
   * <pre>
   * kind | subject | exp | nonce
   * </pre>
   *
   * @param state state トークン
   * @return purpose（例: LINK, LOGIN）
   */
  public String extractPurpose(String state) {
    Parsed p = parse(state);
    if (p == null) {
      throw new IllegalArgumentException("Invalid state format");
    }

    String decoded = new String(B64_URL_DEC.decode(p.payload), StandardCharsets.UTF_8);
    String[] parts = decoded.split("\\|", -1);

    if (parts.length < 1) {
      throw new IllegalArgumentException("Invalid state payload structure");
    }

    return parts[0];
  }

  /**
   * state から主体識別子（subject）を抽出する。
   *
   * <p>LINK フロー専用フォーマット：
   *
   * <pre>
   * LINK|{userId}|exp|nonce
   * </pre>
   *
   * <p>verify 実行後に使用することを前提とする。
   *
   * @param state state トークン
   * @return subject（userId）
   * @throws IllegalArgumentException フォーマット不正時
   */
  public String extractSubject(String state) {
    Parsed p = parse(state);
    if (p == null) {
      throw new IllegalArgumentException("Invalid state format");
    }

    String decoded = new String(B64_URL_DEC.decode(p.payload), StandardCharsets.UTF_8);
    String[] parts = decoded.split("\\|", -1);

    if (parts.length < 2) {
      throw new IllegalArgumentException("Invalid state payload structure");
    }

    if (!OAuthFlow.LINK.getCode().equals(parts[0])) {
      throw new IllegalArgumentException("State purpose is not LINK");
    }

    return parts[1];
  }

  private Parsed parse(String state) {
    if (state == null) return null;
    int idx = state.lastIndexOf('.');
    if (idx <= 0 || idx >= state.length() - 1) return null;

    String payload = state.substring(0, idx);
    String sig = state.substring(idx + 1);
    return new Parsed(payload, sig);
  }

  private boolean constantTimeEquals(String a, String b) {
    if (a == null || b == null) return false;
    byte[] ab = a.getBytes(StandardCharsets.UTF_8);
    byte[] bb = b.getBytes(StandardCharsets.UTF_8);
    return MessageDigest.isEqual(ab, bb);
  }

  private record Parsed(String payload, String sig) {}
}
