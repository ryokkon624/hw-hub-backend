package com.hwhub.backend.presentation.rest.household

import com.hwhub.backend.application.service.HouseholdAuthorizationService
import com.hwhub.backend.application.service.HouseholdInvitationService
import com.hwhub.backend.application.service.HouseholdMemberService
import com.hwhub.backend.application.service.HouseholdService
import com.hwhub.backend.domain.model.HouseholdInvitationModel
import com.hwhub.backend.domain.model.HouseholdMemberModel
import com.hwhub.backend.presentation.rest.household.dto.*
import spock.lang.Specification

import java.time.LocalDateTime

class HouseholdMemberControllerSpec extends Specification {

    HouseholdService householdService = Mock()
    HouseholdMemberService memberService = Mock()
    HouseholdAuthorizationService authService = Mock()
    HouseholdInvitationService invService = Mock()

    HouseholdMemberController controller =
            new HouseholdMemberController(householdService, memberService, authService, invService)

    // -------------------------------------------------
    // getMembers
    // -------------------------------------------------
    def "getMembers は認可チェックを行い HouseholdMembersDto を返す"() {
        given:
        Long householdId = 1L
        Long loginUserId = 10L

        def member = HouseholdMemberModel.create(householdId, 10L, "Taro")

        when:
        List<HouseholdMemberDto> result = controller.getMembers(householdId, loginUserId)

        then:
        1 * authService.assertUserBelongsToHousehold(householdId, 10L)
        1 * memberService.getMembers(householdId) >> [member]

        and:
        result != null
        result.size() == 1
    }

    // -------------------------------------------------
    // updateMyNickname
    // -------------------------------------------------
    def "updateMyNickname はログインユーザIDを使って HouseholdMemberService.updateMyNickname を呼び 204 を返す"() {
        given:
        Long householdId = 1L
        Long loginUserId = 10L
        def request = new UpdateMyNicknameRequest("new-nick")

        when:
        def response = controller.updateMyNickname(householdId, request, loginUserId)

        then:
        1 * memberService.updateMyNickname(householdId, 10L, "new-nick")

        and:
        response.statusCode.value() == 204
    }

    // -------------------------------------------------
    // updateHouseholdName
    // -------------------------------------------------
    def "updateHouseholdName は HouseholdService.updateHouseholdName を呼び 204 を返す"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        def request = new UpdateHouseholdRequest("New Name")

        when:
        def response = controller.updateHouseholdName(householdId, request, userId)

        then:
        1 * householdService.updateHouseholdName(householdId, 10L, "New Name")

        and:
        response.statusCode.value() == 204
    }

    // -------------------------------------------------
    // getInvitations
    // -------------------------------------------------
    def "getInvitations は HouseholdInvitationService.getInvitations を呼び DTO に変換したリストを返す"() {
        given:
        Long householdId = 1L
        Long userId = 10L

        def now = LocalDateTime.now()

        def model = HouseholdInvitationModel.reconstruct(
                "token-123",
                householdId,
                "My Household",
                20L,
                "Inviter Name",
                "invited@example.com",
                "0",
                now.plusDays(7),
                30L,
                "Accepted User",
                now
        )

        when:
        List<HouseholdInvitationDto> result = controller.getInvitations(householdId, userId)

        then:
        1 * invService.getInvitations(householdId, 10L) >> [model]

        and:
        result.size() == 1
        def dto = result[0]
        dto.householdId == householdId
        dto.invitationToken == "token-123"
        dto.invitedEmail == "invited@example.com"
        dto.status == "0"
        dto.expiresAt == model.expiresAt
        dto.acceptedUserId == 30L
        dto.acceptedUserName == "Accepted User"
        dto.inviterUserId == 20L
        dto.inviterDisplayName == "Inviter Name"
        dto.createdAt == model.createdAt
    }

    // -------------------------------------------------
    // createInvitation
    // -------------------------------------------------
    def "createInvitation は HouseholdInvitationService.createInvitation を呼び DTO を返す"() {
        given:
        Long householdId = 1L
        Long userId = 10L

        def request = new CreateInvitationRequest("invited@example.com")

        def now = LocalDateTime.now()

        def model = HouseholdInvitationModel.reconstruct(
                "token-456",
                householdId,
                "My Household",
                10L,
                "Inviter Name",
                "invited@example.com",
                "0",
                now.plusDays(3),
                null,
                null,
                now
        )

        when:
        HouseholdInvitationDto dto = controller.createInvitation(householdId, request, userId)

        then:
        1 * invService.createInvitation(householdId, "invited@example.com", 10L) >> model

        and:
        dto.householdId == householdId
        dto.invitationToken == "token-456"
        dto.invitedEmail == "invited@example.com"
        dto.status == "0"
        dto.expiresAt == model.expiresAt
        dto.acceptedUserId == null
        dto.acceptedUserName == null
        dto.inviterUserId == 10L
        dto.inviterDisplayName == "Inviter Name"
        dto.createdAt == model.createdAt
    }

    // -------------------------------------------------
    // deleteMyself
    // -------------------------------------------------
    def "deleteMyself は HouseholdMemberService.deleteMyself を呼び 204 を返す"() {
        given:
        Long householdId = 1L
        Long userId = 10L

        when:
        def response = controller.deleteMyself(householdId, userId)

        then:
        1 * memberService.deleteMyself(householdId, 10L)

        and:
        response.statusCode.value() == 204
    }

    // -------------------------------------------------
    // deleteMember
    // -------------------------------------------------
    def "deleteMember は HouseholdMemberService.deleteMember を呼び 204 を返す"() {
        given:
        Long householdId = 1L
        Long targetUserId = 20L
        Long loginUserId = 10L

        when:
        def response = controller.deleteMember(householdId, targetUserId, loginUserId)

        then:
        1 * memberService.deleteMember(householdId, targetUserId, 10L)

        and:
        response.statusCode.value() == 204
    }
}
