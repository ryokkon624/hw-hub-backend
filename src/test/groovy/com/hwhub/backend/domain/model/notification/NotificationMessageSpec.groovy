package com.hwhub.backend.domain.model.notification

import spock.lang.Specification
import spock.lang.Unroll

class NotificationMessageSpec extends Specification {

    def "すべての引数を指定してインスタンスが生成できること"() {
        when:
        def params = ["key": "value"]
        def message = new NotificationMessage("title.key", "body.key", params)

        then:
        message.titleKey() == "title.key"
        message.bodyKey() == "body.key"
        message.params() == params
    }

    def "titleKeyがnullでもインスタンスが生成できること"() {
        when:
        def message = new NotificationMessage(null, "body.key", null)

        then:
        message.titleKey() == null
        message.bodyKey() == "body.key"
        message.params() == null
    }

    @Unroll
    def "bodyKeyが無効な値('#invalidBodyKey')の場合、IllegalArgumentExceptionがスローされること"() {
        when:
        new NotificationMessage("title.key", invalidBodyKey, null)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "bodyKey is required"

        where:
        invalidBodyKey || _
        null           || _
        ""             || _
        "   "          || _
    }

    def "ofBodyファクトリメソッドでtitleKeyとparamsがnullのインスタンスが生成できること"() {
        when:
        def message = NotificationMessage.ofBody("body.key")

        then:
        message.titleKey() == null
        message.bodyKey() == "body.key"
        message.params() == null
    }

    def "withParamsメソッドでパラメータを追加した新しいインスタンスが生成できること"() {
        setup:
        def original = new NotificationMessage("title.key", "body.key", null)
        def newParams = ["newKey": "newValue"]

        when:
        def newMessage = original.withParams(newParams)

        then:
        newMessage.titleKey() == "title.key"
        newMessage.bodyKey() == "body.key"
        newMessage.params() == newParams
        // 元のインスタンスは変更されないこと（レコードなので当然だが確認）
        original.params() == null
    }
}
