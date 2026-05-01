package com.hwhub.backend.security

import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

class CurrentUserIdArgumentResolverSpec extends Specification {

    CurrentUserIdArgumentResolver resolver = new CurrentUserIdArgumentResolver()

    def cleanup() {
        SecurityContextHolder.clearContext()
    }

    // ------------------------------------------------------------------
    // supportsParameter
    // ------------------------------------------------------------------

    def "supportsParameter は @CurrentUserId かつ Long 型の場合 true を返す"() {
        given:
        def method = SampleController.getDeclaredMethod("withAnnotation", Long)
        def param = MethodParameter.forExecutable(method, 0)

        expect:
        resolver.supportsParameter(param)
    }

    def "supportsParameter は @CurrentUserId がない場合 false を返す"() {
        given:
        def method = SampleController.getDeclaredMethod("withoutAnnotation", Long)
        def param = MethodParameter.forExecutable(method, 0)

        expect:
        !resolver.supportsParameter(param)
    }

    // ------------------------------------------------------------------
    // resolveArgument - 正常系
    // ------------------------------------------------------------------

    def "resolveArgument は SecurityContext に Authentication がある場合 userId を返す"() {
        given:
        def auth = Mock(Authentication)
        auth.getName() >> "42"

        def context = Mock(SecurityContext)
        context.getAuthentication() >> auth
        SecurityContextHolder.setContext(context)

        def method = SampleController.getDeclaredMethod("withAnnotation", Long)
        def param = MethodParameter.forExecutable(method, 0)

        when:
        def result = resolver.resolveArgument(param, null, null, null)

        then:
        result == 42L
    }

    // ------------------------------------------------------------------
    // resolveArgument - 異常系
    // ------------------------------------------------------------------

    def "resolveArgument は Authentication が null の場合 UNAUTHORIZED を投げる"() {
        given:
        def context = Mock(SecurityContext)
        context.getAuthentication() >> null
        SecurityContextHolder.setContext(context)

        def method = SampleController.getDeclaredMethod("withAnnotation", Long)
        def param = MethodParameter.forExecutable(method, 0)

        when:
        resolver.resolveArgument(param, null, null, null)

        then:
        def ex = thrown(ResponseStatusException)
        ex.statusCode == HttpStatus.UNAUTHORIZED
    }

    def "resolveArgument は Authentication.getName() が null の場合 UNAUTHORIZED を投げる"() {
        given:
        def auth = Mock(Authentication)
        auth.getName() >> null

        def context = Mock(SecurityContext)
        context.getAuthentication() >> auth
        SecurityContextHolder.setContext(context)

        def method = SampleController.getDeclaredMethod("withAnnotation", Long)
        def param = MethodParameter.forExecutable(method, 0)

        when:
        resolver.resolveArgument(param, null, null, null)

        then:
        def ex = thrown(ResponseStatusException)
        ex.statusCode == HttpStatus.UNAUTHORIZED
    }

    // ------------------------------------------------------------------
    // テスト用スタブコントローラ
    // ------------------------------------------------------------------

    static class SampleController {
        void withAnnotation(@CurrentUserId Long userId) {}
        void withoutAnnotation(Long userId) {}
    }
}
