package com.hwhub.backend.application.service.inquiry

import com.hwhub.backend.application.service.notification.NotificationPublisher
import com.hwhub.backend.domain.enums.InquiryCategory
import com.hwhub.backend.domain.enums.InquiryStatus
import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.enums.SenderType
import com.hwhub.backend.domain.model.inquiry.InquiryId
import com.hwhub.backend.domain.model.inquiry.InquiryModel
import com.hwhub.backend.domain.model.inquiry.InquirySummary
import com.hwhub.backend.domain.repository.InquiryRepository
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class InquiryServiceSpec extends Specification {

    InquiryRepository inquiryRepository = Mock()
    NotificationPublisher notificationPublisher = Mock()

    @Subject
    InquiryService service = new InquiryService(inquiryRepository, notificationPublisher)

    // ==================================
    // createInquiry
    // ==================================

    def "createInquiryはInquiryModelをinsertし、生成されたInquiryIdを返す"() {
        given:
        def generatedId = new InquiryId(99L)

        when:
        def result = service.createInquiry(1L, 10L, InquiryCategory.GENERAL, "件名", "本文")

        then:
        1 * inquiryRepository.insert(_, 1L, ProgramType.ONL_INQRY.code) >> generatedId
        result == generatedId
    }

    def "createInquiryは各カテゴリで正しく動作する"() {
        when:
        service.createInquiry(1L, 10L, category, "件名", "本文")

        then:
        1 * inquiryRepository.insert({ InquiryModel m ->
            m.category == category && m.status == InquiryStatus.OPEN
        }, 1L, ProgramType.ONL_INQRY.code) >> new InquiryId(1L)

        where:
        category << InquiryCategory.values()
    }

    // ==================================
    // getInquiries
    // ==================================

    def "getInquiriesはユーザーIDで問い合わせ一覧を返す"() {
        given:
        def summaries = [Mock(InquirySummary), Mock(InquirySummary)]

        when:
        def result = service.getInquiries(1L)

        then:
        1 * inquiryRepository.findSummariesByUserId(1L) >> summaries
        result == summaries
    }

    def "getInquiriesは問い合わせがない場合は空リストを返す"() {
        when:
        def result = service.getInquiries(1L)

        then:
        1 * inquiryRepository.findSummariesByUserId(1L) >> []
        result.isEmpty()
    }

    // ==================================
    // getInquiry
    // ==================================

    def "getInquiryはID・ユーザーIDが一致すれば問い合わせを返す"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(5L, 1L, 10L, "10", "00", "件名", [], LocalDateTime.now())

        when:
        def result = service.getInquiry(inquiryId, 1L)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        result == model
    }

    def "getInquiryは問い合わせが見つからない場合ResourceNotFoundExceptionをスローする"() {
        given:
        def inquiryId = new InquiryId(5L)

        when:
        service.getInquiry(inquiryId, 1L)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "getInquiryはuserIdが一致しない場合ResourceNotFoundExceptionをスローする"() {
        given:
        def inquiryId = new InquiryId(5L)
        // userId=2L で作成されたが、1L でアクセス
        def model = InquiryModel.reconstruct(5L, 2L, 10L, "10", "00", "件名", [], LocalDateTime.now())

        when:
        service.getInquiry(inquiryId, 1L)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        thrown(ResourceNotFoundException)
    }

    // ==================================
    // addMessage
    // ==================================

    def "addMessageはOPENステータスの問い合わせにユーザーメッセージを追加する"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(5L, 1L, 10L, "10", InquiryStatus.OPEN.code, "件名", [], LocalDateTime.now())

        when:
        service.addMessage(inquiryId, 1L, "返信内容", SenderType.YOU)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        1 * inquiryRepository.addMessage(_, 1L, ProgramType.ONL_INQRY.code)
        0 * inquiryRepository.updateStatus(_, _, _, _)
        0 * notificationPublisher.publishInquiryReplied(_, _, _, _)
    }

    def "addMessageはSTAFF_ANSWEREDステータスの場合、ステータスをPENDING_STAFFに更新する"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(
            5L, 1L, 10L, "10", InquiryStatus.STAFF_ANSWERED.code, "件名", [], LocalDateTime.now()
        )

        when:
        service.addMessage(inquiryId, 1L, "返信内容", SenderType.YOU)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        1 * inquiryRepository.addMessage(_, 1L, ProgramType.ONL_INQRY.code)
        1 * inquiryRepository.updateStatus(inquiryId, InquiryStatus.PENDING_STAFF, 1L, ProgramType.ONL_INQRY.code)
        0 * notificationPublisher.publishInquiryReplied(_, _, _, _)
    }

    def "addMessageはsenderTypeがYOU以外の場合、通知を発行する"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(
            5L, 1L, 10L, "10", InquiryStatus.OPEN.code, "件名", [], LocalDateTime.now()
        )

        when:
        service.addMessage(inquiryId, 1L, "AI返信内容", senderType)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        1 * inquiryRepository.addMessage(_, 1L, ProgramType.ONL_INQRY.code)
        1 * notificationPublisher.publishInquiryReplied(5L, "件名", 1L, 1L, ProgramType.ONL_INQRY.code)

        where:
        senderType << [SenderType.AI_SUPPORT, SenderType.STAFF]
    }

    def "addMessageはYOU以外の送信者で問い合わせが見つからない場合ResourceNotFoundExceptionをスローする"() {
        given:
        def inquiryId = new InquiryId(5L)

        when:
        service.addMessage(inquiryId, 1L, "返信内容", SenderType.AI_SUPPORT)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "addMessageはSTAFF_ANSWEREDかつSTAFF送信者の場合、ステータス更新なしで通知のみ行われる"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(
            5L, 1L, 10L, "10", InquiryStatus.STAFF_ANSWERED.code, "件名", [], LocalDateTime.now()
        )

        when:
        service.addMessage(inquiryId, 1L, "スタッフ追記", SenderType.STAFF)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        1 * inquiryRepository.addMessage(_, 1L, ProgramType.ONL_INQRY.code)
        0 * inquiryRepository.updateStatus(_, _, _, _)
        1 * notificationPublisher.publishInquiryReplied(5L, "件名", 1L, 1L, ProgramType.ONL_INQRY.code)
    }

    def "addMessageはPENDING_STAFFかつSTAFF送信者の場合、ステータス更新と通知が両方行われる"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(
            5L, 1L, 10L, "10", InquiryStatus.PENDING_STAFF.code, "件名", [], LocalDateTime.now()
        )

        when:
        service.addMessage(inquiryId, 1L, "スタッフ返信", SenderType.STAFF)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        1 * inquiryRepository.addMessage(_, 1L, ProgramType.ONL_INQRY.code)
        1 * inquiryRepository.updateStatus(inquiryId, InquiryStatus.STAFF_ANSWERED, 1L, ProgramType.ONL_INQRY.code)
        1 * notificationPublisher.publishInquiryReplied(5L, "件名", 1L, 1L, ProgramType.ONL_INQRY.code)
    }

    // ==================================
    // closeInquiry
    // ==================================

    def "closeInquiryはOPENの問い合わせをCLOSEDに変更する"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(
            5L, 1L, 10L, "10", InquiryStatus.OPEN.code, "件名", [], LocalDateTime.now()
        )

        when:
        service.closeInquiry(inquiryId, 1L)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        1 * inquiryRepository.updateStatus(inquiryId, InquiryStatus.CLOSED, 1L, ProgramType.ONL_INQRY.code)
    }

    def "closeInquiryは問い合わせが見つからない場合ResourceNotFoundExceptionをスローする"() {
        given:
        def inquiryId = new InquiryId(5L)

        when:
        service.closeInquiry(inquiryId, 1L)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "closeInquiryはすでにCLOSEDの場合IllegalStateExceptionをスローする"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(
            5L, 1L, 10L, "10", InquiryStatus.CLOSED.code, "件名", [], LocalDateTime.now()
        )

        when:
        service.closeInquiry(inquiryId, 1L)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        thrown(IllegalStateException)
        0 * inquiryRepository.updateStatus(_, _, _, _)
    }

    // ==================================
    // escalateToStaff
    // ==================================

    def "escalateToStaffはAI_ANSWEREDの問い合わせをPENDING_STAFFに変更する"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(
            5L, 1L, 10L, "10", InquiryStatus.AI_ANSWERED.code, "件名", [], LocalDateTime.now()
        )

        when:
        service.escalateToStaff(inquiryId, 1L)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        1 * inquiryRepository.updateStatus(inquiryId, InquiryStatus.PENDING_STAFF, 1L, ProgramType.ONL_INQRY.code)
    }

    def "escalateToStaffは問い合わせが見つからない場合ResourceNotFoundExceptionをスローする"() {
        given:
        def inquiryId = new InquiryId(5L)

        when:
        service.escalateToStaff(inquiryId, 1L)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "escalateToStaffはAI_ANSWERED以外のステータスではIllegalStateExceptionをスローする"() {
        given:
        def inquiryId = new InquiryId(5L)
        def model = InquiryModel.reconstruct(
            5L, 1L, 10L, "10", statusCode, "件名", [], LocalDateTime.now()
        )

        when:
        service.escalateToStaff(inquiryId, 1L)

        then:
        1 * inquiryRepository.findById(inquiryId) >> Optional.of(model)
        thrown(IllegalStateException)
        0 * inquiryRepository.updateStatus(_, _, _, _)

        where:
        statusCode                         | _
        InquiryStatus.OPEN.code           | _
        InquiryStatus.PENDING_STAFF.code  | _
        InquiryStatus.STAFF_ANSWERED.code | _
        InquiryStatus.CLOSED.code         | _
    }
}
