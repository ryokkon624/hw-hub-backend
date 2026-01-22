package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.UserPasswordResetModel
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TUserPasswordReset
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId

class UserPasswordResetConverterSpec extends Specification {

    def "toModelはエンティティがnullの場合nullを返す"() {
        expect:
        UserPasswordResetConverter.toModel(null) == null
    }

    def "toModelはエンティティをモデルに正しく変換する"() {
        given:
        def now = LocalDateTime.now()
        def entity = new TUserPasswordReset()
        entity.setUserPasswordResetId(1L)
        entity.setUserId(100L)
        entity.setTokenHash([1, 2] as byte[])
        entity.setExpiresAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
        entity.setUsedAt(null)
        entity.setRequestedAt(Date.from(now.minusHours(1).atZone(ZoneId.systemDefault()).toInstant()))
        entity.setRequestCount(2)

        when:
        def model = UserPasswordResetConverter.toModel(entity)

        then:
        model.userPasswordResetId == 1L
        model.userId == 100L
        model.tokenHash == [1, 2] as byte[]
        model.expiresAt != null // Date conversion checks
        model.usedAt == null
        model.requestCount == 2
    }

    def "toEntityはモデルがnullの場合nullを返す"() {
        expect:
        UserPasswordResetConverter.toEntity(null) == null
    }

    def "toEntityはモデルをエンティティに正しく変換する"() {
        given:
        def now = LocalDateTime.now()
        def model = UserPasswordResetModel.reconstruct(
                55L, 200L, [3, 4] as byte[], now.plusMinutes(30), null, now, 1
        )

        when:
        def entity = UserPasswordResetConverter.toEntity(model)

        then:
        entity.userPasswordResetId == 55L
        entity.userId == 200L
        entity.tokenHash == [3, 4] as byte[]
        entity.expiresAt != null
        entity.usedAt == null
        entity.requestedAt != null
        entity.requestCount == 1
    }
}
