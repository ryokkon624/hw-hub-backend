package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.HouseholdInvitationModel;
import spock.lang.Specification

import java.time.LocalDateTime

class HouseholdInvitationConverterSpec extends Specification{

    def "toEntityは引数がnullのときnullを返す"() {
        expect:
        HouseholdInvitationConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへ全フィールドを変換する（日時含む）"() {
        given: "すべてのフィールドがセットされた招待モデル"
        LocalDateTime expiresAt = LocalDateTime.of(2025, 1, 10, 12, 0, 0)
        LocalDateTime createdAt = LocalDateTime.of(2025, 1, 1, 9, 30, 0)

        def model = HouseholdInvitationModel.reconstruct(
                "token-abc-123",
                100L,
                "householdName",
                200L,
                "inviter",
                "test@example.com",
                "1",
                expiresAt,
                300L,
                "acceptedName",
                createdAt
        )

        when: "toEntityでMyBatisエンティティへ変換する"
        def entity = HouseholdInvitationConverter.toEntity(model)

        then: "エンティティが生成される"
        entity != null

        and: "数値・文字列フィールドが正しくコピーされている"
        with(entity) {
            householdId == 100L
            inviterUserId == 200L
            invitedEmail == "test@example.com"
            invitationToken == "token-abc-123"
            status == "1"
            acceptedUserId == 300L
        }

        and: "日時フィールドがDateに正しく変換されている"
        entity.expiresAt != null
        entity.createdAt != null

        and: "Date -> LocalDateTime に戻すと元の値と一致する"
        DateConverter.toLocalDateTime(entity.expiresAt) == expiresAt
        DateConverter.toLocalDateTime(entity.createdAt) == createdAt
    }
}
