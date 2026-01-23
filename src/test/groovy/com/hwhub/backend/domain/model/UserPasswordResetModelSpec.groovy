package com.hwhub.backend.domain.model

import spock.lang.Specification

import java.time.LocalDateTime

class UserPasswordResetModelSpec extends Specification {

    def "createは新しいインスタンスを正しく初期化する"() {
        given:
        Long userId = 100L
        String token = "secret-token"
        LocalDateTime requestedAt = LocalDateTime.now()
        int ttlMinutes = 30

        when:
        def model = UserPasswordResetModel.create(userId, token, requestedAt, ttlMinutes)

        then:
        model.userPasswordResetId == null
        model.userId == userId
        model.tokenHash != null
        model.tokenHash.length == 32 // SHA-256
        model.expiresAt == requestedAt.plusMinutes(ttlMinutes)
        model.usedAt == null
        model.requestedAt == requestedAt
        model.requestCount == 1
    }

    def "reconstructはインスタンスを正しく復元する"() {
        given:
        Long id = 1L
        Long userId = 100L
        byte[] hash = [1, 2, 3] as byte[]
        LocalDateTime expiredAt = LocalDateTime.now().plusHours(1)
        LocalDateTime usedAt = LocalDateTime.now()
        LocalDateTime requestedAt = LocalDateTime.now().minusHours(1)
        int count = 5

        when:
        def model = UserPasswordResetModel.reconstruct(id, userId, hash, expiredAt, usedAt, requestedAt, count)

        then:
        model.userPasswordResetId == id
        model.userId == userId
        model.tokenHash == hash
        model.expiresAt == expiredAt
        model.usedAt == usedAt
        model.requestedAt == requestedAt
        model.requestCount == count
    }

    def "hashTokenはSHA-256ハッシュを生成する"() {
        when:
        def hash = UserPasswordResetModel.hashToken("abc")

        then:
        hash.length == 32
    }
}
