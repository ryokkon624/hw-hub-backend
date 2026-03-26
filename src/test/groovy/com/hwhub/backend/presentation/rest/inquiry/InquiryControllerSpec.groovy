package com.hwhub.backend.presentation.rest.inquiry

import com.hwhub.backend.application.service.inquiry.InquiryService
import com.hwhub.backend.domain.enums.InquiryCategory
import com.hwhub.backend.domain.enums.InquiryStatus
import com.hwhub.backend.domain.enums.SenderType
import com.hwhub.backend.domain.model.inquiry.InquiryId
import com.hwhub.backend.domain.model.inquiry.InquiryModel
import com.hwhub.backend.domain.model.inquiry.InquirySummary
import com.hwhub.backend.presentation.rest.inquiry.dto.InquiryCreateRequest
import com.hwhub.backend.presentation.rest.inquiry.dto.InquiryMessageRequest
import org.springframework.security.core.Authentication
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class InquiryControllerSpec extends Specification {

    InquiryService inquiryService = Mock()

    @Subject
    InquiryController controller = new InquiryController(inquiryService)

    Authentication auth = Mock()

    def setup() {
        auth.getName() >> "1"
    }

    // ==================================
    // createInquiry
    // ==================================

    def "createInquiryはサービスを呼び出してinquiryIdを含むMapを返す"() {
        given:
        def request = new InquiryCreateRequest("10", "件名", "本文")
        def generatedId = new InquiryId(99L)

        when:
        def result = controller.createInquiry(request, auth)

        then:
        1 * inquiryService.createInquiry(1L, InquiryCategory.GENERAL, "件名", "本文") >> generatedId
        result["inquiryId"] == 99L
    }

    def "createInquiryは各カテゴリコードを正しくInquiryCategoryに変換してサービスに渡す"() {
        given:
        def request = new InquiryCreateRequest(categoryCode, "件名", "本文")

        when:
        controller.createInquiry(request, auth)

        then:
        1 * inquiryService.createInquiry(1L, expectedCategory, "件名", "本文") >> new InquiryId(1L)

        where:
        categoryCode | expectedCategory
        "10"         | InquiryCategory.GENERAL
        "20"         | InquiryCategory.HOUSEWORK
        "21"         | InquiryCategory.SHOPPING
        "30"         | InquiryCategory.ACCOUNT_SETTINGS
        "40"         | InquiryCategory.BUG_REPORT
        "90"         | InquiryCategory.OTHER
    }

    // ==================================
    // getInquiries
    // ==================================

    def "getInquiriesはユーザーの問い合わせ一覧を返す"() {
        given:
        def summaries = [
            new InquirySummary(new InquiryId(1L), InquiryCategory.GENERAL, InquiryStatus.OPEN, "件名1", LocalDateTime.now()),
            new InquirySummary(new InquiryId(2L), InquiryCategory.HOUSEWORK, InquiryStatus.CLOSED, "件名2", LocalDateTime.now()),
        ]

        when:
        def result = controller.getInquiries(auth)

        then:
        1 * inquiryService.getInquiries(1L) >> summaries
        result.items().size() == 2
        result.items()[0].inquiryId() == 1L
        result.items()[0].category() == "10"
        result.items()[0].status() == "00"
        result.items()[1].inquiryId() == 2L
    }

    def "getInquiriesは問い合わせが空の場合は空リストを返す"() {
        when:
        def result = controller.getInquiries(auth)

        then:
        1 * inquiryService.getInquiries(1L) >> []
        result.items().isEmpty()
    }

    // ==================================
    // getInquiry
    // ==================================

    def "getInquiryは指定IDの問い合わせ詳細を返す"() {
        given:
        def model = InquiryModel.reconstruct(5L, 1L, "10", "00", "件名", [], LocalDateTime.now())

        when:
        def result = controller.getInquiry(5L, auth)

        then:
        1 * inquiryService.getInquiry(new InquiryId(5L), 1L) >> model
        result.inquiryId() == 5L
        result.category() == "10"
        result.status() == "00"
        result.title() == "件名"
        result.messages().isEmpty()
    }

    // ==================================
    // addMessage
    // ==================================

    def "addMessageはユーザーメッセージを追加する（SenderType.YOUで呼ぶ）"() {
        given:
        def request = new InquiryMessageRequest("メッセージ本文")

        when:
        controller.addMessage(5L, request, auth)

        then:
        1 * inquiryService.addMessage(new InquiryId(5L), 1L, "メッセージ本文", SenderType.YOU)
    }

    // ==================================
    // closeInquiry
    // ==================================

    def "closeInquiryはサービスのcloseInquiryを呼ぶ"() {
        when:
        controller.closeInquiry(5L, auth)

        then:
        1 * inquiryService.closeInquiry(new InquiryId(5L), 1L)
    }

    // ==================================
    // escalateToStaff
    // ==================================

    def "escalateToStaffはサービスのescalateToStaffを呼ぶ"() {
        when:
        controller.escalateToStaff(5L, auth)

        then:
        1 * inquiryService.escalateToStaff(new InquiryId(5L), 1L)
    }
}
