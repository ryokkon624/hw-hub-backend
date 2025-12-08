package com.hwhub.backend.domain.model

import com.hwhub.backend.domain.enums.TaskRecalcStatus
import spock.lang.Specification

class HouseworkTaskRecalcRequestModelSpec extends Specification{

    def "createは初期ステータスPENDING・retryCount=0・error=nullで生成される"() {
        given:
        Long houseworkId = 100L

        when:
        def model = HouseworkTaskRecalcRequestModel.create(houseworkId)

        then:
        model.requestId == null
        model.houseworkId == houseworkId
        model.recalcRequestStatus == TaskRecalcStatus.PENDING.code
        model.retryCount == 0
        model.lastErrorMessage == null
    }
}
