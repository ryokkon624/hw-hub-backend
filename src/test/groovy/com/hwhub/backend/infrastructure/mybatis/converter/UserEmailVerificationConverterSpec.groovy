package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.UserEmailVerificationModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserEmailVerification
import java.sql.Timestamp
import java.time.LocalDateTime
import spock.lang.Specification

class UserEmailVerificationConverterSpec extends Specification {

    def "toModel converts entity to model correctly"() {
        given:
        def now = LocalDateTime.now()
        def entity = new TUserEmailVerification()
        entity.setUserEmailVerificationId(1L)
        entity.setUserId(2L)
        entity.setTokenHash([1, 2] as byte[])
        entity.setExpiresAt(Timestamp.valueOf(now.plusHours(1)))
        entity.setUsedAt(null)
        entity.setRequestedAt(Timestamp.valueOf(now))
        entity.setRequestCount(1)

        when:
        def model = UserEmailVerificationConverter.toModel(entity)

        then:
        model.userEmailVerificationId == 1L
        model.userId == 2L
        model.tokenHash == [1, 2] as byte[]
        // Timestamp -> LocalDateTime conversion check (ignoring nanos precision diffs if any)
        model.expiresAt == now.plusHours(1)
        model.usedAt == null
        model.requestedAt == now
        model.requestCount == 1
    }

    def "toModel returns null if entity is null"() {
        expect:
        UserEmailVerificationConverter.toModel(null) == null
    }

    def "toEntity converts model to entity correctly"() {
        given:
        def now = LocalDateTime.now()
        def model = UserEmailVerificationModel.reconstruct(
                10L, 20L, [3, 4] as byte[], now.plusHours(1), null, now, 5
        )

        when:
        def entity = UserEmailVerificationConverter.toEntity(model)

        then:
        entity.userEmailVerificationId == 10L
        entity.userId == 20L
        entity.tokenHash == [3, 4] as byte[]
        entity.expiresAt == Timestamp.valueOf(now.plusHours(1))
        entity.usedAt == null
        entity.requestedAt == Timestamp.valueOf(now)
        entity.requestCount == 5
    }

    def "toEntity returns null if model is null"() {
        expect:
        UserEmailVerificationConverter.toEntity(null) == null
    }
}
