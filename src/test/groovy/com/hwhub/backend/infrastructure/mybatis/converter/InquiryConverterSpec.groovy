package com.hwhub.backend.infrastructure.mybatis.converter

import com.hwhub.backend.domain.enums.InquiryCategory
import com.hwhub.backend.domain.enums.InquiryStatus
import com.hwhub.backend.domain.enums.SenderType
import com.hwhub.backend.domain.enums.UiClient
import com.hwhub.backend.domain.model.inquiry.InquiryId
import com.hwhub.backend.domain.model.inquiry.InquiryMessageModel
import com.hwhub.backend.domain.model.inquiry.InquiryModel
import com.hwhub.backend.infrastructure.mybatis.custom.entity.AdminInquiryEntity
import com.hwhub.backend.infrastructure.mybatis.custom.entity.InquiryWithMessagesEntity
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiry
import com.hwhub.backend.infrastructure.mybatis.generated.entity.TInquiryMessage
import spock.lang.Specification

import java.time.LocalDateTime

class InquiryConverterSpec extends Specification {

    // ==================================
    // コンストラクタ
    // ==================================

    def "InquiryConverterはインスタンス化できる（@Componentとして利用される）"() {
        when:
        def converter = new InquiryConverter()

        then:
        converter != null
    }

    // ==================================
    // toModel
    // ==================================

    def "toModelはentityがnullのときnullを返す"() {
        expect:
        InquiryConverter.toModel(null) == null
    }

    def "toModelはInquiryWithMessagesEntityをInquiryModelに変換する（messagesあり）"() {
        given:
        def msgEntity = new TInquiryMessage()
        msgEntity.setMessageId(10L)
        msgEntity.setInquiryId(5L)
        msgEntity.setSeq(1)
        msgEntity.setSenderType("USER")
        msgEntity.setBody("ユーザーメッセージ")
        msgEntity.setCreatedAt(new Date())

        def entity = new InquiryWithMessagesEntity()
        entity.inquiryId = 5L
        entity.userId = 1L
        entity.category = "10"
        entity.status = "00"
        entity.title = "件名"
        entity.createdAt = new Date()
        entity.uiClient = "web"
        entity.uiVersion = "1.0.0"
        entity.apiVersion = "2.0.0"
        entity.messages = [msgEntity]

        when:
        def model = InquiryConverter.toModel(entity)

        then:
        model != null
        model.inquiryId.value() == 5L
        model.userId == 1L
        model.category == InquiryCategory.GENERAL
        model.status == InquiryStatus.OPEN
        model.title == "件名"
        model.uiClient == UiClient.WEB
        model.uiVersion == "1.0.0"
        model.apiVersion == "2.0.0"
        model.messages.size() == 1
        model.messages[0].senderType == SenderType.YOU
        model.messages[0].body == "ユーザーメッセージ"
    }

    def "toModelはmessagesがnullのとき空リストで変換する"() {
        given:
        def entity = new InquiryWithMessagesEntity()
        entity.inquiryId = 5L
        entity.userId = 1L
        entity.category = "10"
        entity.status = "00"
        entity.title = "件名"
        entity.createdAt = null
        entity.uiClient = "mobile"
        entity.uiVersion = "1.2.3"
        entity.apiVersion = "3.0.0"
        entity.messages = null

        when:
        def model = InquiryConverter.toModel(entity)

        then:
        model != null
        model.messages.isEmpty()
        model.createdAt == null
        model.uiClient == UiClient.MOBILE
    }

    // ==================================
    // toSummary
    // ==================================

    def "toSummaryはentityがnullのときnullを返す"() {
        expect:
        InquiryConverter.toSummary(null) == null
    }

    def "toSummaryはTInquiryをInquirySummaryに変換する"() {
        given:
        def entity = new TInquiry()
        entity.setInquiryId(3L)
        entity.setCategory("20")
        entity.setStatus("10")
        entity.setTitle("家事の件")
        entity.setCreatedAt(new Date())

        when:
        def summary = InquiryConverter.toSummary(entity)

        then:
        summary != null
        summary.inquiryId().value() == 3L
        summary.category() == InquiryCategory.HOUSEWORK
        summary.status() == InquiryStatus.AI_ANSWERED
        summary.title() == "家事の件"
        summary.createdAt() != null
    }

    def "toSummaryはcreatedAtがnullのときnullを返す"() {
        given:
        def entity = new TInquiry()
        entity.setInquiryId(1L)
        entity.setCategory("10")
        entity.setStatus("00")
        entity.setTitle("件名")
        entity.setCreatedAt(null)

        when:
        def summary = InquiryConverter.toSummary(entity)

        then:
        summary.createdAt() == null
    }

    // ==================================
    // toEntity (InquiryModel → TInquiry)
    // ==================================

    def "toEntityはmodelがnullのときnullを返す"() {
        expect:
        InquiryConverter.toEntity(null) == null
    }

    def "toEntityはInquiryModelをTInquiryに変換する（inquiryIdあり）"() {
        given:
        def model = InquiryModel.reconstruct(
            5L, 1L, "10", "00", "件名", [], LocalDateTime.now(), "web", "1.0.0", "2.0.0"
        )

        when:
        def entity = InquiryConverter.toEntity(model)

        then:
        entity != null
        entity.inquiryId == 5L
        entity.userId == 1L
        entity.category == "10"
        entity.status == "00"
        entity.title == "件名"
        entity.uiClient == "web"
        entity.uiVersion == "1.0.0"
        entity.apiVersion == "2.0.0"
    }

