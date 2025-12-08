package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.model.HouseworkTaskRecalcRequestModel
import com.hwhub.backend.domain.enums.TaskRecalcStatus
import com.hwhub.backend.infrastructure.mybatis.generated.entity.THouseworkTaskRecalcRequest
import spock.lang.Specification

class HouseworkTaskRecalcRequestConverterSpec extends Specification{

    def "toEntityは引数がnullのときnullを返す"() {
        expect:
        HouseworkTaskRecalcRequestConverter.toEntity(null) == null
    }

    def "toEntityはモデルからエンティティへ全フィールドを変換する"() {
        given: "createファクトリで生成された再計算リクエストモデル"
        def model = HouseworkTaskRecalcRequestModel.create(100L)

        when: "toEntityでMyBatisエンティティに変換する"
        def entity = HouseworkTaskRecalcRequestConverter.toEntity(model)

        then: "エンティティが生成される"
        entity != null

        and: "houseworkId がコピーされている"
        entity.houseworkId == 100L

        and: "初期ステータスが PENDING でセットされている"
        entity.recalcRequestStatus == TaskRecalcStatus.PENDING.getCode()

        and: "retryCount は 0 でセットされている"
        entity.retryCount == 0

        and: "lastErrorMessage は null でセットされている"
        entity.lastErrorMessage == null
    }
}
