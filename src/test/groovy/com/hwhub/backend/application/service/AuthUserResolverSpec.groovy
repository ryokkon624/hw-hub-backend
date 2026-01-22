package com.hwhub.backend.application.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class AuthUserResolverSpec extends Specification {

    AuthUserResolver resolver = new AuthUserResolver()
    SecurityContext securityContext = Mock()
    Authentication authentication = Mock()

    def setup() {
        SecurityContextHolder.setContext(securityContext)
    }

    def cleanup() {
        SecurityContextHolder.clearContext()
    }

    def "requireUserIdはauthenticationがnullの場合、IllegalStateExceptionをthrowする"() {
        when:
        resolver.requireUserId()

        then:
        1 * securityContext.getAuthentication() >> null
        def e = thrown(IllegalStateException)
        e.message == "Unauthenticated"
    }

    def "requireUserIdは未認証の場合、IllegalStateExceptionをthrowする"() {
        when:
        resolver.requireUserId()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.isAuthenticated() >> false
        def e = thrown(IllegalStateException)
        e.message == "Unauthenticated"
    }

    def "requireUserIdはprincipalがStringかつ解析可能な場合、userIdを返す"() {
        when:
        def result = resolver.requireUserId()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.isAuthenticated() >> true
        1 * authentication.getPrincipal() >> "12345"
        result == 12345L
    }

    def "requireUserIdはprincipalがStringだが解析不可能な場合、IllegalStateExceptionをthrowする"() {
        when:
        resolver.requireUserId()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.isAuthenticated() >> true
        1 * authentication.getPrincipal() >> "invalid-number"
        def e = thrown(IllegalStateException)
        e.message.startsWith("Invalid principal (not userId):")
    }

    def "requireUserIdはprincipalがサポートされていない型の場合、IllegalStateExceptionをthrowする"() {
        when:
        resolver.requireUserId()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.isAuthenticated() >> true
        1 * authentication.getPrincipal() >> new Object()
        def e = thrown(IllegalStateException)
        e.message.startsWith("Unsupported principal type:")
    }
}
