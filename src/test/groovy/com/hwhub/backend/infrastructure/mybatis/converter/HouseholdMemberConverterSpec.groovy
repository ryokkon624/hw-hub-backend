package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.HouseholdMemberModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHouseholdMember
import spock.lang.Specification

class HouseholdMemberConverterSpec extends Specification{

    def "toEntityは引数がnullのときnullを返す"() {
        expect:
        HouseholdMemberConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへ全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたHouseholdMemberModel"
        def model = HouseholdMemberModel.reconstruct(
                1L,               // householdId
                10L,              // userId
                "表示名太郎",        // displayName（toEntityでは使われない）
                "profile-key-001",// profileImageKey（toEntityでは使われない）
                "https://example.com/icon.png", // iconUrl（toEntityでは使われない）
                "にっくねーむ",      // nickname
                "1",              // status
                "MEMBER"          // role（toEntityでは使われない）
        )

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = HouseholdMemberConverter.toEntity(model)

        then: "エンティティが生成され、対応するフィールドがコピーされている"
        entity != null
        with(entity) {
            householdId == 1L
            userId == 10L
            nickname == "にっくねーむ"
            status == "1"
        }
    }

    def "toModelは引数がnullのときnullを返す"() {
        expect:
        HouseholdMemberConverter.toModel(null) == null
    }

    def "toModelはエンティティからモデルへ必要なフィールドのみを変換する"() {
        given: "すべてのフィールドがセットされたMHouseholdMemberエンティティ"
        def entity = new MHouseholdMember()
        entity.setHouseholdId(2L)
        entity.setUserId(20L)
        entity.setNickname("メンバー次郎")
        entity.setStatus("9")

        when: "toModelでドメインモデルに変換する"
        def model = HouseholdMemberConverter.toModel(entity)

        then: "ID系とニックネーム・ステータスはコピーされる"
        model != null
        with(model) {
            householdId == 2L
            userId == 20L
            nickname == "メンバー次郎"
            status == "9"
        }

        and: "displayName, プロフィール情報, role はnull固定である"
        model.displayName == null
        model.profileImageKey == null
        model.iconUrl == null
        model.role == null
    }
}
