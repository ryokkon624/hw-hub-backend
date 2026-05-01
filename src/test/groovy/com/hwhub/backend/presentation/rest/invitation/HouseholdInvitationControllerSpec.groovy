package com.hwhub.backend.presentation.rest.invitation

import com.hwhub.backend.application.service.HouseholdInvitationService
import com.hwhub.backend.domain.model.HouseholdInvitationModel
import com.hwhub.backend.presentation.rest.invitation.dto.InvitationResponce
import spock.lang.Specification

import java.time.LocalDateTime

class HouseholdInvitationControllerSpec extends Specification {

    HouseholdInvitationService service = Mock()
    HouseholdInvitationController controller =
            new HouseholdInvitationController(service)

    // ----------------------------
    // GET /{token}
    // ----------------------------
    def "getInvitation は service.getInvitation を呼び DTO に変換して返す"() {
        given:
        String token = "token-123"

        HouseholdInvitationModel model = HouseholdInvitationModel.reconstruct(
                token,
                10L,
                "テスト世帯",
                1L,
                "招待者",
                "test@example.com",
                "0",
                LocalDateTime.now().plusDays(7),
                null,
                null,
                LocalDateTime.now()
        )

        when:
        InvitationResponce response = controller.getInvitation(token)

        then:
        1 * service.getInvitation(token) >> model

        and:
        response != null
        response.householdId() == 10L
        response.householdName() == "テスト世帯"
        response.inviterDisplayName() == "招待者"
        response.invitedEmail() == "test@example.com"
        response.status() == "0"
    }

    // ----------------------------
    // POST /{token}/accept
    // ----------------------------
    def "accept は 認証ユーザID を取得して service.acceptInvitation を呼ぶ"() {
        given:
        String token = "token-123"
        Long loginUserId = 20L

        when:
        controller.accept(token, loginUserId)

        then:
        1 * service.acceptInvitation(token, 20L)
    }

    // ----------------------------
    // POST /{token}/decline
    // ----------------------------
    def "decline は 認証ユーザID を取得して service.declineInvitation を呼ぶ"() {
        given:
        String token = "token-123"
        Long loginUserId = 30L

        when:
        controller.decline(token, loginUserId)

        then:
        1 * service.declineInvitation(token, 30L)
    }

    // ----------------------------
    // POST /{token}/revoke
    // ----------------------------
    def "revoke は 認証ユーザID を取得して service.revokeInvitation を呼ぶ"() {
        given:
        String token = "token-123"
        Long loginUserId = 40L

        when:
        controller.revoke(token, loginUserId)

        then:
        1 * service.revokeInvitation(token, 40L)
    }
}
