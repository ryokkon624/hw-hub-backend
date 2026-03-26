package com.hwhub.backend.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import com.hwhub.backend.domain.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class JwtAuthenticationFilterSpec extends Specification {

    JwtProvider jwtProvider = Mock()
    UserRepository userRepository = Mock() // Mock UserRepository
    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, userRepository)

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
        1 * jwtProvider.getIssuedAtFromToken("valid-token") >> new Date()
        
        and: "UserRepository がパスワード変更日時を返す（変更なし）"
        1 * userRepository.findPasswordChangedAt(123L) >> Optional.empty()

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
        auth.authorities.empty        // ロールは空リスト
    }

    def "パスワード変更日時がトークン発行日時より後の場合 Authentication はセットされない"() {
        given:
        def request = Mock(HttpServletRequest) {
            getHeader("Authorization") >> "Bearer valid-token"
        }
        def response = Mock(HttpServletResponse)
        def chain = Mock(FilterChain)
        
        def issuedAt = new Date()
        def passwordChangedAt = issuedAt.toInstant().plusSeconds(60).atZone(java.time.ZoneId.of("Asia/Tokyo")).toLocalDateTime()

        and:
        1 * jwtProvider.validateToken("valid-token") >> true
        1 * jwtProvider.getUserIdFromToken("valid-token") >> 123L
        1 * jwtProvider.getIssuedAtFromToken("valid-token") >> issuedAt
        1 * userRepository.findPasswordChangedAt(123L) >> Optional.of(passwordChangedAt)

        when:
        filter.doFilterInternal(request, response, chain)

        then:
        1 * chain.doFilter(request, response)
        SecurityContextHolder.context.authentication == null
    }

    def "パスワード変更日時がトークン発行日時より前の場合 Authentication がセットされる"() {
        given:
        def request = Mock(HttpServletRequest) {
            getHeader("Authorization") >> "Bearer valid-token"
        }
        def response = Mock(HttpServletResponse)
        def chain = Mock(FilterChain)
        
        def issuedAt = new Date()
        def passwordChangedAt = issuedAt.toInstant().minusSeconds(60).atZone(java.time.ZoneId.of("Asia/Tokyo")).toLocalDateTime()

        and:
        1 * jwtProvider.validateToken("valid-token") >> true
        1 * jwtProvider.getUserIdFromToken("valid-token") >> 123L
        1 * jwtProvider.getIssuedAtFromToken("valid-token") >> issuedAt
        1 * userRepository.findPasswordChangedAt(123L) >> Optional.of(passwordChangedAt)

        when:
        filter.doFilterInternal(request, response, chain)

        then:
        1 * chain.doFilter(request, response)
        SecurityContextHolder.context.authentication != null
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

    def "トークンの発行日時(iat)が取得できない場合 Authentication はセットされない"() {
        given:
        def request = Mock(HttpServletRequest) {
            getHeader("Authorization") >> "Bearer valid-token"
        }
        def response = Mock(HttpServletResponse)
        def chain = Mock(FilterChain)

        and:
        1 * jwtProvider.validateToken("valid-token") >> true
        1 * jwtProvider.getUserIdFromToken("valid-token") >> 123L
        1 * jwtProvider.getIssuedAtFromToken("valid-token") >> null

        when:
        filter.doFilterInternal(request, response, chain)

        then:
        1 * chain.doFilter(request, response)
        SecurityContextHolder.context.authentication == null
    }

    @spock.lang.Unroll
    def "shouldNotFilter はパス '#path' のとき #expected を返す"() {
        given:
        def request = Mock(HttpServletRequest) {
            getServletPath() >> path
        }

        expect:
        filter.shouldNotFilter(request) == expected

        where:
        path                                    | expected
        "/api/auth/login"                       | true
        "/api/auth/email-verification/verify"   | true
        "/api/auth/email-verification/resend"   | true
        "/api/auth/password-reset/request"      | true
        "/api/auth/password-reset/confirm"      | true
        "/oauth/google"                         | true
        "/api/houseworks"                       | false
        "/api/user/profile"                     | false
    }

    def "Authorization ヘッダーが Bearer のみでトークンがない場合 skips"() {
        given:
        def request = Mock(HttpServletRequest) {
            getHeader("Authorization") >> "Bearer "
        }
        def response = Mock(HttpServletResponse)
        def chain = Mock(FilterChain)

        when:
        filter.doFilterInternal(request, response, chain)

        then:
        1 * chain.doFilter(request, response)
        SecurityContextHolder.context.authentication == null
    }
}
