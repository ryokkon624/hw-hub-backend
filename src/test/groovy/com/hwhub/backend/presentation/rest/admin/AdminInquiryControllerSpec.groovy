package com.hwhub.backend.presentation.rest.admin

import com.hwhub.backend.application.service.AdminInquiryService
import com.hwhub.backend.domain.model.inquiry.AdminInquirySearchCondition
import com.hwhub.backend.domain.model.inquiry.InquiryModel
import com.hwhub.backend.domain.model.inquiry.InruiryAdmin
import com.hwhub.backend.presentation.rest.admin.dto.AdminInquiryReplyRequest
import org.springframework.security.core.Authentication
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.time.LocalDateTime

class AdminInquiryControllerSpec extends Specification {

    AdminInquiryService adminInquiryService = Mock()

    @Subject
    AdminInquiryController controller = new AdminInquiryController(adminInquiryService)

    Authentication auth = Mock()

    def setup() {
        auth.getName() >> "10"
    }

    // テスト用ファクトリ
    private static InruiryAdmin makeInruiryAdmin(Long inquiryId) {
        new InruiryAdmin(
            inquiryId, 2L, "user@example.com", "ユーザー名",
            "10", "20", "件名 ${inquiryId}",
            LocalDateTime.of(2025, 1, 1, 0, 0),
            LocalDateTime.of(2025, 6, 1, 0, 0),
            3, 1, 1, 1
        )
    }

    // ==================================
    // getPendingStaff
    // ==================================

    def "getPendingStaffはスタッフ対応待ち一覧をAdminInquiryResponseのリストで返す"() {
        given:
        def items = [makeInruiryAdmin(1L), makeInruiryAdmin(2L)]

        when:
        def result = controller.getPendingStaff()

        then:
        1 * adminInquiryService.findPendingStaff() >> items
        result.size() == 2
        result[0].inquiryId() == 1L
        result[0].userEmail() == "user@example.com"
        result[0].userDisplayName() == "ユーザー名"
        result[0].category() == "10"
        result[0].status() == "20"
        result[0].title() == "件名 1"
        result[0].totalMessageCount() == 3
        result[0].userMessageCount() == 1
        result[0].aiMessageCount() == 1
        result[0].staffMessageCount() == 1
        result[1].inquiryId() == 2L
    }

    def "getPendingStaffはスタッフ対応待ちがない場合、空リストを返す"() {
        when:
        def result = controller.getPendingStaff()

        then:
        1 * adminInquiryService.findPendingStaff() >> []
        result.isEmpty()
    }

    // ==================================
    // search
    // ==================================

    def "searchは全パラメータを検索条件に組み立ててサービスを呼び出し結果を返す"() {
        given:
        def createdFrom  = LocalDate.of(2025, 1, 1)
        def createdTo    = LocalDate.of(2025, 6, 30)
        def updatedFrom  = LocalDate.of(2025, 3, 1)
        def updatedTo    = LocalDate.of(2025, 6, 30)
        def item = makeInruiryAdmin(5L)

        when:
        def result = controller.search(createdFrom, createdTo, updatedFrom, updatedTo, 2L, "10", "20")

        then:
        1 * adminInquiryService.searchInquiries({ AdminInquirySearchCondition c ->
            c.createdAtFrom == createdFrom &&
            c.createdAtTo   == createdTo   &&
            c.updatedAtFrom == updatedFrom &&
            c.updatedAtTo   == updatedTo   &&
            c.userId        == 2L          &&
            c.category      == "10"        &&
            c.status        == "20"
        }) >> [item]
        result.size() == 1
        result[0].inquiryId() == 5L
    }

    def "searchは全パラメータがnullでも空の検索条件でサービスを呼び出す"() {
        when:
        def result = controller.search(null, null, null, null, null, null, null)

        then:
        1 * adminInquiryService.searchInquiries({ AdminInquirySearchCondition c ->
            c.createdAtFrom == null &&
            c.createdAtTo   == null &&
            c.updatedAtFrom == null &&
            c.updatedAtTo   == null &&
            c.userId        == null &&
            c.category      == null &&
            c.status        == null
        }) >> []
        result.isEmpty()
    }

    def "searchの結果はAdminInquiryResponseに変換されてリストで返る"() {
        given:
        def items = [makeInruiryAdmin(10L), makeInruiryAdmin(20L), makeInruiryAdmin(30L)]

        when:
        def result = controller.search(null, null, null, null, null, null, null)

        then:
        1 * adminInquiryService.searchInquiries(_) >> items
        result.size() == 3
        result*.inquiryId() == [10L, 20L, 30L]
    }

    // ==================================
    // getDetail
    // ==================================

    def "getDetailは問い合わせIDで管理者向け詳細をInquiryDetailResponseで返す"() {
        given:
        def model = InquiryModel.reconstruct(
            5L, 1L, 10L, "10", "20", "件名", [], LocalDateTime.of(2025, 1, 1, 0, 0)
        )

        when:
        def result = controller.getDetail(5L)

        then:
        1 * adminInquiryService.getInquiryAsAdmin(5L) >> model
        result.inquiryId() == 5L
        result.category()  == "10"
        result.status()    == "20"
        result.title()     == "件名"
        result.messages().isEmpty()
    }

    def "getDetailはメッセージが含まれる問い合わせも正しく返す"() {
        given:
        def now = LocalDateTime.of(2025, 1, 1, 0, 0)
        def model = InquiryModel.reconstruct(7L, 2L, 20L, "40", "25", "バグ報告", [], now)

        when:
        def result = controller.getDetail(7L)

        then:
        1 * adminInquiryService.getInquiryAsAdmin(7L) >> model
        result.inquiryId() == 7L
        result.status()    == "25"
        result.title()     == "バグ報告"
    }

    // ==================================
    // reply
    // ==================================

    def "replyはauthからoperatorUserIdを取得してreplyAsStaffを呼び出す"() {
        given:
        def request = new AdminInquiryReplyRequest("スタッフ返信内容")

        when:
        controller.reply(5L, request, auth)

        then:
        1 * adminInquiryService.replyAsStaff(5L, "スタッフ返信内容", 10L)
    }

    def "replyは別の問い合わせIDとユーザーでも正しく呼び出す"() {
        given:
        def authOther = Mock(Authentication)
        authOther.getName() >> "99"
        def request = new AdminInquiryReplyRequest("別スタッフの返信")

        when:
        controller.reply(100L, request, authOther)

        then:
        1 * adminInquiryService.replyAsStaff(100L, "別スタッフの返信", 99L)
    }
}
