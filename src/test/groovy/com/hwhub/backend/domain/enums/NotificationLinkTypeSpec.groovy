package com.hwhub.backend.domain.enums

import spock.lang.Specification
import spock.lang.Unroll

class NotificationLinkTypeSpec extends Specification {

    @Unroll
    def "getCodeが正しいコード値を返すこと: #type -> #expectedCode"() {
        expect:
        type.getCode() == expectedCode

        where:
        type                              || expectedCode
        NotificationLinkType.NONE         || "None"
        NotificationLinkType.MY_TASKS     || "MyTasks"
        NotificationLinkType.HOUSEHOLD    || "Household"
        NotificationLinkType.INVITATION   || "Invite"
        NotificationLinkType.SETTINGS     || "Settings"
    }

    @Unroll
    def "fromCodeで正しい列挙子が取得できること: #code -> #expectedType"() {
        expect:
        NotificationLinkType.fromCode(code) == expectedType

        where:
        code        || expectedType
        "None"      || NotificationLinkType.NONE
        "MyTasks"   || NotificationLinkType.MY_TASKS
        "Household" || NotificationLinkType.HOUSEHOLD
        "Invite"    || NotificationLinkType.INVITATION
        "Settings"  || NotificationLinkType.SETTINGS
    }

    def "fromCodeで存在しないコードを指定した場合、IllegalArgumentExceptionがスローされること"() {
        when:
        NotificationLinkType.fromCode("Invalid")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Invalid NotificationLinkType code: Invalid"
    }
}
