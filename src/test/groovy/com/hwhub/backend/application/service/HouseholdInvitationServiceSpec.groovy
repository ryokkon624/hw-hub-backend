package com.hwhub.backend.application.service

import com.hwhub.backend.domain.enums.HouseholdMemberStatus
import com.hwhub.backend.domain.enums.InvitationStatus
import com.hwhub.backend.domain.enums.ProgramType
import com.hwhub.backend.domain.enums.AuthProvider
import com.hwhub.backend.domain.model.HouseholdInvitationModel
import com.hwhub.backend.domain.model.HouseholdMemberModel
import com.hwhub.backend.domain.model.UserModel
import com.hwhub.backend.domain.repository.HouseholdInvitationRepository
import com.hwhub.backend.domain.repository.HouseholdMemberRepository
import com.hwhub.backend.domain.repository.UserRepository
import com.hwhub.backend.presentation.rest.common.ResourceNotFoundException
import org.apache.commons.lang3.StringUtils
import org.springframework.security.access.AccessDeniedException
import com.hwhub.backend.application.service.notification.NotificationPublisher
import spock.lang.Specification

class HouseholdInvitationServiceSpec extends Specification {

    HouseholdMemberRepository householdMemberRepository = Mock()
    UserRepository userRepository = Mock()
    HouseholdInvitationRepository invRepository = Mock()
    // 同じ型だが別フィールドとして注入されているので、別Mockにしておく
    HouseholdMemberRepository memberRepository = Mock()
    HouseholdMemberService memberService = Mock()
    HouseholdAuthorizationService authorizationService = Mock()
    NotificationPublisher notificationPublisher = Mock()

    HouseholdInvitationService service = new HouseholdInvitationService(
            userRepository,
            invRepository,
            memberRepository,
            memberService,
            authorizationService,
            notificationPublisher
    )

    // ===========================
    // getInvitation
    // ===========================

    def "getInvitationはトークンに対応する招待が存在しない場合ResourceNotFoundExceptionを投げる"() {
        when:
        service.getInvitation("token-xxx")

        then:
        1 * invRepository.selectByToken("token-xxx") >> null
        thrown(ResourceNotFoundException)
    }

    def "getInvitationは招待が存在する場合そのまま返す"() {
        given:
        def inv = Mock(HouseholdInvitationModel)

        when:
        def result = service.getInvitation("token-abc")

        then:
        1 * invRepository.selectByToken("token-abc") >> inv
        result == inv
    }

    // ===========================
    // getInvitations
    // ===========================

    def "getInvitationsはアクセス権がない場合AccessDeniedExceptionを投げる"() {
        given:
        Long householdId = 1L
        Long userId = 10L

        when:
        service.getInvitations(householdId, userId)

        then:
        1 * authorizationService.canAccessHousehold(householdId, userId) >> false
        0 * invRepository.selectByHouseholdId(_)
        thrown(AccessDeniedException)
    }

    def "getInvitationsはアクセス権がある場合その世帯の招待一覧を返す"() {
        given:
        Long householdId = 2L
        Long userId = 20L
        def list = [Mock(HouseholdInvitationModel)]

        when:
        def result = service.getInvitations(householdId, userId)

        then:
        1 * authorizationService.canAccessHousehold(householdId, userId) >> true
        1 * invRepository.selectByHouseholdId(householdId) >> list
        result == list
    }

    // ===========================
    // acceptInvitation
    // ===========================

    def "acceptInvitationは招待が存在しない場合ResourceNotFoundExceptionを投げる"() {
        when:
        service.acceptInvitation("token-missing", 10L)

        then:
        1 * invRepository.selectByToken("token-missing") >> null
        thrown(ResourceNotFoundException)
    }

    def "acceptInvitationは招待が終端状態の場合ResourceNotFoundException(invitation.alreadyHandled)"() {
        given:
        def inv = Mock(HouseholdInvitationModel)

        when:
        service.acceptInvitation("token-terminal", 10L)

        then:
        1 * invRepository.selectByToken("token-terminal") >> inv
        1 * inv.isTerminal() >> true
        0 * inv.isExpired()
        thrown(ResourceNotFoundException)
    }

    def "acceptInvitationは招待が期限切れの場合EXPIREDに更新してからResourceNotFoundException(invitation.expired)"() {
        given:
        def inv = Mock(HouseholdInvitationModel)

        when:
        service.acceptInvitation("token-expired", 10L)

        then:
        1 * invRepository.selectByToken("token-expired") >> inv
        1 * inv.isTerminal() >> false
        1 * inv.isExpired() >> true
        1 * inv.setStatus(InvitationStatus.EXPIRED.code)
        1 * invRepository.update(inv, 10L, ProgramType.ONL_HLDINVI.code)
        thrown(ResourceNotFoundException)
    }

