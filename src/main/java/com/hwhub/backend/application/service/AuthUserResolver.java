package com.hwhub.backend.application.service;

import com.hwhub.backend.security.JwtAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Spring Security の SecurityContext から「現在ログインしているユーザーID」を取り出すためのユーティリティ。
 *
 * <p>設計意図：
 *
 * <ul>
 *   <li>Controller/Service が SecurityContext の取り出しロジックを重複して持たない
 *   <li>principal の形式を 1 箇所に閉じ込め、将来の変更（UserDetails化など）に備える
 * </ul>
 *
 * <p>前提：
 *
 * <ul>
 *   <li>{@link JwtAuthenticationFilter} が principal に userId.toString() を格納している
 * </ul>
 *
 * <p>例外方針：
 *
 * <ul>
 *   <li>未認証の場合は IllegalStateException
 *   <li>principal が想定外形式の場合も IllegalStateException（原因特定しやすくする）
 * </ul>
 */
@Component
public class AuthUserResolver {

  /**
   * SecurityContext からログイン中ユーザーの userId を取得する。
   *
   * <p>JwtAuthenticationFilter が principal に userId（String）を入れている前提。
   *
   * @return ログイン中ユーザーID
   * @throws IllegalStateException 未認証、または principal が想定外の場合
   */
  public Long requireUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new IllegalStateException("Unauthenticated");
    }

    Object principal = auth.getPrincipal();

    // JwtAuthenticationFilter が userId.toString()を格納しているため
    if (principal instanceof String s) {
      try {
        return Long.parseLong(s);
      } catch (NumberFormatException e) {
        throw new IllegalStateException("Invalid principal (not userId): " + s, e);
      }
    }

    // principal の型を変えた時に原因が分かるように。 just in case
    throw new IllegalStateException("Unsupported principal type: " + principal.getClass());
  }
}
