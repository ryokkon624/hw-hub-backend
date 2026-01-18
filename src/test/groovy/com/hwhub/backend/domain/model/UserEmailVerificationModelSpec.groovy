package com.hwhub.backend.domain.model

import spock.lang.Specification
import java.time.LocalDateTime

class UserEmailVerificationModelSpec extends Specification {

    def "create creates a new model with hashed token and expiration"() {
        given:
        def userId = 10L
        def token = "raw-token"
        def requestedAt = LocalDateTime.of(2025, 1, 1, 10, 0, 0)
        def ttlMinutes = 30

        when:
        def model = UserEmailVerificationModel.create(userId, token, requestedAt, ttlMinutes)

        then:
        model.userEmailVerificationId == null
        model.userId == userId
        // SHA-256 hash of "raw-token"
        model.tokenHash == UserEmailVerificationModel.hashToken("raw-token")
        model.requestedAt == requestedAt
        model.expiresAt == requestedAt.plusMinutes(ttlMinutes)
        model.usedAt == null
        model.requestCount == 1
    }

    def "reconstruct reconstructs the model with all properties"() {
        given:
        def id = 100L
        def userId = 200L
        def hash = [1, 2, 3] as byte[]
        def expire = LocalDateTime.now().plusHours(1)
        def used = LocalDateTime.now()
        def req = LocalDateTime.now().minusHours(1)
        def count = 5

        when:
        def model = UserEmailVerificationModel.reconstruct(id, userId, hash, expire, used, req, count)

        then:
        model.userEmailVerificationId == id
        model.userId == userId
        model.tokenHash == hash
        model.expiresAt == expire
        model.usedAt == used
        model.requestedAt == req
        model.requestCount == count
    }
}