    def "acceptInvitationはユーザが存在しない場合ResourceNotFoundException(user.notFound)"() {
        given:
        def inv = Mock(HouseholdInvitationModel)

        when:
        service.acceptInvitation("token-user-missing", 10L)

        then:
        1 * invRepository.selectByToken("token-user-missing") >> inv
        1 * inv.isTerminal() >> false
        1 * inv.isExpired() >> false
        1 * userRepository.findById(10L) >> Optional.empty()
        thrown(ResourceNotFoundException)
        0 * memberRepository.findById(_, _)
        0 * memberService.createMember(_, _, _, _)
    }

    def "acceptInvitationはメンバーが未登録または非ACTIVEのときメンバーを追加し招待をACCEPTEDに更新する"() {
        given:
        Long householdId = 100L
        Long userId = 10L

        // 招待
        def inv = Mock(HouseholdInvitationModel) {
            getHouseholdId() >> householdId
        }

        // ユーザ
        def user = UserModel.reconstruct(
                userId,
                "user@example.com",
                "hashed",
                null,
                AuthProvider.LOCAL.code,
                null,
                "テスト太郎",
                "ja",
                true,
                "ja",
                null, 
                true,
                null,
                null
        )

        // 既存メンバーなし（または非ACTIVE）を表現するため null を返す
        // ※ ACTIVE のケースは別テストでカバー

        when:
        service.acceptInvitation("token-ok", userId)

        then:
        1 * invRepository.selectByToken("token-ok") >> inv
        1 * inv.isTerminal() >> false
        1 * inv.isExpired() >> false
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * memberRepository.findById(householdId, userId) >> null

        and: "メンバー追加が呼ばれる"
        1 * memberService.createMember(householdId, userId, "テスト太郎", userId)

        and: "招待をACCEPTEDに更新する"
        1 * inv.setStatus(InvitationStatus.ACCEPTED.code)
        1 * inv.setAcceptedUserId(userId)
        1 * invRepository.update(inv, userId, ProgramType.ONL_HLDINVI.code)
    }

    def "acceptInvitationは既にACTIVEなメンバーが存在する場合IllegalStateExceptionを投げる"() {
        given:
        Long householdId = 200L
        Long userId = 20L

        def inv = Mock(HouseholdInvitationModel) {
            getHouseholdId() >> householdId
        }

        def user = UserModel.reconstruct(
                userId,
                "user2@example.com",
                "hashed2",
                null,
                AuthProvider.LOCAL.code,
                null,
                "既存ユーザ",
                "ja",
                true,
                null,
                null, 
                true,
                null,
                null
        )

        def member = HouseholdMemberModel.reconstruct(
                householdId,
                userId,
                "既存ユーザ",
                null,
                null,
                "既存ユーザ",
                HouseholdMemberStatus.ACTIVE.code,
                "MEMBER"
        )

        when:
        service.acceptInvitation("token-dup", userId)

        then:
        1 * invRepository.selectByToken("token-dup") >> inv
        1 * inv.isTerminal() >> false
        1 * inv.isExpired() >> false
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * memberRepository.findById(householdId, userId) >> member
        thrown(IllegalStateException)
        0 * memberService.createMember(_, _, _, _)
        0 * invRepository.update(_, _, _)
        0 * notificationPublisher.publishAcceptInvitation(_, _, _, _)
    }

    def "acceptInvitationはACTIVE以外の場合メンバーを追加し招待をACCEPTEDに更新する"() {
        given:
        Long householdId = 200L
        Long userId = 20L

        def inv = Mock(HouseholdInvitationModel) {
            getHouseholdId() >> householdId
        }

        def user = UserModel.reconstruct(
                userId,
                "user2@example.com",
                "hashed2",
                null,
                AuthProvider.LOCAL.code,
                null,
                "既存ユーザ",
                "ja",
                true,
                null,
                null, 
                true,
                null,
                null
        )

        def member = HouseholdMemberModel.reconstruct(
                householdId,
                userId,
                "既存ユーザ",
                null,
                null,
                "既存ユーザ",
                HouseholdMemberStatus.LEFT.code,
                "MEMBER"
        )

        when:
        service.acceptInvitation("token-ok", userId)

        then:
        1 * invRepository.selectByToken("token-ok") >> inv
        1 * inv.isTerminal() >> false
        1 * inv.isExpired() >> false
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * memberRepository.findById(householdId, userId) >> member

        and: "メンバー追加が呼ばれる"
        1 * memberService.createMember(householdId, userId, "既存ユーザ", userId)

        and: "招待をACCEPTEDに更新する"
        1 * inv.setStatus(InvitationStatus.ACCEPTED.code)
        1 * inv.setAcceptedUserId(userId)
        1 * invRepository.update(inv, userId, ProgramType.ONL_HLDINVI.code)
    }

