package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.HouseholdModel;
import com.hwhub.backend.infrastructure.mybatis.generated.entity.MHousehold
import spock.lang.Specification

class HouseholdConverterSpec extends Specification{

    def "toModelは引数がnullのときnullを返す"() {
        expect:
        HouseholdConverter.toModel(null) == null
    }

    def "toModelはエンティティからモデルへ全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたMHouseholdエンティティ"
        def entity = new MHousehold()
        entity.setHouseholdId(1L)
        entity.setName("テスト世帯")
        entity.setOwnerUserId(10L)

        when: "toModelでドメインモデルに変換する"
        def model = HouseholdConverter.toModel(entity)

        then: "全フィールドが1対1でコピーされている"
        model != null
        with(model) {
            householdId == 1L
            name == "テスト世帯"
            ownerUserId == 10L
        }
    }

    def "toEntityはモデルからエンティティへ全フィールドを変換する"() {
        given: "すべてのフィールドがセットされたHouseholdModel"
        def model = HouseholdModel.reconstruct(
                2L,
                "別の世帯",
                20L
        )

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = HouseholdConverter.toEntity(model)

        then: "全フィールドが1対1でコピーされている"
        entity != null
        with(entity) {
            householdId == 2L
            name == "別の世帯"
            ownerUserId == 20L
        }
    }
}
