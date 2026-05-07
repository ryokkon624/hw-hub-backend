package com.hwhub.backend.security

import com.hwhub.backend.config.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import spock.lang.Specification

import java.nio.charset.StandardCharsets

class JwtProviderSpec extends Specification {

    JwtProperties jwtProperties
    JwtProvider jwtProvider

    def setup() {
        jwtProperties = new JwtProperties()
        // HS256用の十分な長さのシークレット（32バイト以上）
        jwtProperties.secret = "x".repeat(64)
        jwtProperties.expiryMillis = 3600000L          // 1時間
        jwtProperties.refreshExpiryMillis = 2592000000L // 30日

        jwtProvider = new JwtProvider(jwtProperties)
    }

    def "generateToken は userId をsubに、displayName を name クレームに入れた有効なJWTを生成する"() {
        given:
        Long userId = 123L
        String displayName = "Test User"

        when:
        String token = jwtProvider.generateToken(userId, displayName)

        then:
        token != null
        jwtProvider.validateToken(token)

        and: "中身のクレームを検証する"
        def key = Keys.hmacShaKeyFor(jwtProperties.secret.getBytes(StandardCharsets.UTF_8))
        def claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()

        claims.getSubject() == userId.toString()
        claims.get("name") == displayName
        claims.getExpiration().after(new Date())
    }

    def "validateToken は正しいトークンなら true を返す"() {
        given:
        String token = jwtProvider.generateToken(1L, "User")

        expect:
        jwtProvider.validateToken(token)
    }

    def "validateToken はシグネチャ破壊されたトークンなら false を返す"() {
        given:
        String token = jwtProvider.generateToken(1L, "User")
        String broken = token + "x"  // 適当に壊す

        expect:
        !jwtProvider.validateToken(broken)
    }

    def "validateToken は期限切れトークンなら false を返す"() {
        given:
        def key = Keys.hmacShaKeyFor(jwtProperties.secret.getBytes(StandardCharsets.UTF_8))
        Date now = new Date()
        Date past = new Date(now.time - 1000L)

        // すでに期限切れのトークンを自前で発行
        String expiredToken = Jwts.builder()
                .setSubject("123")
                .setIssuedAt(past)
                .setExpiration(past)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()

        expect:
        !jwtProvider.validateToken(expiredToken)
    }

    def "validateToken は null のとき false を返す（IllegalArgumentException 経路）"() {
        expect:
        !jwtProvider.validateToken(null)
    }

    def "getUserIdFromToken はトークンのsubから userId を取得する"() {
        given:
        Long userId = 999L
        String token = jwtProvider.generateToken(userId, "User 999")

        when:
        Long extracted = jwtProvider.getUserIdFromToken(token)

        then:
        extracted == userId
    }

    def "getIssuedAtFromToken はトークンのiatを取得する"() {
        given:
        String token = jwtProvider.generateToken(1L, "User")

        when:
        Date iat = jwtProvider.getIssuedAtFromToken(token)

        then:
        iat != null
        iat.before(new Date(System.currentTimeMillis() + 1000))
    }

    // ── リフレッシュトークン ─────────────────────────────────────────────

    def "generateRefreshToken は type=refresh クレームを持つ有効なJWTを生成する"() {
        given:
        Long userId = 42L

        when:
        String token = jwtProvider.generateRefreshToken(userId)

        then:
        token != null

        and: "type=refresh クレームが含まれること"
        def key = Keys.hmacShaKeyFor(jwtProperties.secret.getBytes(StandardCharsets.UTF_8))
        def claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
        claims.getSubject() == userId.toString()
        claims.get("type") == "refresh"
        claims.getExpiration().after(new Date())
    }

    def "validateRefreshToken は正しいリフレッシュトークンなら true を返す"() {
        given:
        String token = jwtProvider.generateRefreshToken(1L)

        expect:
        jwtProvider.validateRefreshToken(token)
    }

    def "validateRefreshToken はアクセストークン(type無し)を渡すと false を返す"() {
        given:
        String accessToken = jwtProvider.generateToken(1L, "User")

        expect:
        !jwtProvider.validateRefreshToken(accessToken)
    }

    def "validateRefreshToken はシグネチャ破壊されたトークンなら false を返す"() {
        given:
        String token = jwtProvider.generateRefreshToken(1L) + "x"

        expect:
        !jwtProvider.validateRefreshToken(token)
    }

    def "validateRefreshToken は期限切れリフレッシュトークンなら false を返す"() {
        given:
        def key = Keys.hmacShaKeyFor(jwtProperties.secret.getBytes(StandardCharsets.UTF_8))
        Date past = new Date(System.currentTimeMillis() - 1000L)
        String expiredToken = Jwts.builder()
                .setSubject("1")
                .claim("type", "refresh")
                .setIssuedAt(past)
                .setExpiration(past)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()

        expect:
        !jwtProvider.validateRefreshToken(expiredToken)
    }

    def "validateRefreshToken は null のとき false を返す"() {
        expect:
        !jwtProvider.validateRefreshToken(null)
    }
}