    // ===========================
    // declineInvitation
    // ===========================

    def "declineInvitationは招待が存在しない場合ResourceNotFoundExceptionを投げる"() {
        when:
        service.declineInvitation("token-missing", 10L)

        then:
        1 * invRepository.selectByToken("token-missing") >> null
        thrown(ResourceNotFoundException)
    }

    def "declineInvitationは終端状態の招待の場合何もせず終了する"() {
        given:
        def inv = Mock(HouseholdInvitationModel)

        when:
        service.declineInvitation("token-terminal", 10L)

        then:
        1 * invRepository.selectByToken("token-terminal") >> inv
        1 * inv.isTerminal() >> true
        0 * invRepository.update(_, _, _)
    }

    def "declineInvitationは期限切れ招待の場合EXPIREDに更新する"() {
        given:
        def inv = Mock(HouseholdInvitationModel)

        when:
        service.declineInvitation("token-expired", 10L)

        then:
        1 * invRepository.selectByToken("token-expired") >> inv
        1 * inv.isTerminal() >> false
        1 * inv.isExpired() >> true
        1 * inv.setStatus(InvitationStatus.EXPIRED.code)
        1 * inv.setAcceptedUserId(10L)
        1 * invRepository.update(inv, 10L, ProgramType.ONL_HLDINVI.code)
    }

    def "declineInvitationは未期限切れ招待の場合DECLINEDに更新する"() {
        given:
        def inv = Mock(HouseholdInvitationModel)

        when:
        service.declineInvitation("token-decline", 10L)

        then:
        1 * invRepository.selectByToken("token-decline") >> inv
        1 * inv.isTerminal() >> false
        1 * inv.isExpired() >> false
        1 * inv.setStatus(InvitationStatus.DECLINED.code)
        1 * inv.setAcceptedUserId(10L)
        1 * invRepository.update(inv, 10L, ProgramType.ONL_HLDINVI.code)
    }

    // ===========================
    // revokeInvitation
    // ===========================

    def "revokeInvitationは招待が存在しない場合ResourceNotFoundExceptionを投げる"() {
        when:
        service.revokeInvitation("token-missing", 10L)

        then:
        1 * invRepository.selectByToken("token-missing") >> null
        thrown(ResourceNotFoundException)
    }

    def "revokeInvitationは終端状態の招待の場合何もせず終了する"() {
        given:
        def inv = Mock(HouseholdInvitationModel)

        when:
        service.revokeInvitation("token-terminal", 10L)

        then:
        1 * invRepository.selectByToken("token-terminal") >> inv
        1 * inv.isTerminal() >> true
        0 * invRepository.update(_, _, _)
    }

    def "revokeInvitationは未終端招待の場合REVOKEDに更新する"() {
        given:
        def inv = Mock(HouseholdInvitationModel)

        when:
        service.revokeInvitation("token-revoke", 10L)

        then:
        1 * invRepository.selectByToken("token-revoke") >> inv
        1 * inv.isTerminal() >> false
        1 * inv.setStatus(InvitationStatus.REVOKED.code)
        1 * invRepository.update(inv, 10L, ProgramType.ONL_HLDINVI.code)
    }

    // ===========================
    // createInvitation
    // ===========================

    def "createInvitationはアクセス権がない場合AccessDeniedExceptionを投げる"() {
        given:
        Long householdId = 1L
        Long userId = 10L
        String email = "invitee@example.com"

        when:
        service.createInvitation(householdId, email, userId)

        then:
        1 * authorizationService.canAccessHousehold(householdId, userId) >> false
        0 * invRepository.countByActiveEmail(_, _)
        thrown(AccessDeniedException)
    }

    def "createInvitationは同一emailの有効な招待が既に存在する場合IllegalStateExceptionを投げる"() {
        given:
        Long householdId = 2L
        Long userId = 20L
        String email = "dup@example.com"

        when:
        service.createInvitation(householdId, email, userId)

        then:
        1 * authorizationService.canAccessHousehold(householdId, userId) >> true
        1 * invRepository.countByActiveEmail(householdId, email) >> 1L
        thrown(IllegalStateException)
        0 * invRepository.insert(_, _, _)
    }

    def "createInvitationは正常時にHouseholdInvitationModelを作成しリポジトリのinsert結果を返す"() {
        given:
        Long householdId = 3L
        Long userId = 30L
        String email = "new@example.com"

        def inserted = Mock(HouseholdInvitationModel)

        when:
        def result = service.createInvitation(householdId, email, userId)

        then:
        1 * authorizationService.canAccessHousehold(householdId, userId) >> true
        1 * invRepository.countByActiveEmail(householdId, email) >> 0L
        1 * invRepository.insert(_, userId, ProgramType.ONL_HLDINVI.code) >> inserted
        result == inserted
    }
}
