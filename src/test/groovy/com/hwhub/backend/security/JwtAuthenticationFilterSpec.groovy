package com.hwhub.backend.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class JwtAuthenticationFilterSpec extends Specification {

    JwtProvider jwtProvider = Mock()
    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider)

    def cleanup() {
        // 各テスト後にコンテキストをクリア
        SecurityContextHolder.clearContext()
    }

    def "有効なBearerトークンがある場合 Authentication がセットされる"() {
        given:
        def request = Mock(HttpServletRequest) {
            getHeader("Authorization") >> "Bearer valid-token"
        }
        def response = Mock(HttpServletResponse)
        def chain = Mock(FilterChain)

        and: "JwtProvider が有効判定 & userId 返す"
        1 * jwtProvider.validateToken("valid-token") >> true
        1 * jwtProvider.getUserIdFromToken("valid-token") >> 123L

        when:
        filter.doFilterInternal(request, response, chain)

        then: "filterChain は必ず呼ばれる"
        1 * chain.doFilter(request, response)

        and: "SecurityContext に Authentication が入っている"
        def auth = SecurityContextHolder.context.authentication
        auth != null
        auth.principal == "123"      // userId.toString()
        auth.credentials == null
        auth.authorities.empty        // ロールは空リスト
    }

    def "トークンが無効な場合 Authentication はセットされない"() {
        given:
        def request = Mock(HttpServletRequest) {
            getHeader("Authorization") >> "Bearer invalid-token"
        }
        def response = Mock(HttpServletResponse)
        def chain = Mock(FilterChain)

        and:
        1 * jwtProvider.validateToken("invalid-token") >> false
        0 * jwtProvider.getUserIdFromToken(_)

        when:
        filter.doFilterInternal(request, response, chain)

        then:
        1 * chain.doFilter(request, response)

        and:
        SecurityContextHolder.context.authentication == null
    }

    def "Authorization ヘッダが無い場合は JwtProvider を呼ばずに通過する"() {
        given:
        def request = Mock(HttpServletRequest) {
            getHeader("Authorization") >> null
        }
        def response = Mock(HttpServletResponse)
        def chain = Mock(FilterChain)

        when:
        filter.doFilterInternal(request, response, chain)

        then:
        1 * chain.doFilter(request, response)
        0 * jwtProvider._  // validateToken / getUserIdFromToken どちらも呼ばれない

        and:
        SecurityContextHolder.context.authentication == null
    }

    def "shouldNotFilter は /api/auth/login のとき true を返す"() {
        given:
        def request = Mock(HttpServletRequest) {
            getServletPath() >> "/api/auth/login"
        }

        expect:
        filter.shouldNotFilter(request)
    }

    def "shouldNotFilter は login 以外のパスでは false を返す"() {
        given:
        def request = Mock(HttpServletRequest) {
            getServletPath() >> "/api/houseworks"
        }

        expect:
        !filter.shouldNotFilter(request)
    }
}
