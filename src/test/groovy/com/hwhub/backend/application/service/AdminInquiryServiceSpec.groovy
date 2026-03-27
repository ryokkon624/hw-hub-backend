package com.hwhub.backend.application.service

import com.hwhub.backend.application.service.inquiry.InquiryService
import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.enums.SenderType
import com.hwhub.backend.domain.model.inquiry.AdminInquirySearchCondition
import com.hwhub.backend.domain.model.inquiry.InquiryId
import com.hwhub.backend.domain.model.inquiry.InquiryModel
import com.hwhub.backend.domain.model.inquiry.InquiryAdmin
import com.hwhub.backend.domain.repository.InquiryRepository
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AdminInquiryServiceSpec extends Specification {

    InquiryService inquiryService = Mock()
    InquiryRepository inquiryRepository = Mock()

    @Subject
    AdminInquiryService service = new AdminInquiryService(inquiryService, inquiryRepository)

    // ==================================
    // findPendingStaff
    // ==================================

    def "findPendingStaffはリポジトリのfindPendingStaffを呼び出し結果をそのまま返す"() {
        given:
        def items = [Mock(InquiryAdmin), Mock(InquiryAdmin)]

        when:
        def result = service.findPendingStaff()

        then:
        1 * inquiryRepository.findPendingStaff() >> items
        result == items
    }

    def "findPendingStaffは対応待ちがない場合、空リストを返す"() {
        when:
        def result = service.findPendingStaff()

        then:
        1 * inquiryRepository.findPendingStaff() >> []
        result.isEmpty()
    }

    // ==================================
    // searchInquiries
    // ==================================

    def "searchInquiriesはリポジトリのsearchInquiriesに検索条件を渡して結果を返す"() {
        given:
        def condition = new AdminInquirySearchCondition(null, null, null, null, null, "10", "20")
        def items = [Mock(InquiryAdmin)]

        when:
        def result = service.searchInquiries(condition)

        then:
        1 * inquiryRepository.searchInquiries(condition) >> items
        result == items
    }

    def "searchInquiriesは空の条件でも呼び出せる"() {
        given:
        def condition = new AdminInquirySearchCondition(null, null, null, null, null, null, null)

        when:
        def result = service.searchInquiries(condition)

        then:
        1 * inquiryRepository.searchInquiries(condition) >> []
        result.isEmpty()
    }

    // ==================================
    // getInquiryAsAdmin
    // ==================================

    def "getInquiryAsAdminはIDでInquiryModelを取得して返す（userIdチェックなし）"() {
        given:
        def model = InquiryModel.reconstruct(5L, 1L, "10", "00", "件名", [], LocalDateTime.now())

        when:
        def result = service.getInquiryAsAdmin(5L)

        then:
        1 * inquiryRepository.findById(new InquiryId(5L)) >> Optional.of(model)
        result == model
    }

    def "getInquiryAsAdminは問い合わせが見つからない場合ResourceNotFoundExceptionをスローする"() {
        when:
        service.getInquiryAsAdmin(5L)

        then:
        1 * inquiryRepository.findById(new InquiryId(5L)) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "getInquiryAsAdminは任意のuserIdの問い合わせを取得できる（ユーザーチェックなし）"() {
        given:
        // userId=99L（自分以外のユーザーの問い合わせ）でも取得できることを確認
        def model = InquiryModel.reconstruct(10L, 99L, "40", "20", "バグ報告", [], LocalDateTime.now())

        when:
        def result = service.getInquiryAsAdmin(10L)

        then:
        1 * inquiryRepository.findById(new InquiryId(10L)) >> Optional.of(model)
        result.userId == 99L
    }

    // ==================================
    // replyAsStaff
    // ==================================

    def "replyAsStaffはSTAFF送信者・ONL_ADM_INQプログラムでInquiryServiceのaddMessageを呼び出す"() {
        when:
        service.replyAsStaff(5L, "スタッフ返信内容", 10L)

        then:
        1 * inquiryService.addMessage(
            new InquiryId(5L), 10L, "スタッフ返信内容", SenderType.STAFF, ProgramType.ONL_ADM_INQ
        )
    }

    def "replyAsStaffは別のIDと操作者でも正しく委譲する"() {
        when:
        service.replyAsStaff(100L, "別返信", 99L)

        then:
        1 * inquiryService.addMessage(
            new InquiryId(100L), 99L, "別返信", SenderType.STAFF, ProgramType.ONL_ADM_INQ
        )
    }

    // ==================================
    // findDailyStats
    // ==================================

    def "findDailyStatsはリポジトリのfindDailyStatsに指定日数を渡して結果を返す"() {
        given:
        def stats = [Mock(com.hwhub.backend.domain.model.inquiry.DailyInquiryStatus)]

        when:
        def result = service.findDailyStats(15)

        then:
        1 * inquiryRepository.findDailyStats(15) >> stats
        result == stats
    }

    // ==================================
    // findMessageDailyStats
    // ==================================

    def "findMessageDailyStatsはリポジトリのfindMessageDailyStatsに指定日数を渡して結果を返す"() {
        given:
        def stats = [Mock(com.hwhub.backend.domain.model.inquiry.DailyInquiryMessage)]

        when:
        def result = service.findMessageDailyStats(7)

        then:
        1 * inquiryRepository.findMessageDailyStats(7) >> stats
        result == stats
    }

    // ==================================
    // findStatusSummary
    // ==================================

    def "findStatusSummaryはリポジトリのfindStatusSummaryの結果を返す"() {
        given:
        def summary = Mock(com.hwhub.backend.domain.model.inquiry.InquiryStatusSummary)

        when:
        def result = service.findStatusSummary()

        then:
        1 * inquiryRepository.findStatusSummary() >> summary
        result == summary
    }
}
