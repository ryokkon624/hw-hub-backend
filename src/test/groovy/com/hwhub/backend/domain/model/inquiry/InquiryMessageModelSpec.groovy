package com.hwhub.backend.domain.model.inquiry

import com.hwhub.backend.domain.enums.SenderType
import spock.lang.Specification

import java.time.LocalDateTime

class InquiryMessageModelSpec extends Specification {

    // ==================================
    // newMessage
    // ==================================

    def "newMessageはmessageIdがnullで指定フィールドが正しく設定される"() {
        given:
        def inquiryId = new InquiryId(5L)

        when:
        def msg = InquiryMessageModel.newMessage(inquiryId, 1, SenderType.YOU, "ユーザーメッセージ")

        then:
        msg.messageId == null
        msg.inquiryId == inquiryId
        msg.seq == 1
        msg.senderType == SenderType.YOU
        msg.body == "ユーザーメッセージ"
        msg.createdAt == null
    }

    def "newMessageはinquiryIdがnullでも生成できる（新規問い合わせの初回メッセージ）"() {
        when:
        def msg = InquiryMessageModel.newMessage(null, 1, SenderType.YOU, "初回メッセージ")

        then:
        msg.messageId == null
        msg.inquiryId == null
        msg.seq == 1
        msg.senderType == SenderType.YOU
        msg.body == "初回メッセージ"
    }

    def "newMessageはsenderTypeごとに正しく設定される"() {
        when:
        def msg = InquiryMessageModel.newMessage(null, seq, senderType, body)

        then:
        msg.seq == seq
        msg.senderType == senderType
        msg.body == body

        where:
        seq | senderType              | body
        1   | SenderType.YOU         | "ユーザー"
        2   | SenderType.AI_SUPPORT  | "AI返信"
        3   | SenderType.STAFF       | "スタッフ返信"
    }

    // ==================================
    // reconstruct
    // ==================================

    def "reconstructは全フィールドを正しく復元する"() {
        given:
        def now = LocalDateTime.now()

        when:
        def msg = InquiryMessageModel.reconstruct(10L, 5L, 2, "USER", "再構築メッセージ", now)

        then:
        msg.messageId.value() == 10L
        msg.inquiryId.value() == 5L
        msg.seq == 2
        msg.senderType == SenderType.YOU
        msg.body == "再構築メッセージ"
        msg.createdAt == now
    }

    def "reconstructは各senderTypeコードを正しく変換する"() {
        when:
        def msg = InquiryMessageModel.reconstruct(1L, 1L, 1, senderTypeCode, "本文", LocalDateTime.now())

        then:
        msg.senderType == expectedSenderType

        where:
        senderTypeCode | expectedSenderType
        "USER"         | SenderType.YOU
        "AI"           | SenderType.AI_SUPPORT
        "STAFF"        | SenderType.STAFF
    }
}
