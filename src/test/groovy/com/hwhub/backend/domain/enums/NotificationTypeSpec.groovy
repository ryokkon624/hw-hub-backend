package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class NotificationTypeSpec extends Specification {

    @Unroll
    def "getCodeが正しいコード値を返すこと: #type -> #expectedCode"() {
        expect:
        type.getCode() == expectedCode

        where:
        type                                    || expectedCode
        NotificationType.INVITATION_ACCEPTED    || "0101"
        NotificationType.INVITATION_DECLINED    || "0102"
        NotificationType.HAVE_BEEN_REMOVED      || "0201"
        NotificationType.LEFT_THE_HOUSEHOLD     || "0202"
        NotificationType.ASSIGNED_TO_THE_OWNER  || "0203"
        NotificationType.TASK_ASSIGNED          || "0301"
        NotificationType.BE_DUMPED_TASK         || "0302"
        NotificationType.YOUR_TASK_WAS_TAKEN    || "0303"
    }

    @Unroll
    def "fromCodeで正しい列挙子が取得できること: #code -> #expectedType"() {
        expect:
        NotificationType.fromCode(code) == expectedType

        where:
        code   || expectedType
        "0101" || NotificationType.INVITATION_ACCEPTED
        "0102" || NotificationType.INVITATION_DECLINED
        "0201" || NotificationType.HAVE_BEEN_REMOVED
        "0202" || NotificationType.LEFT_THE_HOUSEHOLD
        "0203" || NotificationType.ASSIGNED_TO_THE_OWNER
        "0301" || NotificationType.TASK_ASSIGNED
        "0302" || NotificationType.BE_DUMPED_TASK
        "0303" || NotificationType.YOUR_TASK_WAS_TAKEN
    }

    def "fromCodeで存在しないコードを指定した場合、IllegalArgumentExceptionがスローされること"() {
        when:
        NotificationType.fromCode("9999")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid NotificationType code: 9999"
    }
}
