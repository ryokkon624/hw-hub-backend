package com.hwhub.backend.security;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

/**
 * {@link CurrentUserId} アノテーションが付与されたコントローラーパラメーターに対して、 JWTから取得した認証済みユーザーIDを注入する {@link
 * HandlerMethodArgumentResolver}。
 *
 * <p>SecurityContextから {@link Authentication} を取得し、getName()をLongに変換して返す。
 * Authenticationが存在しない、またはgetName()がnullの場合は 401 UNAUTHORIZED をスローする。
 */
public class CurrentUserIdArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentUserId.class)
        && Long.class.equals(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthenticated");
    }

    return Long.parseLong(authentication.getName());
  }
}
