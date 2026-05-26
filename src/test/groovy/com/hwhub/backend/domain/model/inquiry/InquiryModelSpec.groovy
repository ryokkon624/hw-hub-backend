package com.hwhub.backend.domain.model.inquiry

import com.hwhub.backend.domain.enums.InquiryCategory
import com.hwhub.backend.domain.enums.InquiryStatus
import com.hwhub.backend.domain.enums.SenderType
import com.hwhub.backend.domain.enums.UiClient
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class InquiryModelSpec extends Specification {

    // ==================================
    // newInquiry
    // ==================================

    def "newInquiryは初期ステータスがOPENで最初のメッセージが1件作られる"() {
        when:
        def inquiry = InquiryModel.newInquiry(1L, InquiryCategory.GENERAL, "タイトル", "本文", UiClient.WEB, "1.0.0", "2.0.0")

        then:
        inquiry.userId == 1L
        inquiry.category == InquiryCategory.GENERAL
        inquiry.status == InquiryStatus.OPEN
        inquiry.title == "タイトル"
        inquiry.inquiryId == null
        inquiry.createdAt == null
        inquiry.uiClient == UiClient.WEB
        inquiry.uiVersion == "1.0.0"
        inquiry.apiVersion == "2.0.0"
        inquiry.messages.size() == 1
        inquiry.messages[0].senderType == SenderType.YOU
        inquiry.messages[0].body == "本文"
        inquiry.messages[0].seq == 1
    }

    def "newInquiryはMOBILEクライアントでも正しく作成できる"() {
        when:
        def inquiry = InquiryModel.newInquiry(1L, InquiryCategory.GENERAL, "タイトル", "本文", UiClient.MOBILE, "1.2.3", "3.0.0")

        then:
        inquiry.uiClient == UiClient.MOBILE
        inquiry.uiVersion == "1.2.3"
        inquiry.apiVersion == "3.0.0"
    }

    // ==================================
    // reconstruct
    // ==================================

    def "reconstructは全フィールドを正しく復元する"() {
        given:
        def now = LocalDateTime.now()
        def messages = [
            InquiryMessageModel.newMessage(new InquiryId(1L), 1, SenderType.YOU, "ユーザーメッセージ"),
        ]

        when:
        def inquiry = InquiryModel.reconstruct(5L, 2L, "10", "00", "件名", messages, now, "web", "1.0.0", "2.0.0")

        then:
        inquiry.inquiryId.value() == 5L
        inquiry.userId == 2L
        inquiry.category == InquiryCategory.GENERAL
        inquiry.status == InquiryStatus.OPEN
        inquiry.title == "件名"
        inquiry.createdAt == now
        inquiry.messages.size() == 1
        inquiry.uiClient == UiClient.WEB
        inquiry.uiVersion == "1.0.0"
        inquiry.apiVersion == "2.0.0"
    }

    // ==================================
    // addUserMessage
    // ==================================

    def "addUserMessageは現在のメッセージ数+1のseqでユーザーメッセージを生成する"() {
        given:
        def inquiry = InquiryModel.newInquiry(1L, InquiryCategory.GENERAL, "タイトル", "初回メッセージ", UiClient.WEB, "1.0.0", "2.0.0")

        when:
        def message = inquiry.addMessage("追加メッセージ", SenderType.YOU)

        then:
        message.senderType == SenderType.YOU
        message.body == "追加メッセージ"
        message.seq == 2  // 既存1件 + 1
    }

    def "addUserMessageはメッセージが複数あってもseqが正しく計算される"() {
        given:
        def now = LocalDateTime.now()
        def existingMessages = [
            InquiryMessageModel.newMessage(new InquiryId(1L), 1, SenderType.YOU, "1通目"),
            InquiryMessageModel.newMessage(new InquiryId(1L), 2, SenderType.AI_SUPPORT, "AI返信"),
            InquiryMessageModel.newMessage(new InquiryId(1L), 3, SenderType.YOU, "3通目"),
        ]
        def inquiry = InquiryModel.reconstruct(1L, 2L, "10", "00", "件名", existingMessages, now, "web", "1.0.0", "2.0.0")

        when:
        def message = inquiry.addMessage("4通目", SenderType.YOU)

        then:
        message.seq == 4
    }

    // ==================================
    // close
    // ==================================

    def "closeはOPENステータスをCLOSEDに変更する"() {
        given:
        def inquiry = InquiryModel.newInquiry(1L, InquiryCategory.GENERAL, "タイトル", "本文", UiClient.WEB, "1.0.0", "2.0.0")

        when:
        inquiry.close()

        then:
        inquiry.status == InquiryStatus.CLOSED
    }

    def "closeはAI_ANSWEREDステータスをCLOSEDに変更できる"() {
        given:
        def inquiry = InquiryModel.reconstruct(
            1L, 1L, "10", InquiryStatus.AI_ANSWERED.code, "タイトル", [], LocalDateTime.now(), "web", "1.0.0", "2.0.0"
        )

        when:
        inquiry.close()

        then:
        inquiry.status == InquiryStatus.CLOSED
    }

    def "closeはSTAFF_ANSWEREDステータスをCLOSEDに変更できる"() {
        given:
        def inquiry = InquiryModel.reconstruct(
            1L, 1L, "10", InquiryStatus.STAFF_ANSWERED.code, "タイトル", [], LocalDateTime.now(), "web", "1.0.0", "2.0.0"
        )

        when:
        inquiry.close()

        then:
        inquiry.status == InquiryStatus.CLOSED
    }

    def "closeはすでにCLOSEDの場合はIllegalStateExceptionをスローする"() {
        given:
        def inquiry = InquiryModel.reconstruct(
            1L, 1L, "10", InquiryStatus.CLOSED.code, "タイトル", [], LocalDateTime.now(), "web", "1.0.0", "2.0.0"
        )

        when:
        inquiry.close()

        then:
        thrown(IllegalStateException)
    }

    // ==================================
    // escalateToStaff
    // ==================================

    def "escalateToStaffはAI_ANSWEREDステータスをPENDING_STAFFに変更する"() {
        given:
        def inquiry = InquiryModel.reconstruct(
            1L, 1L, "10", InquiryStatus.AI_ANSWERED.code, "タイトル", [], LocalDateTime.now(), "web", "1.0.0", "2.0.0"
        )

        when:
        inquiry.escalateToStaff()

        then:
        inquiry.status == InquiryStatus.PENDING_STAFF
    }

    def "escalateToStaffはAI_ANSWERED以外のステータスではIllegalStateExceptionをスローする"() {
        given:
        def inquiry = InquiryModel.reconstruct(1L, 1L, "10", statusCode, "タイトル", [], LocalDateTime.now(), "web", "1.0.0", "2.0.0")

        when:
        inquiry.escalateToStaff()

        then:
        thrown(IllegalStateException)

        where:
        statusCode                          | _
        InquiryStatus.OPEN.code            | _
        InquiryStatus.PENDING_STAFF.code   | _
        InquiryStatus.STAFF_ANSWERED.code  | _
        InquiryStatus.CLOSED.code          | _
    }

    // ==================================
    // applyAiAnswer
    // ==================================

    def "applyAiAnswerはステータスをAI_ANSWEREDに変更し、AIメッセージをリストに追加する"() {
        given:
        def inquiry = InquiryModel.newInquiry(1L, InquiryCategory.GENERAL, "タイトル", "ユーザー質問", UiClient.WEB, "1.0.0", "2.0.0")
        int initialMessageCount = inquiry.messages.size()

        when:
        inquiry.applyAiAnswer("AI回答です")

        then:
        inquiry.status == InquiryStatus.AI_ANSWERED
        inquiry.messages.size() == initialMessageCount + 1
        inquiry.messages.last().senderType == SenderType.AI_SUPPORT
        inquiry.messages.last().body == "AI回答です"
        inquiry.messages.last().seq == initialMessageCount + 1
    }
}