    def "toEntityはinquiryIdがnull（新規）のときinquiryIdをセットしない"() {
        given:
        def model = InquiryModel.newInquiry(1L, InquiryCategory.GENERAL, "件名", "本文", UiClient.MOBILE, "1.2.3", "3.0.0")

        when:
        def entity = InquiryConverter.toEntity(model)

        then:
        entity != null
        entity.inquiryId == null
        entity.userId == 1L
        entity.uiClient == "mobile"
        entity.uiVersion == "1.2.3"
        entity.apiVersion == "3.0.0"
    }

    // ==================================
    // toMessageModel (TInquiryMessage → InquiryMessageModel)
    // ==================================

    def "toMessageModelはentityがnullのときnullを返す"() {
        expect:
        InquiryConverter.toMessageModel(null) == null
    }

    def "toMessageModelはTInquiryMessageをInquiryMessageModelに変換する"() {
        given:
        def entity = new TInquiryMessage()
        entity.setMessageId(10L)
        entity.setInquiryId(5L)
        entity.setSeq(2)
        entity.setSenderType("AI")
        entity.setBody("AI回答")
        entity.setCreatedAt(new Date())

        when:
        def model = InquiryConverter.toMessageModel(entity)

        then:
        model != null
        model.messageId.value() == 10L
        model.inquiryId.value() == 5L
        model.seq == 2
        model.senderType == SenderType.AI_SUPPORT
        model.body == "AI回答"
        model.createdAt != null
    }

    // ==================================
    // toMessageEntity (InquiryMessageModel → TInquiryMessage)
    // ==================================

    def "toMessageEntityはmessageがnullのときnullを返す"() {
        expect:
        InquiryConverter.toMessageEntity(null) == null
    }

    def "toMessageEntityは新規メッセージ（messageId・inquiryIdがnull）を変換する"() {
        given:
        def msg = InquiryMessageModel.newMessage(null, 1, SenderType.YOU, "本文")

        when:
        def entity = InquiryConverter.toMessageEntity(msg)

        then:
        entity != null
        entity.messageId == null
        entity.inquiryId == null
        entity.seq == 1
        entity.senderType == "USER"
        entity.body == "本文"
    }

    def "toMessageEntityはmessageIdとinquiryIdが設定済みのメッセージを変換する"() {
        given:
        def msg = InquiryMessageModel.reconstruct(10L, 5L, 3, "STAFF", "スタッフ返信", LocalDateTime.now())

        when:
        def entity = InquiryConverter.toMessageEntity(msg)

        then:
        entity != null
        entity.messageId == 10L
        entity.inquiryId == 5L
        entity.seq == 3
        entity.senderType == "STAFF"
        entity.body == "スタッフ返信"
    }

    // ==================================
    // toModel4Admin (AdminInquiryEntity → InruiryAdmin)
    // ==================================

    def "toModel4Adminはentityがnullのときnullを返す"() {
        expect:
        InquiryConverter.toModel4Admin(null) == null
    }

    def "toModel4AdminはAdminInquiryEntityをInruiryAdminに変換する"() {
        given:
        def now = LocalDateTime.of(2025, 6, 1, 12, 0)
        def updated = LocalDateTime.of(2025, 6, 15, 9, 30)
        def entity = new AdminInquiryEntity()
        entity.setInquiryId(10L)
        entity.setUserId(2L)
        entity.setUserEmail("user@example.com")
        entity.setUserDisplayName("テストユーザー")
        entity.setCategory("40")
        entity.setStatus("20")
        entity.setTitle("バグ報告")
        entity.setCreatedAt(now)
        entity.setUpdatedAt(updated)
        entity.setTotalMessageCount(5)
        entity.setUserMessageCount(3)
        entity.setAiMessageCount(1)
        entity.setStaffMessageCount(1)

        when:
        def model = InquiryConverter.toModel4Admin(entity)

        then:
        model != null
        model.inquiryId()         == 10L
        model.userId()            == 2L
        model.userEmail()         == "user@example.com"
        model.userDisplayName()   == "テストユーザー"
        model.category()          == "40"
        model.status()            == "20"
        model.title()             == "バグ報告"
        model.createdAt()         == now
        model.updatedAt()         == updated
        model.totalMessageCount() == 5
        model.userMessageCount()  == 3
        model.aiMessageCount()    == 1
        model.staffMessageCount() == 1
    }

    def "toModel4AdminはcreatedAtとupdatedAtがnullのときnullのまま変換する"() {
        given:
        def entity = new AdminInquiryEntity()
        entity.setInquiryId(1L)
        entity.setUserId(1L)
        entity.setUserEmail("a@example.com")
        entity.setUserDisplayName("ユーザー")
        entity.setCategory("10")
        entity.setStatus("00")
        entity.setTitle("件名")
        entity.setCreatedAt(null)
        entity.setUpdatedAt(null)
        entity.setTotalMessageCount(0)
        entity.setUserMessageCount(0)
        entity.setAiMessageCount(0)
        entity.setStaffMessageCount(0)

        when:
        def model = InquiryConverter.toModel4Admin(entity)

        then:
        model != null
        model.createdAt() == null
        model.updatedAt() == null
    }

    def "toMessageEntityは各SenderTypeを正しくコードに変換する"() {
        given:
        def msg = InquiryMessageModel.newMessage(null, 1, senderType, "本文")

        when:
        def entity = InquiryConverter.toMessageEntity(msg)

        then:
        entity.senderType == expectedCode

        where:
        senderType             | expectedCode
        SenderType.YOU         | "USER"
        SenderType.AI_SUPPORT  | "AI"
        SenderType.STAFF       | "STAFF"
    }
}
